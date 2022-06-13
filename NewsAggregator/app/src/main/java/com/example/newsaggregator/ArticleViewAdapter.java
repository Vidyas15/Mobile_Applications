package com.example.newsaggregator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsaggregator.R;

import java.io.InputStream;
import java.util.ArrayList;

public class ArticleViewAdapter extends
        RecyclerView.Adapter<ArticleViewHolder> {

    private final com.example.newsaggregator.MainActivity mainActivity;
    private final ArrayList<Articles> articlesList;

    public ArticleViewAdapter(com.example.newsaggregator.MainActivity mainActivity, ArrayList<Articles> articlesList) {
        this.mainActivity = mainActivity;
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.topheadlinespagedesign, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Articles articles = articlesList.get(position);

        if(!TextUtils.isEmpty(articles.urlToImage)) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                InputStream in = new java.net.URL(articles.urlToImage).openStream();
                Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                holder.imageview.setImageBitmap(mIcon11);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Error",e.getMessage());
                holder.imageview.setImageResource(R.drawable.brokenimage);
            }
        }
        else{
            holder.imageview.setImageResource(R.drawable.noimage);
        }

        holder.descriptiontextview.setText(articles.description);
        holder.authortextview.setText(articles.author);
        holder.datetextview.setText(articles.publishedAt.split("T")[0]);
        holder.headingtextview.setText(articles.title);

        holder.countertextview.setText((position+1) +" of "+(articlesList.size()));


        holder.headingtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = articles.url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mainActivity.startActivity(i);
            }
        });

        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = articles.url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mainActivity.startActivity(i);
            }
        });

        holder.descriptiontextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = articles.url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mainActivity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}