package com.kosa.jungdoin.member.repository;

import com.kosa.jungdoin.entity.BaseMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseMemberRepository extends JpaRepository<BaseMember, Long> {

    boolean existsByMemberOAuthId(String oauthId);

    Optional<BaseMember> findByMemberOAuthId(String memberOAuthId);
}
