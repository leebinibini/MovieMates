package com.nc13.moviemates.util;

import com.nc13.moviemates.absent.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module.Provide;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.nc13.moviemates.absent.OAuth2UserInfo;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.enums.Provider;
import com.nc13.moviemates.enums.Role;
import com.nc13.moviemates.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {



    private final UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    //함수종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 인증 제공자 식별
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
       /* System.out.println("로그인 제공자: " + registrationId);
        System.out.println("로그인 제공자 attributes: " + attributes);*/
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, attributes);

        log.info(oAuth2UserInfo.toString());

        if (oAuth2UserInfo == null) {
            throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자입니다.");
        }

        //각 서비스에 맞게 정보를 가져옴
        //OAuth2UserInfo는 직접 만든 인터페이스 이고,
        //각 브랜드별로 구현체를 만듬
     /*   OAuth2UserInfo oAuth2UserInfo;*/

      /*  if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = OAuth2UserInfo.builder()
                    .email(oAuth2UserInfo.email())
                    .nickname(oAuth2UserInfo.nickname())
                    .profileImageUrl(oAuth2UserInfo.profileImageUrl())
                    .provider(Provider.GOOGLE)
                    .build();
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = OAuth2UserInfo.builder()
                    .provider(Provider.FACEBOOK)
                    .build();
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = OAuth2UserInfo.builder()
                    .provider(Provider.FACEBOOK)
                    .build();
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = OAuth2UserInfo.builder()
                    .provider(Provider.FACEBOOK)
                    .build();
        }  else {
            System.out.println("우리는 구글과 페이스 북만 지원해요");
            return null;
        }*/


        // 사용자 정보 추출

        String email = oAuth2UserInfo.email();
        String nickname = oAuth2UserInfo.nickname();
        String profileImageUrl = oAuth2UserInfo.profileImageUrl();
        System.out.println("추출된 이메일: " + email);
        System.out.println("추출된 닉네임: " + nickname);
        System.out.println("사진:" + profileImageUrl);

        // UserEntity 조회 및 저장


        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            System.out.println(email + " 로그인이 최초입니다.");
            //강제 회원가입
            //회원 DB에 추가함
            //password 가 null 이기 때문에 일반적인 회원가입을 할 수가 없음
            userEntity = oAuth2UserInfo.toEntity();
            userRepository.save(userEntity);
        } else {
            System.out.println(oAuth2UserInfo.provider() +" 로그인을 이미 한 적이 있습니다.");
        }
        System.out.println("구글 로그인 완료");
        return new UserPrincipal(userEntity,oAuth2User.getAttributes());

    }
}