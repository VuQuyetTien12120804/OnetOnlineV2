package com.example.clientsocket1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private EditText editTextRoomId;
    private Button buttonCreateRoom, buttonJoinRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextRoomId = findViewById(R.id.editTextRoomId);
        buttonCreateRoom = findViewById(R.id.buttonCreateRoom);
        buttonJoinRoom = findViewById(R.id.buttonJoinRoom);

        buttonCreateRoom.setOnClickListener(v -> handleRoom("create"));
        buttonJoinRoom.setOnClickListener(v -> handleRoom("join"));
    }

    private void handleRoom(String type) {
        String roomId = editTextRoomId.getText().toString().trim();
        if (roomId.length() != 6) {
            Toast.makeText(this, "Room ID must be 6 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        intent.putExtra("ROOM_ID", roomId);
        intent.putExtra("ROOM_ACTION", type); // "create" or "join"
        startActivity(intent);
    }
}
