package ggitlab.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import ggitlab.error.UnauthorizedException;
import ggitlab.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);
		if (token != null && jwtService.isTokenExpired(token)) {
			return true;
		} else {
			throw new UnauthorizedException();
		}
	}
}
