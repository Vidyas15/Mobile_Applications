package com.example.openweatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class daily_forecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast);

        JSONObject wObj = new JSONObject(getIntent().getStringExtra("OBJ"));

    }

}
