package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.dto.SignUpRequest;
import com.example.shoh_oauth.data.entity.User;
import com.example.shoh_oauth.data.entity.type.UserType;
import com.example.shoh_oauth.exception.DuplicateUserException;
import com.example.shoh_oauth.exception.SimpleFieldError;
import com.example.shoh_oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(SignUpRequest signUpRequest) {
        checkDuplicateEmail(signUpRequest.getUsername());

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .name(signUpRequest.getName())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .age(signUpRequest.getAge())
                .gender(signUpRequest.getGender())
                .phone(signUpRequest.getPhone())
                .provider(null)
                .type(UserType.DEFAULT)
                .build();

        userRepository.save(user);
    }

    @Override
    public void saveKakaoUser(String email, String nickname) {
        User user = User.builder()
                .username(nickname)
                .nickname(nickname)
                .password(passwordEncoder.encode("1234"))
                .type(UserType.DEFAULT)
                .build();

        User kakaoUser = userRepository.save(user);
        log.info(kakaoUser.getUsername());
    }

    public User checkRegisteredUser(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        Assert.state(optUser.isPresent(), "가입되지 않은 회원입니다.");
        return optUser.get();
    }

    public void checkDuplicateEmail(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateUserException("사용중인 이메일 입니다.", new SimpleFieldError("email", "사용중인 이메일 입니다."));
    }

    public void checkLoginEmail(Map<String, String> parameters) {
        if ( parameters.get("provider") != null ) {
            updateKaKaoLoginInfo(parameters);
        }
    }

    public void updateKaKaoLoginInfo(Map<String, String> parameters) {
       User user = userRepository.findByUsername(String.valueOf(parameters.get("username"))).get();
       user.setNickname(String.valueOf(parameters.get("nickname")));
       user.setGender(String.valueOf(parameters.get("gender")));
       user.setAge(String.valueOf(parameters.get("age")));
       user.setPhone(String.valueOf(parameters.get("phone")));
       user.setProvider(String.valueOf(parameters.get("provider")));
       userRepository.save(user);
    }

}
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<OAuth2AccountDTO> getOAuth2Account(String username) {
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        if (!optionalUser.isPresent() || optionalUser.get().getSocial() == null) return Optional.empty();
//        return Optional.of(optionalUser.get().getSocial().toDTO());
//    }

//    @Override
//    public void updateProfile(String username, UpdateProfileRequest updateProfileRequest){
//
//        User user = userRepository.findByUsername(username).get();
//
//        //이름이 변경되었는지 체크
//        if (!user.getName().equals(updateProfileRequest.getName()))
//            user.updateName(updateProfileRequest.getName());
//
//        //이메일이 변경되었는지 체크
//        if (!user.getEmail().equals(updateProfileRequest.getEmail())) {
//            checkDuplicateEmail(updateProfileRequest.getEmail());
//            user.updateEmail(updateProfileRequest.getEmail());
//        }
//    }


//    @Override
//    public UserDetails loginOAuth2User(String provider, OAuth2Token oAuth2Token, OAuth2UserInfo userInfo) {
//
//        Optional<OAuth2Account> optOAuth2Account = oAuth2AccountRepository.findByProviderAndProviderId(provider, userInfo.getId());
//        User user = null;
//
//        //가입된 계정이 존재할때
//        if (optOAuth2Account.isPresent()) {
//            OAuth2Account oAuth2Account = optOAuth2Account.get();
//            user = oAuth2Account.getUser();
//            //토큰 업데이트
//            oAuth2Account.updateToken(oAuth2Token.getToken(), oAuth2Token.getRefreshToken(), oAuth2Token.getExpiredAt());
//        }
//        //가입된 계정이 존재하지 않을때
//        else {
//            //소셜 계정 정보 생성
//            OAuth2Account newAccount = OAuth2Account.builder()
//                    .provider(provider)
//                    .providerId(userInfo.getId())
//                    .token(oAuth2Token.getToken())
//                    .refreshToken(oAuth2Token.getRefreshToken())
//                    .tokenExpiredAt(oAuth2Token.getExpiredAt()).build();
//            oAuth2AccountRepository.save(newAccount);
//
//            //이메일 정보가 있을때
//            if (userInfo.getEmail() != null) {
//                // 같은 이메일을 사용하는 계정이 존재하는지 확인 후 있다면 소셜 계정과 연결시키고 없다면 새로 생성한다
//                user = userRepository.findByEmail(userInfo.getEmail())
//                        .orElse(User.builder()
//                                .username(provider + "_" + userInfo.getId())
//                                .name(userInfo.getName())
//                                .email(userInfo.getEmail())
//                                .type(UserType.OAUTH)
//                                .build());
//            }
//            //이메일 정보가 없을때
//            else {
//                user = User.builder()
//                        .username(provider + "_" + userInfo.getId())
//                        .name(userInfo.getName())
//                        .type(UserType.OAUTH)
//                        .build();
//            }
//
//            //새로 생성된 유저이면 db에 저장
//            if (user.getId() == null)
//                userRepository.save(user);
//
//            //연관관계 설정
//            user.linkSocial(newAccount);
//        }
//
//        return UserDetailsImpl.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .name(user.getName())
//                .email(user.getEmail())
//                .type(user.getType())
//                .authorities(user.getAuthorities()).build();
//    }
