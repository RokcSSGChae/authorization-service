package ggitlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ggitlab.repository.MemberRepository;

@Service
public class SignOutService {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	public void signOut(String id) {
		redisTemplate.delete(id);
	}
}
