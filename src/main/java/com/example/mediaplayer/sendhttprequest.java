package com.example.mediaplayer;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class sendhttprequest{
    public void dorequest(String url,String RequestMethod,CallBackListener backListener){

        new Thread(() -> {
            try {
                URL url1 = new URL(url);
                Log.e("request", url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setRequestMethod(RequestMethod);
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.connect();
                Log.e("request", connection.toString());
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                Log.e("request", bufferedReader.toString());
                StringBuilder stringBuilder = new StringBuilder();
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                    Log.e("request", stringBuilder.toString());
                }
                Log.e("request", stringBuilder.toString());
                String out = stringBuilder.toString();
                Log.e("request", out);
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }
}
