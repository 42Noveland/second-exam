package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private final String N = "MainActivity";
    private ArrayList<Article> myData;
    private int page = 0;
    private final sendhttprequest sendhttprequest = new sendhttprequest();
    private boolean requset;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView  = findViewById(R.id.gedan_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        new Thread(()->{
           try {
               URL url = new URL("https://www.wanandroid.com/article/list/0/json");
               HttpURLConnection connection=(HttpURLConnection)url.openConnection();
               connection.setRequestMethod("GET");
               connection.setConnectTimeout(8000);
               connection.setReadTimeout(8000);
               connection.setDoInput(true);
               connection.setDoOutput(false);
//                    connection.connect();
               Log.e("request", connection.toString());
               InputStream in = connection.getInputStream();
               //字节流效率更高
               BufferedReader reader = new BufferedReader(new InputStreamReader(in));
               StringBuilder response = new StringBuilder();
               String line;
               while ((line = reader.readLine()) != null) {
                   response.append(line);
//                        .append('\n');
               }
               String out = response.toString();
               JSONObject jsonObject = new JSONObject(out);
               JSONArray jsonArray = jsonObject.getJSONArray("playlist");
               myData = new ArrayList<>();
               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject object = jsonArray.getJSONObject(i);
                   if (object != null) {
                       Article article = new Article();
                       article.setId(object.getString("id"));
                       article.setName(object.getString("name"));
                       article.setDescription(object.getString("description"));
                       article.setLink();
                       myData.add(article);
                   }
               }
               runOnUiThread(() -> recyclerView.setAdapter(new RecycleViewAdapter(myData)));
           }catch (Exception e){
               e.printStackTrace();
           }
        });
    }
}