import React from 'react';
import { connect } from 'react-redux';

import Burger from '../../components/Burger';
import BuildControls from '../../components/Burger/BuildControls';
import OrderSummary from '../../components/Burger/OrderSummary';
import Modal from '../../components/UI/Modal';
import Spinner from '../../components/UI/Spinner';
import withErrorHandler from '../../hoc/withErrorHandler';
import axios from '../../axios-order';
import * as actionTypes from '../../store/actions';

class BurgerBuilder extends React.Component {

	state = {
		purchasing: false,
		error: false
	};

	// useEffect(() => {
	// 	axios.get('/ingredients.json')
	// 		.then(response => {
	// 			const newIngredients = response.data;
	// 			let newPrice = INITIAL_PRICE;
	// 			for (const key in newIngredients) {
	// 				newPrice += newIngredients[key] * INGREDIENT_PRICES[key];
	// 			}
	// 			setIngredients(newIngredients);
	// 			setTotalPrice(newPrice);
	// 		})
	// 		.catch(() => {
	// 			setError(true);
	// 		});
	// }, []);

	purchaseHandler = () => {
		this.setState({ purchasing: true });
	};

	purchaseCancelHandler = () => {
		this.setState({ purchasing: false });
	};

	purchaseContinueHandler = () => {
		this.props.history.push('/checkout');
	};

	render() {
		let burgerWithControls;
		let orderSummary = null;
		if (this.props.ingredients) {
			const disabledInfo = {
				...this.props.ingredients
			};
			for (let key in disabledInfo) {
				disabledInfo[key] = disabledInfo[key] === 0;
			}
			const purchasable = Object.keys(this.props.ingredients)
				.reduce((sum, key) => sum + this.props.ingredients[key], 0) > 0;
			burgerWithControls = (
				<>
					<Burger ingredients={this.props.ingredients} />
					<BuildControls
						price={this.props.totalPrice}
						purchasable={purchasable}
						ordered={this.purchaseHandler}
						disabled={disabledInfo}
						lessHandler={this.props.onIngredientRemoved}
						moreHandler={this.props.onIngredientAdded} />
				</>
			);
			orderSummary = (
				<OrderSummary
					ingredients={this.props.ingredients}
					price={this.props.totalPrice}
					purchaseCancelled={this.purchaseCancelHandler}
					purchaseContinued={this.purchaseContinueHandler} />
			);
		} else {
			burgerWithControls = this.state.error ? <p>Ingredients can't be loaded!</p> : <Spinner />;
		}

		return (
			<>
				<Modal show={this.state.purchasing} modalClosed={this.purchaseCancelHandler}>
					{orderSummary}
				</Modal>
				{burgerWithControls}
			</>
		);
	}

}

const mapStateToProps = state => {
	return {
		ingredients: state.ingredients,
		totalPrice: state.totalPrice
	};
};

const mapDispatchToProps = dispatch => {
	return {
		onIngredientAdded: (ingredient) => dispatch({ type: actionTypes.ADD_INGREDIENT, ingredientName: ingredient }),
		onIngredientRemoved: (ingredient) => dispatch({
			type: actionTypes.REMOVE_INGREDIENT,
			ingredientName: ingredient
		})
	};
};

export default connect(mapStateToProps, mapDispatchToProps)(withErrorHandler(BurgerBuilder, axios));
