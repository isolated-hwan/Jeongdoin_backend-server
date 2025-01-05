package com.kosa.jungdoin.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosa.jungdoin.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);

	Optional<Member> findByMemberOAuthId(String oauthId);

	Boolean existsByMemberOAuthId(String oauthId);
}
