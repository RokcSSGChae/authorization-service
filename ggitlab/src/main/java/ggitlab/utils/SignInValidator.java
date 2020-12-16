package ggitlab.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ggitlab.domain.Member;

public class SignInValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "id", "required", "This field is required.");
		ValidationUtils.rejectIfEmpty(errors, "password", "required", "This field is required.");		
	}
}
