package ggitlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ggitlab.domain.Member;
import ggitlab.repository.MemberRepository;

@Service
public class RegisterService {

	@Autowired
	MemberRepository memberRepository;
	
	public boolean existsById(String id) {
		return memberRepository.existsById(id);
	}

	public void addMember(Member member) {
		memberRepository.save(member);
	}
}
