import React from 'react';
import PropTypes from 'prop-types';

import classes from './Ingredient.module.css'

const Ingredient = ({ type }) => {
	let ingredient;
	switch (type) {
		case 'top-bun':
			ingredient = (
				<div className={classes.TopBun}>
					<div className={classes.Seeds1} />
					<div className={classes.Seeds2} />
				</div>
			);
			break;
		case 'salad':
			ingredient = <div className={classes.Salad} />
			break;
		case 'bacon':
			ingredient = <div className={classes.Bacon} />
			break;
		case 'cheese':
			ingredient = <div className={classes.Cheese} />
			break;
		case 'meat':
			ingredient = <div className={classes.Meat} />
			break;
		case 'bottom-bun':
			ingredient = <div className={classes.BottomBun} />;
			break;
		default:
			ingredient = null;
	}
	return ingredient;
};

Ingredient.propTypes = {
	type: PropTypes.oneOf(
		['top-bun', 'salad', 'cheese', 'bacon', 'meat', 'bottom-bun']
	).isRequired
}

export default Ingredient;
