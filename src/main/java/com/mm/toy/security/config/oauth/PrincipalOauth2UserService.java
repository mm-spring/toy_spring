package com.mm.toy.security.config.oauth;

import com.mm.toy.domain.user.entity.Role;
import com.mm.toy.domain.user.entity.User;
import com.mm.toy.domain.user.repository.UserRepository;
import com.mm.toy.presentation.payload.code.ErrorStatus;
import com.mm.toy.security.config.auth.PrincipalDetails;
import com.mm.toy.security.config.oauth.provider.GoogleUserInfo;
import com.mm.toy.security.config.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글로부터 회원 프로필을 받아옴.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else{
            throw new OAuth2AuthenticationException(ErrorStatus.AUTH_IS_NOT_PERMITTED.getMessage());
        }

        String provider = Objects.requireNonNull(oAuth2UserInfo).getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = oAuth2UserInfo.getUsername();
        String password = bCryptPasswordEncoder.encode("Password"); // 기본 비밀번호

        User user = userRepository.findByEmail(email)
                .orElseGet(()->{
                    return User.builder()
                            .username(username)
                            .password(password)
                            .email(email)
                            .role(Role.USER)
                            .provider(provider)
                            .provideId(providerId)
                            .build();

                });
        userRepository.save(user);
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
