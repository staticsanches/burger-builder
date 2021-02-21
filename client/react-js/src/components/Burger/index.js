import React from 'react';
import PropTypes from 'prop-types';

import Ingredient from './Ingredient';
import classes from './Burger.module.css';

const Burger = ({ ingredients }) => {
	let transformedIngredients = Object.keys(ingredients)
		.sort((a, b) => ingredientScore[a] - ingredientScore[b])
		.map(igKey => {
			return [...Array(ingredients[igKey])].map((_, i) => {
				return <Ingredient type={igKey} key={igKey + i} />
			});
		})
		.reduce((arr, el) => arr.concat(el), []);
	if (transformedIngredients.length === 0) {
		transformedIngredients = <p>Please, start adding ingredients!</p>;
	}
	return (
		<div className={classes.Burger}>
			<Ingredient type="top-bun" />
			{transformedIngredients}
			<Ingredient type="bottom-bun" />
		</div>
	);
}

// Used to always keep the ingredients in the same order
const ingredientScore = {
	salad: 1,
	bacon: 2,
	cheese: 3,
	meat: 4
}

const isPositive = (props, propName, componentName) => {
	const regex = /^\d+$/;
	if (!regex.test(props[propName])) {
		return new Error(`Invalid prop '${propName}' passed to '${componentName}'. Expected non-negative integer!`);
	}
}

Burger.propTypes = {
	ingredients: PropTypes.exact({
		salad: isPositive,
		cheese: isPositive,
		bacon: isPositive,
		meat: isPositive
	})
}

export default Burger;
