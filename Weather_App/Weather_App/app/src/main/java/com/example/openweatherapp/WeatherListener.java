package com.example.openweatherapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

public class WeatherListener extends AsyncTask<String, Void, WeatherApp> {
    private WeatherAppListener mListener;

    WeatherListener(WeatherAppListener pListener) {
        this.mListener = pListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onMyWeatherTaskPreExecute();
    }

    @Override
    protected WeatherApp doInBackground(String... strings) {
        WeatherApp weather = null;
        String json = WeatherFetch.fetchWeather(strings[0]);

        if (json != null) {
            weather = jsonParser.getWeather(json);
        }
        return weather;
    }

    @Override
    protected void onPostExecute(WeatherApp weather)
    {
        super.onPostExecute(weather);
        mListener.onMyWeatherTaskPostExecute(weather);
    }
}
