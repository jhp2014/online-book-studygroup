package com.project.bookstudy.security.service;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.security.dto.MemberOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class KakaoOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final DefaultOAuth2UserService userService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = userService.loadUser(userRequest);

        //Save or Update at DB
        Map<String, Object> memberInfo = oAuth2User.getAttribute("kakao_account");
        String email = (String) memberInfo.get("email");
        String name = (String) ((Map) memberInfo.get("profile")).get("nickname");

        Member member = saveOrUpdate(email, name);

        /**
         * 기존 로직: Success Handler에 oAuth2User가 전달된다.
         *
         * 변경 로직: oAuth2User를 memberOAuth2User로 감싸서 Success Handler에 전달
         * 이렇게 하는 경우, Success Handler에서 Member Entity의 정보를 활용 할 수 있다.
         */
        MemberOAuth2User memberOAuth2User = new MemberOAuth2User(member, oAuth2User);
        return memberOAuth2User;
    }

    private Member saveOrUpdate(String email, String name) {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isEmpty()) {
            Member member = memberRepository.save(Member.builder()
                    .email(email)
                    .name(name)
                    .build());
            return member;
        }
        return memberOptional.get();
    }
}

