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
	private static final byte[] SECRET_KEY = SecurityUtils.getSalt().getBytes(StandardCharsets.UTF_8);
	
	public Claims extractAllClaims(String jwt) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
				.getBody();
    }
	
	public String getId(String jwt) {
        return extractAllClaims(jwt).get("id", String.class);
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
        claims.put("id", id);
		
		String jwt = Jwts.builder()
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
		return jwt;
	}
}
