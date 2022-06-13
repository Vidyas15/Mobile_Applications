package com.example.newsaggregator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsaggregator.R;


public class ArticleViewHolder extends RecyclerView.ViewHolder {

    TextView headingtextview,datetextview,authortextview,descriptiontextview,countertextview;
    ImageView imageview;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        headingtextview = itemView.findViewById(R.id.headingtextview);
        datetextview = itemView.findViewById(R.id.datetextview);
        authortextview = itemView.findViewById(R.id.authortextview);
        descriptiontextview = itemView.findViewById(R.id.descriptiontextview);
        countertextview = itemView.findViewById(R.id.countertextview);
        imageview = itemView.findViewById(R.id.imageview);
    }
}
