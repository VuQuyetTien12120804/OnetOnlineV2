package com.example.onetonline.data;

import android.content.Context;

import com.example.onetonline.presentation.model.ChangePassRequest;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.SignupRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepo {
    private UserAPI userAPI;
    private myDBHelper myDB;
    private TokenStorage tokenStorage;

    public UserRepo(Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        userAPI = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(UserAPI.class);
        myDB = new myDBHelper(context);
        tokenStorage = new TokenStorage(context);
    }

    public interface SignUpCallBack{
        void onSuccess(PostResponse postResponse);
        void onFailure(String err);
    }

    public interface LoginCallBack{
        void onSuccess(token t);
        void onFailure(String err);
    }

    public interface GetUserCallBack{
        void onSuccess(User user);
        void onFailure(String err);
    }

    public interface UpdateUserCallBack{
        void onSuccess();
        void onFailure(String err);
    }

    public interface ChangePasswordCallBack{
        void onSuccess();
        void onFailure(String err);
    }

    public void login(LoginRequest loginRequest, final LoginCallBack callBack){
        userAPI.login(loginRequest).enqueue(new Callback<token>() {
            @Override
            public void onResponse(Call<token> call, Response<token> response) {
                if(response.isSuccessful()){
                    token t = response.body();
                    callBack.onSuccess(t);
                    try {
                        tokenStorage.saveToken(t.access_token());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<token> call, Throwable t) {
                callBack.onFailure("Network error: "+ t.getMessage());
            }
        });
    }

    public void addUser(SignupRequest signupRequest, final SignUpCallBack callBack){
        userAPI.addUser(signupRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.isSuccessful()){
                    PostResponse postResponse = response.body();
                    callBack.onSuccess(postResponse);
                }
                else{
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                callBack.onFailure("Network error: "+ t.getMessage());
            }
        });
    }

    public void getUser(String token, final GetUserCallBack callBack){
        userAPI.getUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    callBack.onSuccess(user);
                    long i = myDB.Insert(user.id(), user.userName(), user.email(), user.level(), user.score(), user.exp(), user.lastUpdate());
                }
                else{
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.onFailure("Network error: "+ t.getMessage());
            }
        });
    }

    public void changePass(ChangePassRequest changePassRequest, final ChangePasswordCallBack callBack){
        userAPI.changePass(changePassRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    callBack.onSuccess();
                }
                else{
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure("Network error: "+ t.getMessage());
            }
        });
    }

    public void updateUser(User user, final UpdateUserCallBack callBack){
        userAPI.updateUser(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    callBack.onSuccess();
                }
                else{
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure("Network error: "+ t.getMessage());
            }
        });
    }
}
