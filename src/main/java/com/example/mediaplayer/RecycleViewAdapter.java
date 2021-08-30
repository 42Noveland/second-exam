package com.example.mediaplayer;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;


public  class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.articleViewHolder> {
    List<Article> myarticles;

    public RecycleViewAdapter(List<Article> articles) {
        this.myarticles = articles;
    }
    @NonNull
    @Override
    public articleViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        articleViewHolder holder = new articleViewHolder(view);
        return new articleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull  articleViewHolder holder, int position) {
        Article article = myarticles.get(position);
        holder.title.setText(article.name);
        holder.time.setText(article.Id);
        holder.zuozhe.setText(article.description);
        String link = article.link;
        holder.articleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),link,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                intent.putExtra("link", link);
                v.getContext().startActivity(intent);
            }
        });
    }
    public int getItemCount() {
        return myarticles.size();
    }


    class articleViewHolder extends RecyclerView.ViewHolder{
        TextView title,time,zuozhe;
        View articleView;
        public articleViewHolder(@NonNull  View itemView) {
            super(itemView);
            articleView = itemView;
            title = itemView.findViewById(R.id.tv_article_title);
            time = itemView.findViewById(R.id.tv_article_time);
            zuozhe = itemView.findViewById(R.id.tv_article_author);
        }
    }
}
