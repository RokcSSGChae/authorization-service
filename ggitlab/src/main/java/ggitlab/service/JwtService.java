package ggitlab.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Service;

import ggitlab.domain.Member;
import ggitlab.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	private static final Long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 10;
	private static final Long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 24 * 2;
	private static final String HEADER_REGISTER_DATE_KEY = "regDate";
	private static final String CLAIM_ID_KEY = "id";
	private static final byte[] SECRET_KEY = SecurityUtils.getSalt().getBytes(StandardCharsets.UTF_8);

	public Claims extractAllClaims(String jwt) throws ExpiredJwtException {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(jwt)
				.getBody();
	}

	public String getId(String jwt) {
		return extractAllClaims(jwt).get(CLAIM_ID_KEY, String.class);
	}

	public Boolean isTokenExpired(String jwt) {
		final Date expiration = extractAllClaims(jwt).getExpiration();
		return expiration.before(new Date());
	}

	public String createAccessToken(String id) {
		return createToken(id, ACCESS_TOKEN_EXPIRE_TIME);
	}

	public String createRefreshToken(String id) {
		return createToken(id, REFRESH_TOKEN_EXPIRE_TIME);
	}

	public String createToken(String id, long expireTime) {
		Claims claims = Jwts.claims();
		claims.put(CLAIM_ID_KEY, id);
		claims.setExpiration(new Date(System.currentTimeMillis() + expireTime));

		String jwt = Jwts.builder()
				.setHeaderParam(HEADER_REGISTER_DATE_KEY, System.currentTimeMillis())
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
		return jwt;
	}
}
