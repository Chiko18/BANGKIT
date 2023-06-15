const isEmail = (email) => {
	const regEx =
		/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return email.match(regEx) ? true : false;
};

const isPassword = (password) => {
	const regEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
	return password.match(regEx) ? true : false;
};

const isEmpty = (data) => {
	return data.trim() === "" ? true : false;
};

exports.validateSignup = (data) => {
	let errors = {};
	isEmpty(data.email)
		? (errors.email = "Must not be empty")
		: !isEmail(data.email) && (errors.email = "Must be a valid email address");
	isEmpty(data.password)
		? (errors.password = "Must not be empty")
		: !isPassword(data.password)
		? (errors.password =
				"Password at least 8 characters and contains uppercase, lowercase, and number")
		: data.password !== data.confirmPassword &&
		  (errors.confirmPassword = "Passwords must match");
	isEmpty(data.name) && (errors.name = "Must not be empty");
	isEmpty(data.address) && (errors.address = "Must not be empty");

	return {
		valid: Object.keys(errors).length === 0 ? true : false,
		errors,
	};
};

exports.validateLogin = (data) => {
	let errors = {};
	isEmpty(data.email)
		? (errors.email = "Must not be empty")
		: !isEmail(data.email) && (errors.email = "Must be a valid email address");
	isEmpty(data.password) && (errors.password = "Must not be empty");

	return {
		valid: Object.keys(errors).length === 0 ? true : false,
		errors,
	};
};
