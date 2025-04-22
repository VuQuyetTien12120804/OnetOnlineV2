package com.example.onetonline.presentation.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onetonlinev2.R;

public class Room extends AppCompatActivity {
    private EditText editTextRoomId;
    private Button buttonCreateRoom, buttonJoinRoom;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thiết lập nền trong suốt và loại bỏ khung cửa sổ mặc định
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setDimAmount(0.6f); // Làm tối nền (từ 0.0 đến 1.0)

        setContentView(R.layout.activity_room);


        editTextRoomId = findViewById(R.id.editTextRoomId);
        buttonCreateRoom = findViewById(R.id.buttonCreateRoom);
        buttonJoinRoom = findViewById(R.id.buttonJoinRoom);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        buttonCreateRoom.setOnClickListener(v -> handleRoom("create"));
        buttonJoinRoom.setOnClickListener(v -> handleRoom("join"));
    }

    private void handleRoom(String type) {
        String roomId = editTextRoomId.getText().toString().trim();
        if (roomId.length() != 6) {
            Toast.makeText(this, "Room ID must be 6 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Room.this, RoomChat.class);
        intent.putExtra("ROOM_ID", roomId);
        intent.putExtra("ROOM_ACTION", type); // "create" or "join"
        intent.putExtra("name", name);
        startActivity(intent);
    }
}