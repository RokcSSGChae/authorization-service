package ggitlab.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ggitlab.domain.Member;
import ggitlab.domain.SignInRequest;
import ggitlab.error.InvalidSignInException;
import ggitlab.service.JwtService;
import ggitlab.service.SignInService;
import ggitlab.utils.SignInValidator;

@RestController
public class SignInController {

	@Autowired
	SignInService signInService;

	@Autowired
	JwtService jwtService;

	@Autowired
	SignInValidator signInValidator;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@PostMapping("/signin")
	public Map<String, String> signIn(@ModelAttribute SignInRequest signReq, BindingResult result,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		signInValidator.validate(signReq, result);
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			return map;
		}
		try {
			Member loginMember = signInService.signIn(signReq.getId(), signReq.getPassword());
			String accessToken = jwtService.createAccessToken(loginMember.getId());
			String refreshToken = jwtService.createRefreshToken(loginMember.getId());
			response.addCookie(new Cookie("authorization", accessToken));
			response.addCookie(new Cookie("id", signReq.getId()));
			redisTemplate.opsForValue().set(signReq.getId(), refreshToken);
			map.put("redirect", "/main");
		} catch (InvalidSignInException e) {
			map.put("failure", "Invalid id or password");
		}
		return map;
	}
}
