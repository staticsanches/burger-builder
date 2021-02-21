import React from 'react';

import classes from './SideDrawer.module.css';
import Logo from '../../Logo';
import NavigationItems from '../NavigationItems';
import Backdrop from '../../UI/Backdrop';

const sideDrawer = ({ open, closed }) => {
	let attachedClasses = [classes.SideDrawer, classes.Close];
	if (open) {
		attachedClasses = [classes.SideDrawer, classes.Open];
	}
	return (
		<>
			<Backdrop show={open} clicked={closed} />
			<div className={attachedClasses.join(' ')}>
				<div className={classes.Logo}>
					<Logo />
				</div>
				<nav>
					<NavigationItems />
				</nav>
			</div>
		</>
	);
};

export default sideDrawer;
