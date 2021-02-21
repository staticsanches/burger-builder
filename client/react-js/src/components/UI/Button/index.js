import React from 'react';

import classes from './Button.module.css';

const button = ({ btnType, clicked, disabled, children }) => (
	<button
		disabled={disabled}
		className={[classes.Button, classes[btnType]].join(' ')}
		onClick={clicked}
	>
		{children}
	</button>
);

export default button;
