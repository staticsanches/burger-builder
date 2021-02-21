import React from 'react';

import classes from './Order.module.css';

const order = ({ ingredients, price }) => {
	const ingredientsArray = [];
	for (let ingredientName in ingredients) {
		ingredientsArray.push({
			name: ingredientName,
			amount: ingredients[ingredientName]
		});
	}
	const ingredientsOutput = ingredientsArray.map(ingredient => {
		return <span
			key={ingredient.name}
			style={{
				textTransform: 'capitalize',
				display: 'inline-block',
				margin: '0 8px',
				border: '1px solid #ccc',
				padding: '5px'
			}}>{ingredient.name} ({ingredient.amount})</span>;
	});
	return (
		<div className={classes.Order}>
			<p>Ingredients: {ingredientsOutput}</p>
			<p>Price: <strong>$ {price.toFixed(2)}</strong></p>
		</div>
	);
};

export default order;
