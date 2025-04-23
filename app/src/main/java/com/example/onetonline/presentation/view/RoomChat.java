package com.example.onetonline.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onetonlinev2.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class RoomChat extends AppCompatActivity {
    private WebSocketClient webSocketClient;
    private EditText editTextMessage;
    private TextView textViewChat;
    private Button buttonSend, buttonHide;
    private String name;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);

        // Lấy dữ liệu từ StartActivity
        String roomId = getIntent().getStringExtra("ROOM_ID");
        String actionType = getIntent().getStringExtra("ROOM_ACTION"); // "create" hoặc "join"
        name = getIntent().getStringExtra("name");

        // Gọi hàm kết nối WebSocket với thông tin room
        connectWebSocket(roomId, actionType);

        editTextMessage = findViewById(R.id.editTextMessage);
        textViewChat = findViewById(R.id.textViewChat);
        buttonSend = findViewById(R.id.buttonSend);
        buttonHide = findViewById(R.id.buttonHide);
        scrollView = findViewById(R.id.scrollView);

        editTextMessage.setOnEditorActionListener((v, actionId, event) -> {
            String message = editTextMessage.getText().toString().trim();
            if (!message.isEmpty() && webSocketClient != null) {
                String jsonMessage = "{\"type\": \"message\", \"name\": \"" + name + "\", \"text\": \"" + message + "\"}";
                webSocketClient.send(jsonMessage);
                editTextMessage.setText("");
                return true;
            }
            return false;
        });

        buttonSend.setOnClickListener(view -> {
            String message = editTextMessage.getText().toString();
            if (!message.isEmpty() && webSocketClient != null) {
                // Gửi tin nhắn với type = "message"
                String jsonMessage = "{\"type\": \"message\", \"name\": \"" + name + "\", \"text\": \"" + message + "\"}";
                webSocketClient.send(jsonMessage);
                editTextMessage.setText("");
            }
        });

        buttonHide.setOnClickListener(view -> {
            if (webSocketClient != null) {
                webSocketClient.send("{\"type\":\"hide_button\"}");
            }
        });
    }

    private void connectWebSocket(String roomId, String actionType) {
        URI uri;
        try {
            uri = new URI("wss://chat-ws-jlxj.onrender.com");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                runOnUiThread(() -> textViewChat.append("Connected to server\n"));
                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

                // Gửi roomId và actionType ("create" hoặc "join") tới server
                JSONObject json = new JSONObject();
                try {
                    json.put("type", actionType);
                    json.put("roomId", roomId);
                    webSocketClient.send(json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(String message) {
                runOnUiThread(() -> {
                    try {
                        JSONObject data = new JSONObject(message);
                        String type = data.getString("type");

                        switch (type) {
                            case "message":
                                String senderName = data.getString("name");
                                String text = data.getString("text");

                                if (senderName.equals(name)) {
                                    textViewChat.append("Tôi: " + text + "\n");
                                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                                } else {
                                    textViewChat.append(senderName + ": " + text + "\n");
                                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                                }
                                break;

                            case "hide_button":
                                buttonHide.setVisibility(View.GONE);
                                break;

                            case "status":
                            case "error":
                                String msg = data.getString("message");
                                textViewChat.append("[System]: " + msg + "\n");
                                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }


            @Override
            public void onClose(int code, String reason, boolean remote) {
                runOnUiThread(() -> textViewChat.append("Disconnected\n"));
                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            }

            @Override
            public void onError(Exception ex) {
                runOnUiThread(() -> textViewChat.append("Error: " + ex.getMessage() + "\n"));
                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            }
        };

        webSocketClient.connect();
    }

}