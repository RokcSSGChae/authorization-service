package ggitlab.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import ggitlab.service.SignOutService;

@Controller
public class SignOutController {
	
	@Autowired
	SignOutService signOutService;
	
	@GetMapping("/signout")
	public RedirectView signOut(HttpServletRequest request, HttpServletResponse response) {
		Cookie accessToken = new Cookie("authorization", null);
		accessToken.setMaxAge(0);
		Cookie id = new Cookie("id", null);
		id.setMaxAge(0);
		response.addCookie(accessToken);
		response.addCookie(id);
		return new RedirectView("/");
	}
}
