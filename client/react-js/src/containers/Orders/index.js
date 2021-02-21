import React from 'react';

import axios from '../../axios-order';
import Order from '../../components/Order';
import Spinner from '../../components/UI/Spinner';
import withErrorHandler from '../../hoc/withErrorHandler';

class Orders extends React.Component {

	state = {
		orders: [],
		loading: true
	};

	componentDidMount() {
		axios.get('/orders.json')
			.then(response => {
				const fetchedOrders = [];
				for (let key in response.data) {
					fetchedOrders.push({
						...response.data[key],
						id: key
					});
				}
				this.setState({ orders: fetchedOrders, loading: false });
			})
			.catch(() => {
				this.setState({ loading: false });
			});
	}

	render() {
		let ordersOutput;
		if (this.state.loading) {
			ordersOutput = <Spinner />;
		} else {
			ordersOutput = this.state.orders.map(order => (
				<Order
					key={order.id}
					ingredients={order.ingredients}
					price={+order.price} />
			));
		}
		return (
			<div>
				{ordersOutput}
			</div>
		);
	}

}

export default withErrorHandler(Orders, axios);
