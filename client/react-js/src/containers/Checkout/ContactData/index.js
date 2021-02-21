import React from 'react';

import classes from './ContactData.module.css';
import Button from '../../../components/UI/Button';
import Spinner from '../../../components/UI/Spinner';
import Input from '../../../components/UI/Input';
import axios from '../../../axios-order';

export default class ContactData extends React.Component {

	state = {
		orderForm: {
			name: {
				elementType: 'input',
				elementConfig: {
					type: 'text',
					placeholder: 'Your Name'
				},
				value: '',
				validation: {
					required: true
				},
				valid: false,
				touched: false
			},
			street: {
				elementType: 'input',
				elementConfig: {
					type: 'text',
					placeholder: 'Street'
				},
				value: '',
				validation: {
					required: true
				},
				valid: false,
				touched: false
			},
			postalCode: {
				elementType: 'input',
				elementConfig: {
					type: 'text',
					placeholder: 'Postal Code'
				},
				value: '',
				validation: {
					required: true,
					minLength: 5,
					maxLength: 5
				},
				valid: false,
				touched: false
			},
			country: {
				elementType: 'input',
				elementConfig: {
					type: 'text',
					placeholder: 'Country'
				},
				value: '',
				validation: {
					required: true
				},
				valid: false,
				touched: false
			},
			email: {
				elementType: 'input',
				elementConfig: {
					type: 'email',
					placeholder: 'Your E-mail'
				},
				value: '',
				validation: {
					required: true
				},
				valid: false,
				touched: false
			},
			deliveryMethod: {
				elementType: 'select',
				elementConfig: {
					options: [
						{ value: 'cheapest', displayValue: 'Cheapest' },
						{ value: 'fastest', displayValue: 'Fastest' }
					]
				},
				value: 'fastest',
				validation: {},
				valid: true
			}
		},
		validForm: false,
		loading: false
	};

	orderHandler = (event) => {
		event.preventDefault();
		this.setState({ loading: true });
		const formData = {};
		for (let formElementIdentifier in this.state.orderForm) {
			formData[formElementIdentifier] = this.state.orderForm[formElementIdentifier].value;
		}
		const order = {
			ingredients: this.props.ingredients,
			price: this.props.price,
			orderData: formData
		};
		axios.post('/orders.json', order)
			.then(() => {
				this.setState({ loading: false });
				this.props.history.push('/');
			})
			.catch(() => {
				this.setState({ loading: false });
			});
	};

	checkValidity = (value, rules) => {
		let valid = true;

		if (rules.required) {
			valid = valid && value.trim() !== '';
		}

		if (rules.minLength) {
			valid = valid && value.trim().length >= rules.minLength;
		}

		if (rules.maxLength) {
			valid = valid && value.trim().length <= rules.maxLength;
		}

		return valid;
	};

	inputChangedHandler = (event, inputIdentifier) => {
		const newOrderForm = { ...this.state.orderForm };
		const newElement = { ...newOrderForm[inputIdentifier] };
		newElement.value = event.target.value;
		newElement.valid = this.checkValidity(newElement.value, newElement.validation);
		newElement.touched = true;
		newOrderForm[inputIdentifier] = newElement;

		let validForm = true;
		for (let inputName in newOrderForm) {
			if (!newOrderForm[inputName].valid) {
				validForm = false;
				break;
			}
		}

		this.setState({ orderForm: newOrderForm, validForm: validForm });
	};

	render() {
		const formElements = [];
		for (let key in this.state.orderForm) {
			formElements.push({
				id: key,
				config: this.state.orderForm[key]
			});
		}
		let form = (
			<form onSubmit={this.orderHandler}>
				{formElements.map(formElement => (
					<Input
						key={formElement.id}
						elementType={formElement.config.elementType}
						elementConfig={{ ...formElement.config.elementConfig }}
						changed={event => this.inputChangedHandler(event, formElement.id)}
						invalid={!formElement.config.valid}
						touched={formElement.config.touched}
						value={formElement.config.value} />
				))}
				<Button btnType="Success" disabled={!this.state.validForm}>ORDER</Button>
			</form>
		);
		if (this.state.loading) {
			form = <Spinner />;
		}
		return (
			<div className={classes.ContactData}>
				<h4>Enter your Contact Data</h4>
				{form}
			</div>
		);
	}

}
