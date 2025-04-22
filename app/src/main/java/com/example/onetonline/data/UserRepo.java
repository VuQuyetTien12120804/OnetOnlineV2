package com.example.onetonline.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.recyclerview.widget.DiffUtil;

import com.example.onetonline.presentation.model.ChangePassRequest;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.model.SignupRequest;
import com.example.onetonline.presentation.model.UserInf;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepo {
    private UserAPI userAPI;
    private myDBHelper myDB;
    private TokenStorage tokenStorage;
    private AvatarManager avatarManager;
    //10.0.2.2:8000
    public UserRepo(Context context) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        userAPI = new Retrofit.Builder()
                .baseUrl("https://user-onetonline.fly.dev/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(UserAPI.class);
        myDB = new myDBHelper(context);
        myDB.openDB();
        avatarManager = new AvatarManager(context);
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

    public interface GetTopUserCallBack{
        void onSuccess(List<userRanking> rankingList);
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

    public void getTopUser(final GetTopUserCallBack callBack){
        userAPI.getTopUser().enqueue(new Callback<List<userRanking>>() {
            @Override
            public void onResponse(Call<List<userRanking>> call, Response<List<userRanking>> response) {
                if(response.isSuccessful()){
                    List<userRanking> rankingList = response.body();
                    callBack.onSuccess(rankingList);
                }
                else{
                    callBack.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<userRanking>> call, Throwable t) {
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

    public User getUserLocal(){
        myDB.openDB();
        Cursor cursor = myDB.Display();
        User user = null;
        if(cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.ID()));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.NAME()));
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.LEVEL()));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.SCORE()));
            int exp = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.EXP()));
            String last_update = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.LAST_UPDATE()));
            Boolean isDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(myDBHelper.IS_DELETED())) != 0;
            cursor.close();
            user = new User(id, name, level, score, exp, last_update, isDeleted);
        }
        return user;
    }

    public void updateToLocal(User user){
        myDB.Update(user);
    }

    public void insertToLocal(User user){
        myDB.Insert(user);
    }

    public void deleteFromLocal(String id){
        myDB.Delete(id);
    }

    public void deleteAllFromLocal(){
        myDB.DeleteAll();
    }

    public boolean hasRecords(){
        return myDB.hasRecords();
    }

    public void updateVersion(String last_upadte){
        myDB.UpdateVersion(last_upadte);
    }

    public void saveAvatar(Bitmap bitmap, String nameFile, final AvatarManager.AvatarCallback callback){
        avatarManager.saveImage(bitmap, nameFile, new AvatarManager.AvatarCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callback.onSuccess(bitmap);
            }

            @Override
            public void onFailure(String err) {
                callback.onFailure(err);
            }
        });
    }

    public void sync(User user, final UpdateUserCallBack callBack){
        String access_token = "Bearer " + tokenStorage.getToken();
        userAPI.updateUser(user, access_token).enqueue(new Callback<Void>() {
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

    public void saveToken(String accessToken) {
        tokenStorage.saveToken(accessToken);
    }

    public String getToken(){
        return tokenStorage.getToken();
    }

    public void close(){
        myDB.close();
    }
}
