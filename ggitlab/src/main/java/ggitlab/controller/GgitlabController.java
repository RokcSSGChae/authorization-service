package ggitlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GgitlabController {

	@GetMapping("/")
	public String home() {
		return "index.html";
	}
	
	@GetMapping("/main")
	public String main() {
		return "main.html";
	}
	
	@GetMapping("/changepassword")
	public String view() {
		return "changepassword.html";
	}
}
