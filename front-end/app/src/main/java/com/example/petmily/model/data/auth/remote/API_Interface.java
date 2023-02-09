package com.example.petmily.model.data.auth.remote;

import com.example.petmily.model.data.auth.Login;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API_Interface {

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @POST("token")//2차 카카오 로그인은 여기서 진행
    Call<Login> login(
            @FieldMap Map<String, String> login
    ); //Login 클래스를 kakao와 email로 구분



    @POST("sing-up")
    Call<JoinEmail> createEmail(@Body JoinEmail authJoinEmail);


    /*
    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @POST("sing-up")
    Call<Join> createUser(@Body Join join);


     */








    @POST("sing-up")
    Call<JoinEmail> createUser(@Body JoinEmail authJoinEmail);








    /*

    @FormUrlEncoded
    @POST("sing-up")
    Call<Auth_JoinEmail> createUser(
            @Field("username") String username,
            @Field("nickname") String nickname,
            @Field("name") String name,
            @Field("password") String password,
            @Field("age") String age,
            @Field("gender") String gender,
            @Field("phone") String phone,
            @Field("provider") String provider
    );


     */













    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @POST("kakao")//1차 카카오 로그인
    Call<JoinEmail> createKakao(@Body JoinEmail authJoinEmail);


    @GET("user")
    Call<String> user();

    //@Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    //@POST("check_token")
    //Call<TempInterface> accessToken(@Body Access_Token token);




    /*
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization:Basic ZGV2OnBldA=="})
    @POST("token")
    Call<Refresh_Token> refresh_token(@Body Refresh_Token token);


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
