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
import com.example.petmily.viewModel.LoginViewModel;
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
        binding.setJoin(this);

        intent = new Intent(this, Activity_Join.class);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
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
                startActivity(intent);
            }
        });

        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if(oAuthToken != null) {
                    kakaologin();
                }
                if (throwable != null) {

                }

                return null;
            }
        };
        // 로그인 버튼
        binding.btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
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
                        updateKakaoLoginUi();
                        return null;
                    }
                });
                UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });

            }
        });

        updateKakaoLoginUi();

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
                    // 로그인이 되어 있지 않다면 위와 반대로
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
                // 로그인이 되어있으면
                if (user!=null){

                    // 유저의 아이디
                    Log.d(TAG,"invoke: id\t" + user.getId());



                    intent.putExtra("username", user.getKakaoAccount().getEmail());
                    intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                    intent.putExtra("age", user.getKakaoAccount().getAgeRange());
                    intent.putExtra("password", user.getId());
                    startActivity(intent);
                }else {
                    // 로그인이 되어 있지 않다면 위와 반대로
                    Log.d(TAG,"로그인 되어있지 않습니다.");
                }
                return null;
            }
        });
    }
}
