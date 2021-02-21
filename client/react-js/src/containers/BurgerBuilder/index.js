import React, { useState, useEffect } from 'react';

import Burger from '../../components/Burger';
import BuildControls from '../../components/Burger/BuildControls';
import OrderSummary from '../../components/Burger/OrderSummary';
import Modal from '../../components/UI/Modal';
import Spinner from '../../components/UI/Spinner';
import withErrorHandler from '../../hoc/withErrorHandler';
import axios from '../../axios-order';

const INGREDIENT_PRICES = {
	salad: 0.5,
	bacon: 0.7,
	cheese: 0.4,
	meat: 1.3
};
const INITIAL_PRICE = 4;

const BurgerBuilder = ({ history }) => {
	const [ingredients, setIngredients] = useState(null);
	const [totalPrice, setTotalPrice] = useState(INITIAL_PRICE);
	const [purchasing, setPurchasing] = useState(false);
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState(false);

	useEffect(() => {
		axios.get('/ingredients.json')
			.then(response => {
				const newIngredients = response.data;
				let newPrice = INITIAL_PRICE;
				for (const key in newIngredients) {
					newPrice += newIngredients[key] * INGREDIENT_PRICES[key];
				}
				setIngredients(newIngredients);
				setTotalPrice(newPrice);
			})
			.catch(() => {
				setError(true);
			});
	}, []);

	const removeIngredientHandler = (type) => {
		const oldCount = ingredients[type];
		if (oldCount === 0) {
			return; // nothing to remove
		}
		const updatedIngredients = {
			...ingredients
		};
		updatedIngredients[type] = oldCount - 1;

		setIngredients(updatedIngredients);
		setTotalPrice(totalPrice - INGREDIENT_PRICES[type]);
	};
	const addIngredientHandler = (type) => {
		const updatedIngredients = {
			...ingredients
		};
		updatedIngredients[type] = ingredients[type] + 1;

		setIngredients(updatedIngredients);
		setTotalPrice(totalPrice + INGREDIENT_PRICES[type]);
	};
	const purchaseHandler = () => {
		setPurchasing(true);
	};
	const purchaseCancelHandler = () => {
		setPurchasing(false);
	};
	const purchaseContinueHandler = () => {
		const queryParams = [];
		for (let i in ingredients) {
			queryParams.push(encodeURIComponent(i) + '=' + encodeURIComponent(ingredients[i]));
		}
		queryParams.push('price=' + totalPrice);
		history.push({
			pathname: '/checkout',
			search: '?' + queryParams.join('&')
		});
	};

	let burgerWithControls;
	let orderSummary = null;
	if (ingredients) {
		const disabledInfo = {
			...ingredients
		};
		for (let key in disabledInfo) {
			disabledInfo[key] = disabledInfo[key] === 0;
		}
		const purchasable = Object.keys(ingredients)
			.reduce((sum, key) => sum + ingredients[key], 0) > 0;
		burgerWithControls = (
			<>
				<Burger ingredients={ingredients} />
				<BuildControls
					price={totalPrice}
					purchasable={purchasable}
					ordered={purchaseHandler}
					disabled={disabledInfo}
					lessHandler={removeIngredientHandler}
					moreHandler={addIngredientHandler} />
			</>
		);
		orderSummary = (
			<OrderSummary
				ingredients={ingredients}
				price={totalPrice}
				purchaseCancelled={purchaseCancelHandler}
				purchaseContinued={purchaseContinueHandler} />
		);
		if (loading) {
			orderSummary = <Spinner />;
		}
	} else {
		burgerWithControls = error ? <p>Ingredients can't be loaded!</p> : <Spinner />;
	}

	return (
		<>
			<Modal show={purchasing} modalClosed={purchaseCancelHandler}>
				{orderSummary}
			</Modal>
			{burgerWithControls}
		</>
	);
};

export default withErrorHandler(BurgerBuilder, axios);
