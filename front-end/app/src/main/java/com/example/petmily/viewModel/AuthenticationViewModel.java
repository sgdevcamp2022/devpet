package com.example.petmily.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.petmily.model.data.auth.AccessToken;
import com.example.petmily.model.data.auth.FailMessage;
import com.example.petmily.model.data.auth.local.AuthDatabase;
import com.example.petmily.model.data.auth.local.TokenSQL;
import com.example.petmily.model.data.auth.remote.API_Interface;
import com.example.petmily.model.data.auth.RefreshToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AuthenticationViewModel extends AndroidViewModel {

    //final String URL = "http://121.187.22.37:7070/oauth/";
    final String URL = "http://ec2-52-192-78-94.ap-northeast-1.compute.amazonaws.com:7070/oauth/";


    private AuthDatabase db;
    private AuthCallback authCallback;

    private API_Interface authInterface;
    private Retrofit retrofit;
    private Context context;

    private Call<?> restApi;

    private TokenSQL token;
    private String email;

    private SingleLiveEvent<Boolean> eventEmailDuplication;
    public SingleLiveEvent<Boolean> getEventEmailDuplication() {
        if (eventEmailDuplication == null) {
            eventEmailDuplication = new SingleLiveEvent<Boolean>();
        }
        return eventEmailDuplication;
    }

    private SingleLiveEvent<Boolean> eventNicknameDuplication;
    public SingleLiveEvent<Boolean> getEventNickNameDuplication() {
        if (eventNicknameDuplication == null) {
            eventNicknameDuplication = new SingleLiveEvent<Boolean>();
        }
        return eventNicknameDuplication;
    }

    private SingleLiveEvent<Boolean> eventRefreshExpiration;
    public SingleLiveEvent<Boolean> getEventRefreshExpiration() {
        if (eventRefreshExpiration == null) {
            eventRefreshExpiration = new SingleLiveEvent<Boolean> ();
        }
        return eventRefreshExpiration;
    }

    private SingleLiveEvent<Boolean> eventLoginExpiration;
    public SingleLiveEvent<Boolean> getEventLoginExpiration() {
        if (eventLoginExpiration == null) {
            eventLoginExpiration = new SingleLiveEvent<Boolean> ();
        }
        return eventLoginExpiration;
    }

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        init();
    }

    public void init()
    {
        db = AuthDatabase.getInstance(context);
        authCallback = new AuthCallback(context);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        authInterface = retrofit.create(API_Interface.class);
        TokenSQL tokenSQL = db.authDao().getToken();

        if(tokenSQL != null)
        {
            token =  db.authDao().getToken();

        }
        else
        {
            token = new TokenSQL("dummy", "dummy", "dummy", "dummy");
        }
    }

    public void accessTokenCheck()
    {
        //checkToken??? ????????? ????????? ?????? ????????? ??????
        restApi = authInterface.checkToken(token.getAccessToken());;
        restApi.enqueue(authCallback);
    }

    public void refreshTokenCheck()
    {
        restApi = authInterface.refresh_token("refresh_token", token.getRefreshToken());

        restApi.enqueue(authCallback);
    }

    public void join(String username, String name, String phone, String password)
    {
        restApi = authInterface.createUser(username, name, phone, password, "");
        restApi.enqueue(authCallback);
    }

    public void login(String username, String password)
    {
        restApi = authInterface.login("password", username, password, "trust");
        email = username;
        restApi.enqueue(authCallback);
    }

    public void logout()
    {
        if(token != null)
        {
            TokenSQL logout = new TokenSQL(token.getUserId(), "", "", "");
            db.authDao().insertToken(logout);
        }
    }
    public void tokenSave(RefreshToken refreshToken)
    {
        String newAccessToken = refreshToken.getAccess_token();
        String newRefreshToken = refreshToken.getRefresh_token();
        String uid = refreshToken.getUid();
        TokenSQL newToken = new TokenSQL(uid, newAccessToken, newRefreshToken, email);
        db.authDao().insertToken(newToken);
    }
    public class AuthCallback<T> implements retrofit2.Callback<T> {

        final int SUCCESS               = 200;

        final int INVALID_PARAMETER     = 400;
        final int NEED_LOGIN            = 401;
        final int UNAUTHORIZED          = 403;
        final int NOT_FOUND             = 404;

        final int INTERNAL_SERVER_ERROR = 500;

        final int EMAIL_DUPLICATION = 4000;//????????? ??????
        final int NICKNAME_DUPLICATION = 4001;//????????? ??????
        final int WRONG_PASSWORD = 4002;//???????????? ?????? ??? ?????? ???????????? ??????
        final int WRONG_LOGIN = 4003;//id, pw ?????? -> ????????? ?????????
        final int ACCESS_EXPIRATION = 4004;//????????? ?????? ??????
        final int REFRESH_EXPIRATION = 4010;//???????????? ?????? ??????

        Context context;

        public AuthCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(retrofit2.Call<T> call, retrofit2.Response<T> response) {


            Gson gson = new Gson();
            int responseCode = response.code();//???????????? ????????? ??? ?????? ??????
            T body = response.body();
            FailMessage errorBody =null;
            if(responseCode != SUCCESS)
            {
                Log.e("???????????? ?????? ?????? : ", responseCode+"");
                try {
                    errorBody = gson.fromJson( response.errorBody().string(), FailMessage.class);
                    Log.e("???????????? ?????? ?????? : ", errorBody.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(errorBody  != null) {
                    int code;
                    if(errorBody.getMessage().charAt(0) != '4')
                    {
                        code = responseCode;
                    }
                    else
                    {
                        code = Integer.parseInt(errorBody.getMessage());
                    }
                    switch (code) {
                        //???????????? ?????????
                        case EMAIL_DUPLICATION:
                            eventEmailDuplication.setValue(false);
                            break;
                        case NICKNAME_DUPLICATION:
                            eventNicknameDuplication.setValue(false);
                            break;
                        //????????? ?????????
                        case WRONG_LOGIN:
                            eventLoginExpiration.setValue(false);
                            break;
                        //case WRONG_PASSWORD://???????????? ?????? ??? ?????? ??????????????? ?????? -> ???????????? ????????? ??????
                        case ACCESS_EXPIRATION:
                            refreshTokenCheck();
                            break;
                        case REFRESH_EXPIRATION:
                            eventRefreshExpiration.setValue(false);
                            break;
                        default:
                            break;
                    }
                }
                else if(body instanceof AccessToken)
                {
                    restApi = authInterface.refresh_token("refresh_token", token.getRefreshToken());
                }
                else if(body instanceof RefreshToken)
                {
                    tokenSave((RefreshToken) body);
                    eventLoginExpiration.setValue(true);
                }
            }
            else if(responseCode == SUCCESS)
            {
                if(body instanceof RefreshToken)//????????? ?????? or ???????????? ?????????
                {
                    tokenSave((RefreshToken) body);
                    eventRefreshExpiration.setValue(true);

                    SharedPreferences sharedPreferences= context.getSharedPreferences("token", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("token", token.getAccessToken());
                    editor.putString("refresh", token.getAccessToken());
                    editor.putString("userId", token.getUserId());
                    editor.putString("email", token.getUserEmail());
                    editor.commit();

                }
                else if(body instanceof  AccessToken)//?????? ????????? ??????
                {
                    Log.e("?????? ????????? ?????? - token",token.getAccessToken());
                    Log.e("?????? ????????? ?????? - refresh",token.getRefreshToken());
                    Log.e("?????? ????????? ?????? - userId",token.getUserId());
                    SharedPreferences sharedPreferences= context.getSharedPreferences("token", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    String email = token.getUserEmail();
                    for(int i = 0; i < email.length(); i++)
                    {
                        if(email.charAt(i)==' ')
                        {

                        }
                    }
                    editor.putString("token", token.getAccessToken());
                    editor.putString("refresh", token.getAccessToken());
                    editor.putString("userId", token.getUserId());
                    editor.putString("email", token.getUserEmail());
                    editor.commit();
                    eventRefreshExpiration.setValue(true);
                }

            }
            else
            {
                ResponseBody hh = response.errorBody();
                try {
                    Log.e("?????? ?????? ?????? : ", responseCode + "\t" +hh.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onFailure(retrofit2.Call<T> call, Throwable t) {
            Log.e("?????? ?????? : ", t.getMessage());
            t.printStackTrace();
        }
    }

    public class SingleLiveEvent<T> extends MutableLiveData<T> {

        private static final String TAG = "SingleLiveEvent";

        private final AtomicBoolean mPending = new AtomicBoolean(false);

        @MainThread
        public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

            if (hasActiveObservers()) {
                Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
            }

            // Observe the internal MutableLiveData
            super.observe(owner, new Observer<T>() {
                @Override
                public void onChanged(@Nullable T t) {
                    if (mPending.compareAndSet(true, false)) {
                        observer.onChanged(t);
                    }
                }
            });
        }

        @MainThread
        public void setValue(@Nullable T t) {
            mPending.set(true);
            super.setValue(t);
        }

        /**
         * Used for cases where T is Void, to make calls cleaner.
         */
        @MainThread
        public void call() {
            setValue(null);
        }
    }
}