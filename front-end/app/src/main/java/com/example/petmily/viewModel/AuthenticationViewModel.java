package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.petmily.model.data.auth.remote.JoinEmail;
import com.example.petmily.model.FailModel;
import com.example.petmily.model.data.auth.Login;
import com.example.petmily.model.data.auth.remote.API_Interface;
import com.example.petmily.model.Place;
import com.example.petmily.model.PlaceRepository;
import com.example.petmily.model.data.auth.Refresh_Token;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AuthenticationViewModel extends AndroidViewModel {


    private API_Interface logininterface;
    private Context context;
    private Call<Login> call;
    private Call<JoinEmail> join;
    //Call<TempInterface> access_tokenCall;


    private LiveData<List<Place>> allPlaces;
    private PlaceRepository placeRepository=new PlaceRepository();



    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()

                                //@Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .addHeader("Authorization", "Basic ZGV2OnBldA==")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();



        String URL = "http://10.0.2.2:8080/oauth/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        logininterface = retrofit.create(API_Interface.class);
        //join(new JoinEmail("이메일", "닉네임", "이름", "비밀번호","나이", "남자", "01021113250", ""));


    }

    public void join(JoinEmail userdata)
    {
        join = logininterface.createUser(userdata);
       // join.enqueue(new AuthCallback<>(context));

        /*
        join = logininterface.createUser(
                "email",
                "nickname",
                "name",
                "password",
                "age",
                "gender",
                "phone",
                ""
        );
         */

        join.enqueue(new Callback<JoinEmail>(){
            @Override
            public void onResponse(Call<JoinEmail> call, Response<JoinEmail> response) {
                //Join result = response.body();

                Log.e("결과 테스트 header : ", response.headers().toString());

                Log.e("결과 테스트 : ", response.code()+"");
                Log.e("결과 테스트 : ", response.errorBody().toString());
                //Log.e("결과 테스트 : ", response.body().toString());
            }
            @Override
            public void onFailure(Call<JoinEmail> call, Throwable t) {
                Log.e("결과 실패 : ", call+"");
                Log.e("결과 실패 : ", t.getMessage()+"");
                t.printStackTrace();
            }
        });







        /*
        join.enqueue(new LoginCallback<Join>(context));




        join.enqueue(new Callback<Join>(){
            @Override
            public void onResponse(Call<Join> call, Response<Join> response) {
                //Join result = response.body();

                Log.e("결과 테스트 : ", response.code()+"");
                Log.e("결과 테스트 : ", response.errorBody().toString());
                Log.e("결과 테스트 : ", response.body().toString());
            }
            @Override
            public void onFailure(Call<Join> call, Throwable t) {
                Log.e("결과 실패 : ", call+"");
                Log.e("결과 실패 : ", t.getMessage()+"");
                t.printStackTrace();
            }
        });



         */


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



    public int kakaoLogin(String username, String name, String password)
    {
        int code = 0;
        JoinEmail authJoinEmail = new JoinEmail(username, name, password);
        authJoinEmail = (JoinEmail) logininterface.createKakao(authJoinEmail);


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
        /*
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

         */


    }
    public void refreshToken(String token)
    {
        /*
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

         */
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




    public class AuthCallback<T> implements retrofit2.Callback<T> {
        final int SUCCESS               = 200;

        final int INVALID_PARAMETER     = 400;
        final int NEED_LOGIN            = 401;
        final int UNAUTHORIZED          = 403;
        final int NOT_FOUND             = 404;

        final int INTERNAL_SERVER_ERROR = 500;

        final int EMAIL_DUPLICATION = 4000;//이메일 중복
        final int NICKNAME_DUPLICATION = 4001;//닉네임 중복

        final int WRONG_PASSWORD = 4002;//비밀번호 수정 시 현재 비밀번호 다름

        final int WRONG_LOGIN = 4003;//id, pw 다름

        final int NOT_USERJOIN = 4005;//등록되지 않는 유저

        final int KAKAO_DUPLICATION = 4009; //카카오 로그인시 이메일 중복

        final int ACCESS_EXPIRATION = 4004;//엑세스 토큰 만료
        final int REFRESH_EXPIRATION = 4010;//리프레시 토큰 만료


        Context context;

        public AuthCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(retrofit2.Call<T> call, retrofit2.Response<T> response) {

            int code = response.code();
            T body = response.body();
            ResponseBody errorBody = response.errorBody();

            switch (code){
                case SUCCESS:
                    Login login = (Login)body;
                    break;
                case INVALID_PARAMETER:
                case INTERNAL_SERVER_ERROR:
                case NOT_FOUND:
                    break;
                case UNAUTHORIZED:
                case NEED_LOGIN:
                    loginProcess();
                    break;
                default:
                    Log.d("로그인 콜백 : ", code+"");

                    break;
            }
        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
            //t.printStackTrace();
            Log.d("로그인 콜백 : ", t.getMessage()+"");
        }

        private void loginProcess(){
        }
    }




}


