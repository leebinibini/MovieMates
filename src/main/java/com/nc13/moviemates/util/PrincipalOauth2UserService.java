package com.nc13.moviemates.util;

import com.nc13.moviemates.absent.UserPrincipal;
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

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {



    private UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    //함수종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //각 서비스에 맞게 정보를 가져옴
        //OAuth2UserInfo는 직접 만든 인터페이스 이고,
        //각 브랜드별로 구현체를 만듬
        OAuth2UserInfo oAuth2UserInfo;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = OAuth2UserInfo.builder()
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
        }


        String nickname = oAuth2UserInfo.nickname();
        String email = oAuth2UserInfo.email();


        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            System.out.println(email + " 로그인이 최초입니다.");
            //강제 회원가입
            //회원 DB에 추가함
            //password 가 null 이기 때문에 일반적인 회원가입을 할 수가 없음
            userEntity = UserEntity.builder()
                    .nickname(nickname)
                    .email(email)
                    .role(Role.USER)
                    .provider(oAuth2UserInfo.provider())
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println(oAuth2UserInfo.provider() +" 로그인을 이미 한 적이 있습니다.");
        }

        return new UserPrincipal(userEntity,oAuth2User.getAttributes());
    }
}