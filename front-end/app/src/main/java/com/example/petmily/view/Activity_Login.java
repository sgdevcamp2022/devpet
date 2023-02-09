package com.example.petmily.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.petmily.R;
import com.example.petmily.databinding.ActivityLoginBinding;
import com.example.petmily.viewModel.AuthenticationViewModel;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class Activity_Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    String TAG = "테스트\t";
    Function2<OAuthToken, Throwable, Unit> callback;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setAuthJoinEmail(this);


        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if(loginViewModel.login(binding.email.getText().toString())==0)
                {
                    Log.e("로그인 테스트 \t" ,"로그인 실패");
                }
                else if(loginViewModel.login(binding.email.getText().toString())==1)
                {

                    String st1 = "엑세스토큰";
                    String st2 = "리프레쉬토큰";

                    //이메일 틀림 -> 4003 : 400
                    //비밀번호 틀린 ->4003 : 400

                    액세스 만료 4004
                    리프레쉬 만료 : Invalid refresh token


                    finish();
                }

                 */
            }
        });

        binding.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), Activity_Join.class);
                startActivity(intent);
            }
        });

        callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {
                    kakaologin();
                }
                if (throwable != null) {
                    Log.e("카카오 로그인 실패 : ", throwable.getMessage());
                }

                return null;
            }
        };
        // 로그인 버튼
        binding.kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Activity_Login.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(Activity_Login.this, callback);
                    //loginViewModel

                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(Activity_Login.this, callback);
                }
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });
                UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                });

            }
        });


    }
    private void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면
                if (user!=null){
                    Log.e(TAG,"invoke: id\t" + user.getKakaoAccount());

                    // 유저의 아이디
                    Log.e(TAG,"invoke: id\t" + user.getId());
                    // 유저의 어카운트정보에 이메일
                    Log.e(TAG,"invoke: nickname\t" + user.getKakaoAccount().getEmail());
                    // 유저의 어카운트 정보의 프로파일에 닉네임
                    Log.e(TAG,"invoke: email\t" + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 어카운트 파일의 성별
                    Log.e(TAG,"invoke: gerder\t" + user.getKakaoAccount().getGender());
                    // 유저의 어카운트 정보에 나이
                    Log.e(TAG,"invoke: age\t" + user.getKakaoAccount().getAgeRange());

                }else {
                    Log.e(TAG,"로그인 되어있지 않습니다.");
                }
                return null;
            }
        });
    }

    private void kakaologin(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user!=null){

                    intent.putExtra("provider", "kakao");
                    intent.putExtra("username", user.getKakaoAccount().getEmail());
                    intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                    intent.putExtra("age", user.getKakaoAccount().getAgeRange()+"");
                    intent.putExtra("password", user.getId()+"");
                    startActivity(intent);
                }else {
                    Log.d(TAG,"로그인 되어있지 않습니다.");
                }
                return null;
            }
        });
    }
}
