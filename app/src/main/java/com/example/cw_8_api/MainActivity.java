package com.example.cw_8_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button getButton;
    OkHttpClient client;
    String url = "https://publicobject.com/helloworld.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        getButton = findViewById(R.id.button);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Запрос к серверу не был успешен: " +
                                        response.code() + " " + response.message());
                            }
                            // пример получения конкретного заголовка ответа
                            Log.d("test", response.toString());
                            // вывод тела ответа
                            Log.d("test",response.body().string());
                        } catch (IOException e) {
                            Log.d("test","Ошибка подключения: " + e);
                        }
                    }
                };
                thread.start();
            }
        });
    }
}