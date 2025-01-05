package com.kosa.jungdoin.security.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    // OAuth2 요청을 통해 유저를 불러오는 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // service 내에서 OAuth2 정보를 가져온다

        // OAuth2 서비스 id (provider) -> 요청을 보낸 클라이언트의 소셜 사이트 ID를 가져옵니다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2User 에서 받은 정보를 provider 에 기반하여 재설정
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(registrationId, oAuth2User);

        // 앞서 받은 정보를 Map 형태로 변환
        Map<String, Object> attributes = oAuth2Attributes.convertToMap();

        log.info(oAuth2Attributes.toString());

        // 최종적으로 OAuth2User 를 반환해준다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("REGULAR")), attributes,
                OAuth2Attributes.NAME_ATTRIBUTE_KEY
        );
    }
}
