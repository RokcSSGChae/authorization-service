package ggitlab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ggitlab.domain.Member;

public class RegisterVaildatior implements Validator {

	private static final String ID_REGEX = "\\w+";
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern idPattern;
	private Pattern emailPattern;

	public RegisterVaildatior() {
		idPattern = Pattern.compile(ID_REGEX);
		emailPattern = Pattern.compile(EMAIL_REGEX);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Member member = (Member) target;
		Matcher matcher = null;
		if (member.getId() == null || member.getId().trim().isEmpty()) {
			errors.rejectValue("id", "required", "ID is required.");
		} else {
			matcher = idPattern.matcher(member.getId());
			if (!matcher.matches()) {
				errors.rejectValue("id", "bad", "Please create a username with only alphanumeric characters.");
			}
		}
		matcher = emailPattern.matcher(EMAIL_REGEX);
		if (!matcher.matches()) {
			errors.rejectValue("email", "bad", "Please provide a valid email address.");
		}
		if (member.getPassword().length() < 8) {
			errors.rejectValue("password", "bad", "Minimum length is 8 characters.");
		}
	}
}
