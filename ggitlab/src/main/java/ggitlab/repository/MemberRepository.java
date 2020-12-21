package ggitlab.repository;

import org.springframework.data.repository.Repository;

import ggitlab.domain.Member;

public interface MemberRepository extends Repository<Member, String> {
	
	Member save(Member member);
	
	void deleteById(String id);
	
	boolean existsById(String id);
	
	Member findById(String id);
	
	Member findByEmail(String email);
}
