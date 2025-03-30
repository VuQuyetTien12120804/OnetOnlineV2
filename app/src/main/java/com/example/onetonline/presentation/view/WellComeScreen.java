package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onetonline.business.User;
import com.example.onetonline.data.myDBHelper;
import com.example.onetonlinev2.R;

public class WellComeScreen extends AppCompatActivity {
    private myDBHelper myDB = new myDBHelper(WellComeScreen.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Khai báo biến
        Button btnLogin, btnRegister, btnGuest;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_well_come_screen);

        myDB.openDB();
        Cursor cursor = myDB.Display();
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.ID()));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.NAME()));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.EMAIL()));
            String level = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.LEVEL()));
            String last_update = cursor.getString(cursor.getColumnIndexOrThrow(myDBHelper.LAST_UPDATE()));
            User user = new User(id, email, name, Integer.parseInt(level), last_update);
            cursor.close();
            Log.d("auto", "onCreate " + cursor.getCount());
            Intent i = new Intent(WellComeScreen.this, MenuGame.class);
            i.putExtra("user", user);
            startActivity(i);
        }
        Log.d("auto", "onCreateee");
        myDB.close();

        //ánh xạ id
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnGuest = findViewById(R.id.btnGuest);

        // Xử lý click

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellComeScreen.this, LoginForm.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellComeScreen.this, RegisterForm.class);
                startActivity(intent);

            }
        });
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WellComeScreen.this, MenuGame.class);
                startActivity(intent);
                finish();
            }
        });

    }
}