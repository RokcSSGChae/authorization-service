package ggitlab.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ggitlab.domain.ChangePasswordRequest;
import ggitlab.service.ChangePasswordService;
import ggitlab.utils.ChangePasswordValidator;

@Controller
public class ChangePasswordController {
	
	@Autowired
	ChangePasswordService changePasswordService;

	@Autowired
	ChangePasswordValidator changePasswordValidator;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@PostMapping("/changepassword/update")
	@ResponseBody
	public Map<String, String> changePassword(@ModelAttribute ChangePasswordRequest changePasswordRequest,
			BindingResult result, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		changePasswordValidator.validate(changePasswordRequest, result);
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			return map;
		}
		String id = null;
		try {
			changePasswordService.updatePassword(id, changePasswordRequest.getPassword());
			map.put("redirect", "/");
		} catch (Exception e) {
			map.put("failure", "invalid");
		}
		return map;
	}
}
