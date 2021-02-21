import React from 'react';

import classes from './CheckoutSummary.module.css';
import Burger from '../../Burger';
import Button from '../../UI/Button';

const checkoutSummary = ({ ingredients, checkoutCancelled, checkoutContinued }) => {
	return (
		<div className={classes.CheckoutSummary}>
			<h1>We hope it tastes well!</h1>
			<div style={{ width: '100%', margin: 'auto' }}>
				<Burger ingredients={ingredients} />
			</div>
			<Button
				clicked={checkoutCancelled}
				btnType="Danger">CANCEL</Button>
			<Button
				clicked={checkoutContinued}
				btnType="Success">CONTINUE</Button>
		</div>
	);
};

export default checkoutSummary;
