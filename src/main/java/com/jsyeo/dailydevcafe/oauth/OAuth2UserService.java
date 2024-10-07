package com.jsyeo.dailydevcafe.oauth;

import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.domain.member.MemberType;
import com.jsyeo.dailydevcafe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);

        String oauthClientName = request.getClientRegistration().getClientName();

        Member member = null;
        String nickname = null;
        String name = null;
        String email = null;

        if (oauthClientName.equals("kakao")) {
            nickname = "kakao_" + oAuth2User.getAttributes().get("id");
            name = oAuth2User.getAttributes().get("properties").toString();
            name = name.substring(10, name.length()-1);
            email = nickname + "@email.com";
            member = new Member(name, nickname, email, MemberType.KAKAO);
        }

        if (!memberRepository.existsByNickname(nickname)) {
            memberRepository.save(member);
        }

        return new CustomOAuth2User(email);
    }
}
