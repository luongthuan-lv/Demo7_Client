package com.luongthuan.demo7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText edtData;
    Button btnSend;
    final String serverHost = "192.168.26.101";
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtData = findViewById(R.id.edtData);
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = edtData.getText().toString().trim();
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        Socket socketOffClient = null;
                        BufferedWriter os = null;
                        BufferedReader is = null;

                        try {
                            socketOffClient = new Socket(serverHost, 9999);

                            os = new BufferedWriter(new OutputStreamWriter(socketOffClient.getOutputStream()));
                            is = new BufferedReader(new InputStreamReader(socketOffClient.getInputStream()));

                            os.write("Hello, Iam CLient: " + content);
                            os.newLine();
                            os.flush();

                            String responseLine;
                            while ((responseLine = is.readLine()) != null) {
                                Log.e("Res", responseLine);
                            }
                            os.close();
                            is.close();
                            socketOffClient.close();

                        } catch (Exception e) {

                            Log.e("LOI", e.getMessage());
                        }
                        return null;

                    }
                };
                asyncTask.execute();
            }
        });


    }
}