import React from 'react';

import classes from './BuildControl.module.css';

const buildControl = ({ label, lessDisabled, lessHandler, moreHandler }) => (
	<div className={classes.BuildControl}>
		<div className={classes.Label}>{label}</div>
		<button
			className={classes.Less}
			onClick={lessHandler}
			disabled={lessDisabled}>
			Less
		</button>
		<button
			className={classes.More}
			onClick={moreHandler}>
			More
		</button>
	</div>
);

export default buildControl;
