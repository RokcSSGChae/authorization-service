package ggitlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GgitlabController {

	@RequestMapping("/")
	public String home() {
		return "index.html";
	}
	
	@RequestMapping("/main")
	public String main() {
		return "main.html";
	}
}
