package com.example.wetherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    private EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed=findViewById(R.id.editText);
        btn=findViewById(R.id.button);
        tv=findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=ed.getText().toString();
                MyWeather m = new MyWeather();
                m.execute(city);

            }
        });
    }
    private class MyWeather extends AsyncTask<String,Void,String>{

        HttpURLConnection httpurl;

        @Override
        protected String doInBackground(String... str) {
            String quary=str[0];


            try {
                URL url=new URL("http://api.weatherstack.com/current?access_key=da5cbafa2bfbfa6894e9c0290a09e192&query="+quary);
                httpurl=(HttpURLConnection)url.openConnection();
                httpurl.setRequestMethod("GET");
                httpurl.connect();
                InputStream input= httpurl.getInputStream();
                Scanner scan = new Scanner(input);
                StringBuffer buffer= new StringBuffer();
                while(scan.hasNextLine()){
                    buffer.append(scan.nextLine());
                }
                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson g = new Gson();
            Weather w=g.fromJson(s,Weather.class);
            int temp = w.getCurrent().getTemperature();
            tv.setText(Integer.toString(temp));
        }
    }
}
