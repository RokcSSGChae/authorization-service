package ggitlab.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ggitlab.domain.FindPasswordRequest;
import ggitlab.service.FindPasswordService;
import ggitlab.utils.FindPasswordValidator;

@RestController
public class FindPasswordController {

	private static final String MESSAGE_KEY = "message";
	private static final String MESSAGE_VALUE = "If your email address exists in our database, you will receive a password recovery link at your email address in a few minutes.";

	@Autowired
	FindPasswordService findPasswordService;

	@Autowired
	FindPasswordValidator findPasswordValidator;

	@PostMapping("/findpassword")
	public Map<String, String> findPassword(@ModelAttribute FindPasswordRequest findPasswordRequest,
			BindingResult result) {
		Map<String, String> map = new HashMap<String, String>();
		findPasswordValidator.validate(findPasswordRequest, result);
		if (result.hasErrors()) {
			List<FieldError> list = result.getFieldErrors();
			for (FieldError error : list) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			return map;
		}

		try {
			findPasswordService.sendMail(findPasswordRequest.getEmail());	
		} catch (MessagingException e) {
			// Do nothing;
		}
		map.put(MESSAGE_KEY, MESSAGE_VALUE);
		return map;
	}
}
