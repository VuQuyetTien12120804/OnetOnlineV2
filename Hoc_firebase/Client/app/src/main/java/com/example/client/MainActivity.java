package com.example.client;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    String serverIP = "10.0.2.2"; // Đặt IP thực của Server
    int port = 5000;
    Socket socket;
    PrintWriter output;
    BufferedReader input;
    TextView txtMessage;
    EditText edtMessage;
    Button btnSend;
    ScrollView scrollView;
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessage = findViewById(R.id.txtMessage);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        scrollView = findViewById(R.id.scv);

        connectToServer();

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void connectToServer() {
        executorService.execute(() -> {
            try {
                socket = new Socket(serverIP, port);
                runOnUiThread(() ->
                        Toast.makeText(this, "Kết nối thành công đến Server!", Toast.LENGTH_SHORT).show()
                );

                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                while ((message = input.readLine()) != null) {
                    String finalMessage = message;
                    runOnUiThread(() -> {
                        txtMessage.append("Server: " + finalMessage + "\n");
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Kết nối thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            } finally {
                closeConnection();
            }
        });
        Log.d("Client", "Đang cố gắng kết nối với " + serverIP + ":" + port);

    }

    private void sendMessage() {
        String message = edtMessage.getText().toString();
        if (!message.isEmpty()) {
            if (output != null) {
                output.println(message);
                txtMessage.append("Client: " + message + "\n");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                edtMessage.setText("");
            } else {
                Toast.makeText(this, "Chưa kết nối với server!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void closeConnection() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConnection();
    }
}
