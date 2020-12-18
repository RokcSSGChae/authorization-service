package ggitlab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ggitlab.domain.RegisterRequest;

@Component
public class RegisterValidator implements Validator {

	private static final String ID_REGEX = "\\w+";
	private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	private Pattern idPattern;
	private Pattern emailPattern;

	public RegisterValidator() {
		idPattern = Pattern.compile(ID_REGEX);
		emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterRequest regReq = (RegisterRequest) target;
		Matcher matcher = null;

		if (regReq.getId() == null || regReq.getId().trim().isEmpty()) {
			errors.rejectValue("id", "required", "ID is required.");
		} else {
			matcher = idPattern.matcher(regReq.getId());
			if (!matcher.matches()) {
				errors.rejectValue("id", "bad", "Please create a username with only alphanumeric characters.");
			}
		}

		if (regReq.getPassword().length() < 8) {
			errors.rejectValue("password", "bad", "Minimum length is 8 characters.");
		}

		matcher = emailPattern.matcher(regReq.getEmail());
		if (!matcher.matches()) {
			errors.rejectValue("email", "bad", "Please provide a valid email address.");
		}

		if (!regReq.getEmail().equals(regReq.getEmailConfirm())) {
			errors.rejectValue("emailConfirm", "bad", "Please retype the email address.");
		}
	}
}
