import React from 'react';
import { Route, Switch } from 'react-router-dom';

import Layout from './components/Layout';
import BurgerBuilder from './containers/BurgerBuilder';
import Checkout from './containers/Checkout';
import Orders from './containers/Orders';

const App = () => (
	<div>
		<Layout>
			<Switch>
				<Route path="/checkout" component={Checkout} />
				<Route path="/orders" component={Orders} />
				<Route path="/" exact component={BurgerBuilder} />
				<Route path="/" render={() => <h1>404: Page not found</h1>} />
			</Switch>
		</Layout>
	</div>
);

export default App;
