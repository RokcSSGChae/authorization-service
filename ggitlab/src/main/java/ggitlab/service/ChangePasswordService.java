package ggitlab.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ggitlab.domain.Member;
import ggitlab.domain.MemberBuilder;
import ggitlab.repository.MemberRepository;
import ggitlab.utils.SecurityUtils;

@Service
public class ChangePasswordService {

	@Autowired
	MemberRepository memberRepository;

	public Member updatePassword(String id, String password) {
		Member alreadyExist = memberRepository.findById(id);
		String newSalt = SecurityUtils.getSalt();
		Member updated = new MemberBuilder(alreadyExist)
				.salt(newSalt)
				.password(SecurityUtils.getEncrypted(newSalt, password.getBytes(StandardCharsets.UTF_8)))
				.build();
		memberRepository.deleteById(id);
		return memberRepository.save(updated);
	}
}
