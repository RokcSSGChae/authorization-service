package ggitlab.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import ggitlab.error.UnauthorizedException;
import ggitlab.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	private static final String COOKIE_AUTH_NAME = "authorization";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String accessToken = null;
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(COOKIE_AUTH_NAME)) {
				accessToken = cookie.getValue();
			}
		}
		if (accessToken != null) {
			if (!jwtService.isTokenExpired(accessToken)) {
				return true;
			}
			String id = jwtService.getId(accessToken);
			String refreshToken = redisTemplate.opsForValue().get(id);
			if (refreshToken != null && !jwtService.isTokenExpired(refreshToken)) {
				String newAccessToken = jwtService.createAccessToken(id);
				response.addCookie(new Cookie(COOKIE_AUTH_NAME, newAccessToken));
				return true;
			}
		}
		throw new UnauthorizedException();
	}
}
