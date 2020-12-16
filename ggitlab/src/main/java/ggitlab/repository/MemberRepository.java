package ggitlab.repository;

import org.springframework.data.repository.CrudRepository;

import ggitlab.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {
}
