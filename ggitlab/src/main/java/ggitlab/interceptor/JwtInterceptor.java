package ggitlab.interceptor;

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

	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String accessToken = request.getHeader(HEADER_AUTH);
		if (accessToken != null) {
			String id = jwtService.getId(accessToken);
			if (!jwtService.isTokenExpired(accessToken)) {
				return true;
			}
			String refreshToken = redisTemplate.opsForValue().get(id);
			if (!jwtService.isTokenExpired(refreshToken)) {
				String newAccessToken = jwtService.createAccessToken(id);
				response.setHeader(HEADER_AUTH, newAccessToken);
				return true;
			}
		}
		throw new UnauthorizedException();
	}
}
