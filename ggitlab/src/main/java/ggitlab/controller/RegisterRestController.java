package ggitlab.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.Registration.Signing;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ggitlab.domain.Member;
import ggitlab.domain.MemberBuilder;
import ggitlab.domain.RegisterRequest;
import ggitlab.error.InvalidSignInException;
import ggitlab.service.JwtService;
import ggitlab.service.RegisterService;
import ggitlab.service.SignInService;
import ggitlab.utils.RegisterValidator;
import ggitlab.utils.SecurityUtils;

@RestController
public class RegisterRestController {

	@Autowired
	RegisterService registerService;

	@Autowired
	RegisterValidator registerValidator;

	@PostMapping(path = "/register")
	public Map<String, String> register(@ModelAttribute RegisterRequest regReq, BindingResult result) {
		registerValidator.validate(regReq, result);
		Map<String, String> map = new HashMap<String, String>();

		if (result.hasErrors()) {
			List<FieldError> list = result.getFieldErrors();
			for (FieldError error : list) {
				map.put(error.getField(), error.getDefaultMessage());
			}
		}

		if (registerService.existsById(regReq.getId())) {
			map.put("id", "id is already taken.");
		} else {
			String salt = SecurityUtils.getSalt();
			String password = SecurityUtils.getEncrypted(salt, regReq.getPassword().getBytes());
			MemberBuilder memberBuilder = new MemberBuilder()
					.id(regReq.getId())
					.password(password)
					.salt(salt)
					.email(regReq.getEmail()).type('Y')
					.registerDate(new Date(System.currentTimeMillis()))
					.modifiedDate(new Date(System.currentTimeMillis()));
			Member member = memberBuilder.build();
			registerService.addMember(member);
			map.put("success", "register success.");
		}
		return map;
	}
}
