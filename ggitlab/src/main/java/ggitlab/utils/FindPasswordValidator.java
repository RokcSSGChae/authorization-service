package ggitlab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ggitlab.domain.FindPasswordRequest;

@Component
public class FindPasswordValidator implements Validator {

	private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	private Pattern emailPattern;

	public FindPasswordValidator() {
		emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FindPasswordRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FindPasswordRequest fpReq = (FindPasswordRequest) target;
		Matcher matcher = emailPattern.matcher(fpReq.getEmail());

		if (!matcher.matches()) {
			errors.rejectValue("email", "bad", "Please provide a valid email address.");
		}
	}
}
