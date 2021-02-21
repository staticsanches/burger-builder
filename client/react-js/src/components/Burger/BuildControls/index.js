import React from 'react'

import classes from './BuildControls.module.css'
import BuildControl from './BuildControl';

const controls = [
	{ label: 'Salad', type: 'salad' },
	{ label: 'Bacon', type: 'bacon' },
	{ label: 'Cheese', type: 'cheese' },
	{ label: 'Meat', type: 'meat' }
];

const buildControls = ({ price, purchasable, ordered, disabled, lessHandler, moreHandler }) => (
	<div className={classes.BuildControls}>
		<p>Current Price: <strong>${price.toFixed(2)}</strong></p>
		{controls.map(ctrl => (
			<BuildControl
				key={ctrl.label}
				label={ctrl.label}
				lessDisabled={disabled[ctrl.type]}
				lessHandler={() => lessHandler(ctrl.type)}
				moreHandler={() => moreHandler(ctrl.type)} />
		))}
		<button
			className={classes.OrderButton}
			disabled={!purchasable}
			onClick={ordered}>
			ORDER NOW
		</button>
	</div>
);

export default buildControls;
