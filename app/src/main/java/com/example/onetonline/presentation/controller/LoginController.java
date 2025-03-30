package com.example.onetonline.presentation.controller;

import android.content.Context;
import com.example.onetonline.business.User;
import com.example.onetonline.business.UserRepo;
import com.example.onetonline.business.token;
import com.example.onetonline.presentation.model.LoginRequest;
import com.example.onetonline.presentation.view.LoginView;

public class LoginController {
    private LoginView loginView;
    private UserRepo userRepo;

    public LoginController(LoginView loginView, Context context) {
        this.loginView = loginView;
        userRepo = new UserRepo(context);
    }

    public void handleLogin(){
        LoginRequest loginRequest = new LoginRequest(loginView.getUserName(), loginView.getPassword());
        if (loginView.getUserName().isEmpty() || loginView.getPassword().isEmpty()) {
            loginView.showMessage("Please enter username and password");
            return;
        }
        userRepo.login(loginRequest, new UserRepo.LoginCallBack() {
            @Override
            public void onSuccess(token t) {
                String access_token = "Bearer " + t.access_token();
                userRepo.getUser(access_token, new UserRepo.GetUserCallBack() {
                    @Override
                    public void onSuccess(User user) {
                        loginView.showMessage(user.id());
                        loginView.showMessage(user.userName());
                        loginView.showMessage(user.email());
                        loginView.showMessage(String.valueOf(user.level()));
                        loginView.showMessage(user.lastUpdate());
                        loginView.convertContext(user);
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("401")){
                    loginView.showMessage("Wrong password");
                }
                else if(err.equals("404")){
                    loginView.showMessage("Account doesn't exists");
                }
                else {
                    loginView.showMessage(err);
                }
            }
        });
    }

}
