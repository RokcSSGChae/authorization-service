package ggitlab.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ggitlab.service.FindPasswordService;

@RestController
public class FindPasswordController {
	
	@Autowired
	FindPasswordService findPasswordService;
	
	@GetMapping("/findPassword/{email}")
	public String findPassword(@PathVariable String email) {
		try {
			findPasswordService.sendMail(email);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
		
	}
}
