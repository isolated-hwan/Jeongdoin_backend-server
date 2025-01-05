package com.kosa.jungdoin.security.oauth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

import static com.kosa.jungdoin.security.oauth.OAuth2Provider.KAKAO;

@Slf4j
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Attributes {
	public static final String NAME_ATTRIBUTE_KEY = "id"; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미

	// 소셜 타입별 로그인 유저 정보
	private String id;
	private String provider;
	private String nickname;
	private String profileImgUrl;

	@Builder
	public OAuth2Attributes(String id, String provider, String nickname, String profileImgUrl) {
		this.id = id;
		this.provider = provider;
		this.nickname = nickname;
		this.profileImgUrl = profileImgUrl;
	}

	/**
	 * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
	 * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
	 * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
	 * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
	 */
	public static OAuth2Attributes of(String provider, OAuth2User oAuth2User) {
		if (provider.equals(KAKAO.getProvider())) {
			return ofKakao(oAuth2User);
		}

		return null;
	}

	private static OAuth2Attributes ofKakao(OAuth2User oAuth2User) {
		Map<String, String> properties = oAuth2User.getAttribute("properties");
		return new OAuth2Attributes(
			oAuth2User.getAttribute("id").toString(),
			KAKAO.getProvider(),
			properties.get("nickname"),
			properties.get("profile_image")
		);
	}

	// 추후 네이버, 구글 등 추가함.

	// OAuth2Attributes 의 정보를 기반으로 Map 으로 변환하는 메서드
	public Map<String, Object> convertToMap() {
		Map<String, Object> changedAttributes = new HashMap<>();
		changedAttributes.put("id", this.id);
		changedAttributes.put("provider", this.provider);
		changedAttributes.put("nickname", this.nickname);
		changedAttributes.put("profile_img_url", this.profileImgUrl);

		return changedAttributes;
	}
}