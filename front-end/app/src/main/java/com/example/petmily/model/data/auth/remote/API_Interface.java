package com.example.petmily.model.data.auth.remote;



import com.example.petmily.model.data.auth.AccessToken;
import com.example.petmily.model.data.auth.RefreshToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API_Interface {

    //이메일 회원가입
    //의논 후 반환타입 생각
    @FormUrlEncoded
    @POST("sign-up")
    Call<Join> createUser(
            @Field("username") String username,
            @Field("name") String name,
            @Field("nickname") String nickname,
            @Field("password") String password,
            @Field("age") String age,
            @Field("gender") String gender,
            @Field("phone") String phone,
            @Field("provider") String provider

    );



    @FormUrlEncoded
    @Headers("Authorization: Basic ZGV2OnBldA==")
    @POST("token")//2차 카카오 로그인은 여기서 진행
    Call<RefreshToken> login(
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password,
            @Field("scope") String scope
    ); //Login 클래스를 kakao와 email로 구분


    @GET("users/user")//서비스 이용시
    Call<String> tokenCheck(@Header("Authorization") String token);

    @FormUrlEncoded
    @Headers("Authorization: Basic ZGV2OnBldA==")
    @POST("check_token")//앱 시작시 토큰체크 실패시 FailClass 호출
    Call<AccessToken> checkToken(@Field("token")  String token);

    @FormUrlEncoded
    @Headers("Authorization: Basic ZGV2OnBldA==")
    @POST("token")//2차 카카오 로그인은 여기서 진행
    Call<RefreshToken> refresh_token(
            @Field("grant_type") String grant_type,//"refresh_token" 고정
            @Field("refresh_token") String refresh_token//앞에 추가문자 없음
    ); //엑세스토큰 실패시 리프레쉬 넣어서 실행




    //@Headers("Authorization: Basic ZGV2OnBldA==")

    /*
    이메일, 소셜로그인, 엑세스토큰 체크, 엑세스토큰 재발급 할 때 헤더 추가가
    */


    //없앨예정
    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @POST("kakao")//1차 카카오 회원가입
    Call<Join> createKakao(@Body Join authJoin);

    /*



    //프로필 관련
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @GET("{username}")//이메일주소(프로필 읽어오기)
    Call<Refresh_Token> refresh_token(@Body Refresh_Token token);


    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @PUT("{username}")//편집 후 데이터 보내기
    Call<Refresh_Token> refresh_token(@Body Refresh_Token token);

    @POST("{username}")//내아이디, 상대 아이디 String 값 2개 결과값은 uuid(채팅방 id)
    Call<Refresh_Token> refresh_token(@Body Refresh_Token token);



    //채팅 관련
    @GET("{username}")//내아이디, 상대 아이디 String 값 2개 결과값은 uuid(채팅방 id)
    Call<Refresh_Token> refresh_token(@Body Refresh_Token token);



     */






}
