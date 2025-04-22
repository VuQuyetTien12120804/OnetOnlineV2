package com.example.server;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    int port = 5000;
    ServerSocket serverSocket;
    Socket clientSocket;
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
        scrollView = findViewById(R.id.scrollView);

        startServer();

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void startServer() {
        executorService.execute(() -> {
            try {
                serverSocket = new ServerSocket(port);
                runOnUiThread(() -> Toast.makeText(this, "Server đang chạy...", Toast.LENGTH_SHORT).show());

                clientSocket = serverSocket.accept();
                runOnUiThread(() -> Toast.makeText(this, "Client đã kết nối!", Toast.LENGTH_SHORT).show());

                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;
                while ((message = input.readLine()) != null) {
                    String finalMessage = message;
                    runOnUiThread(() -> {
                        txtMessage.append("Client: " + finalMessage + "\n");
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Lỗi server: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                closeConnection();
            }
        });
        Log.d("Server", "Đang chạy trên cổng: " + port);

    }

    private void sendMessage() {
        String message = edtMessage.getText().toString();
        if (!message.isEmpty()) {
            if (output != null) {
                output.println(message);
                txtMessage.append("Server: " + message + "\n");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                edtMessage.setText("");
            } else {
                Toast.makeText(this, "Chưa kết nối với client!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void closeConnection() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConnection();
    }
}
