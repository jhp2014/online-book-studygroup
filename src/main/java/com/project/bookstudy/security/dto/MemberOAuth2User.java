package com.project.bookstudy.security.dto;

import com.project.bookstudy.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * 역할: oAuth2User를 memberOAuth2User로 감싸서 Success Handler에 전달
 * 이렇게 하는 이유는 나중에 Success Handler에서 Member Entity의 정보를 활용할 수 있도록 하기 위함이다.
 */
@Getter
@RequiredArgsConstructor
public class MemberOAuth2User implements OAuth2User {

    private final Member member;
    private final OAuth2User oAuth2User;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    @Override
    public int hashCode() {
        return oAuth2User.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return oAuth2User.equals(obj);
    }

    @Override
    public String toString() {
        return oAuth2User.toString();
    }
}
