import React from "react";

import Modal from '../../components/UI/Modal';

const withErrorHandler = (WrappedComponent, axios) => {
	return class extends React.Component {

		state = {
			error: null
		}
		requestInterceptor = null;
		responseInterceptor = null;

		constructor(props) {
			super(props);

			this.requestInterceptor = axios.interceptors.request.use(request => {
				this.setState({ error: null })
				return request;
			})
			this.responseInterceptor = axios.interceptors.response.use(response => response, error => {
				this.setState({ error: error })
			});
		}

		componentWillUnmount() {
			axios.interceptors.request.eject(this.requestInterceptor);
			axios.interceptors.response.eject(this.responseInterceptor);
		}

		errorConfirmedHandler = () => {
			this.setState({ error: null })
		}

		render() {
			const error = this.state.error;
			return (
				<>
					<Modal show={error} modalClosed={this.errorConfirmedHandler}>
						{error ? error.message : null}
					</Modal>
					<WrappedComponent {...this.props} />
				</>
			);
		}

	}
};

export default withErrorHandler;
