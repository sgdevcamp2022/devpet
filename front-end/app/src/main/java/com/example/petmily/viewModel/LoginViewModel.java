package com.example.petmily.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.petmily.R;
import com.example.petmily.model.Access_Token;
import com.example.petmily.model.Chat;
import com.example.petmily.model.FailModel;
import com.example.petmily.model.Join;
import com.example.petmily.model.Login;
import com.example.petmily.model.LoginInterface;
import com.example.petmily.model.Place;
import com.example.petmily.model.PlaceRepository;
import com.example.petmily.model.Refresh_Token;
import com.example.petmily.model.TempInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;

public class LoginViewModel  extends AndroidViewModel {

    //ViewModel이 가진 데이터

    LoginInterface logininterface;
    Call<Login> call;
    Call<Join> join;
    Call<TempInterface> access_tokenCall;


    private LiveData<List<Place>> allPlaces;
    private PlaceRepository placeRepository=new PlaceRepository();



    public LoginViewModel(@NonNull Application application) {
        super(application);


        String URL_test = "https://53210263-048a-4162-a41f-bc759625d1a6.mock.pstmn.io/profile/chs0476@naver.com";

        String URL = "https://53210263-048a-4162-a41f-bc759625d1a6.mock.pstmn.io/oauth/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_test)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        logininterface = retrofit.create(LoginInterface.class);



    }



    public LiveData<List<Place>> findAll(){
        return allPlaces;
    }


    public void join(Join userdata)
    {
        join = logininterface.createUser(userdata);
        join.enqueue(new Callback<Join>(){
            @Override
            public void onResponse(Call<Join> call, Response<Join> response) {

                if(response.code()==200)//성공
                {

                }
                else if ((response.code()==4000))//이메일 중복
                {

                }
                else if((response.code()==4001))//닉네임 중복
                {

                }


                Join result = response.body();
                response.body();
                if(!response.isSuccessful())
                {
                    Log.e("결과 테스트 : ", response.code()+"");
                }

                Log.e("결과 테스트 : ", response.errorBody().toString());


            }

            @Override
            public void onFailure(Call<Join> call, Throwable t) {
                Log.e("결과 테스트 : ", "실패");
            }

        });
    }

    public int login(String username, String password, String phone, String age, String gender, String nickname)
    {
        int code = 0;


        /*



        //Login login = new Login(username,password,phone,age,gender,nickname);
        Map<String, String> login = new HashMap<String, String>();
        login.put("username", username);

        call = logininterface.login(login);


        call.enqueue(new Callback<Login>(){
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login result = response.body();
                if(!response.isSuccessful())
                {
                    if(response.code()==200)
                    {
                        //code = 200;
                        Log.e("결과 테스트 성공 : ", response.code()+"");
                    }

                }

                Log.e("결과 테스트 : ", response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("결과 테스트 : ", "실패");
            }

        });

         */
        return code;


    }



    public int kakao(String username, String name, String password)
    {
        int code = 0;
        Join join = new Join(username, name, password);
        join = (Join) logininterface.createKakao(join);


        /*
        로직 카카오 로그인 완료 후 이메일, 이름, 비밀번호(카톡 고유id) 을 회원가입 서버로 전송
        완료받고 바로 로그인 진행(이메일 로그인과 동일한 메소드 사용)
        회원가입 페이지 시작 후 페이지에 추가정보 기입
        저장 버튼을 누르면 새로운 경로로 데이터 전달
         */

        return code;
    }

    public void accessToken(String token)
    {
        Access_Token accessToken = new Access_Token(token);
        access_tokenCall = logininterface.accessToken(accessToken);


        access_tokenCall.enqueue(new Callback<TempInterface>(){
            @Override
            public void onResponse(Call<TempInterface> call, Response<TempInterface> response) {
                Access_Token result =(Access_Token) response.body();

                if(!response.isSuccessful())
                {
                    if(response.code()==200)
                    {
                        //code = 200;
                        Log.e("결과 테스트 성공 : ", response.code()+"");
                    }

                }
                if(result.getToken().equals("4004"))//엑세스 토큰 만료
                {


                }
                else if(result.getToken().equals("200"))
                    Log.e("결과 테스트 : ", response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<TempInterface> call, Throwable t) {
                Log.e("결과 테스트 : ", "실패");
            }

        });


    }
    public void refreshToken(String token)
    {
        Access_Token accessToken = new Access_Token(token);
        access_tokenCall = logininterface.accessToken(accessToken);


        access_tokenCall.enqueue(new Callback<TempInterface>(){
            @Override
            public void onResponse(Call<TempInterface> call, Response<TempInterface> response) {
                if (response.body() instanceof FailModel)
                {
                    FailModel result =  (FailModel)response.body();
                    failFunction(result);
                }
                else
                {
                    Refresh_Token result = (Refresh_Token)response.body();
                    successFunction(result);
                }
            }

            @Override
            public void onFailure(Call<TempInterface> call, Throwable t) {
                Log.e("결과 테스트 : ", "실패");
            }

        });
    }
    public void successFunction(Refresh_Token result)
    {


        /*
        failFunction();
        return;


        if(result.getToken())
            result.getMessage();
        if(response.code()==200)
        {
            result.getToken();               //code = 200;
            Log.e("결과 테스트 성공 : ", response.code()+"");
        }
        else if(response.code()==4010)
        {
            result.getMessage();
        }

         */


        //Log.e("결과 테스트 : ", response.errorBody().toString());
    }

    public void failFunction(FailModel result)
    {

    }







}


