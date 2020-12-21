package ggitlab.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ggitlab.domain.ChangePasswordRequest;

@Component
public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordRequest changePasswordRequest = (ChangePasswordRequest) target;
		if (changePasswordRequest.getPassword().length() < 8) {
			errors.rejectValue("password", "bad", "Minimum length is 8 characters.");
		}
		if (!changePasswordRequest.getPassword().equals(changePasswordRequest.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "bad", "Please retype the password.");
		}
	}
}
