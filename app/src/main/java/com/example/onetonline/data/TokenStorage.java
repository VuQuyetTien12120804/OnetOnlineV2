package com.example.onetonline.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TokenStorage {
    private static final String HARD_CODED_KEY = "OnetOnline123456";
    private static final String TRANSFORMATION = "AES/CBC/PKCS7Padding"; //securrity method
    private static final String PREFS_NAME = "secure_prefs"; //SharedPreferences's name that is used to store access token
    private Context context;

    public TokenStorage(Context context) {
        this.context = context;
    }

    //Encode and save access token to SharedPreferences
    public void saveToken(String accessToken) throws Exception {
        //Create a SecretKey by using HARD_CODED_KEY
        SecretKeySpec secretKey = new SecretKeySpec(HARD_CODED_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        //Encode access token
        byte[] iv = cipher.getIV();
        byte[] encryptedToken = cipher.doFinal(accessToken.getBytes(StandardCharsets.UTF_8));

        //Save encoded access token to SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", Base64.encodeToString(encryptedToken, Base64.DEFAULT));
        editor.putString("iv", Base64.encodeToString(iv, Base64.DEFAULT));
        editor.apply();
    }

    // Decode and get access token
    public String getToken() throws Exception {
        //Get data from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String encryptedToken = prefs.getString("access_token", null);
        String iv = prefs.getString("iv", null);

        if (encryptedToken == null || iv == null) {
            return null;
        }

        // create a SecretKey from key
        SecretKeySpec secretKey = new SecretKeySpec(HARD_CODED_KEY.getBytes(StandardCharsets.UTF_8), "AES");

        //Init cipher to Decrypt access token
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.decode(iv, Base64.DEFAULT));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        //Decrypt access token
        byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedToken, Base64.DEFAULT));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    //Remove access token from SharedPreferences
    public void removeToken() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("access_token");
        editor.remove("iv");
        editor.apply();
    }
}
