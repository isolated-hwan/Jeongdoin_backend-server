package com.kosa.jungdoin.security;

import com.kosa.jungdoin.common.exception.CustomException;
import com.kosa.jungdoin.entity.BaseMember;
import com.kosa.jungdoin.member.repository.BaseMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kosa.jungdoin.common.exception.CustomExceptionCode.ALREADY_EXIST_USER;
import static com.kosa.jungdoin.common.exception.CustomExceptionCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	private final BaseMemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BaseMember user = memberRepository.findByMemberOAuthId(username)
				.orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_USER.name()));
		return CustomUserDetails.of(user);
	}

	@Transactional
	public void createUser(CustomUserDetails user) {
		// 사용자가 (이미) 있으면 생성할수 없다.
		if (this.userExists(user.getMemberOAuthId()))
			throw new CustomException(ALREADY_EXIST_USER);

		memberRepository.save((user).newEntity());
	}

	public boolean userExists(String oauthId) {
		return memberRepository.existsByMemberOAuthId(oauthId);
	}
}