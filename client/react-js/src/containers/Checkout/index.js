import React from 'react';
import { Route } from 'react-router-dom';
import { connect } from 'react-redux';

import CheckoutSummary from '../../components/Order/CheckoutSummary';
import ContactData from './ContactData';

class Checkout extends React.Component {

	checkoutCancelledHandler = () => {
		this.props.history.goBack();
	};

	checkoutContinuedHandler = () => {
		this.props.history.replace(this.props.match.path + '/contact-data');
	};

	render() {
		return (
			<div>
				<CheckoutSummary
					checkoutCancelled={this.checkoutCancelledHandler}
					checkoutContinued={this.checkoutContinuedHandler}
					ingredients={this.props.ingredients} />
				<Route path={this.props.match.path + '/contact-data'} component={ContactData} />
			</div>
		);
	}

}

const mapStateToProps = state => {
	return {
		ingredients: state.ingredients
	};
};

export default connect(mapStateToProps)(Checkout);
