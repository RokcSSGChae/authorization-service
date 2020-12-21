package ggitlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ggitlab.domain.Member;
import ggitlab.error.InvalidSignInException;
import ggitlab.repository.MemberRepository;
import ggitlab.utils.SecurityUtils;

@Service
public class SignInService {

	@Autowired
	MemberRepository memberRepository;

	public Member signIn(String id, String password) throws InvalidSignInException {
		Member member = memberRepository.findById(id);
		if (member == null) {
			throw new InvalidSignInException();
		}
		String salt = member.getSalt();
		String encryptedPassword = SecurityUtils.getEncrypted(salt, password.getBytes());
		if (!encryptedPassword.equals(member.getPassword())) {
			throw new InvalidSignInException();
		}
		return member;
	}
}
