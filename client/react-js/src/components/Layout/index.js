import React, { useState } from 'react';

import classes from './Layout.module.css';
import Toolbar from '../Navigation/Toolbar';
import SideDrawer from '../Navigation/SideDrawer';

const Layout = ({ children }) => {
	const [showSideDrawer, setShowSideDrawer] = useState(false);

	const sideDrawerClosedHandler = () => {
		setShowSideDrawer(false);
	}
	const sideDrawerToggleHandler = () => {
		setShowSideDrawer(!showSideDrawer);
	}

	return (
		<>
			<Toolbar drawerToggleClicked={sideDrawerToggleHandler} />
			<SideDrawer open={showSideDrawer} closed={sideDrawerClosedHandler} />
			<main className={classes.Content}>
				{children}
			</main>
		</>
	);
};

export default Layout;
