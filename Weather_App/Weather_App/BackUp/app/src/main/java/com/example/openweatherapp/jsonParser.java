package com.example.openweatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class jsonParser {
    public static WeatherApp getWeather(String json)
    {
        WeatherApp weatherApp = new WeatherApp();
        float vis = 0.0F;
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            //JSONArray currarray = jsonObject.getJSONArray("current");
            JSONObject jsonObject_curr = jsonObject.getJSONObject("current");
            JSONArray jsonObject_daily = jsonObject.getJSONArray("daily");
            JSONArray weatherarray = jsonObject_curr.getJSONArray("weather");

            //Log.e("DEBUG", "Json value " +jsonObject);
            //Log.e("DEBUG", "weat "+weatherarray.getJSONObject(0).getString("description"));
            weatherApp.setTemperature(Float.parseFloat(jsonObject_curr.getString("temp")));
            weatherApp.setFeelLike(Float.parseFloat(jsonObject_curr.getString("feels_like")));
            weatherApp.setWeatherDescription(weatherarray.getJSONObject(0).getString("description"));
            weatherApp.setDegree(Float.parseFloat(jsonObject_curr.getString("wind_deg")));
            weatherApp.setSpeed(Float.parseFloat(jsonObject_curr.getString("wind_speed")));
            try {
                weatherApp.setGust(Float.parseFloat(jsonObject_curr.getString("wind_gust")));
            } catch (JSONException e) {
                weatherApp.setGust(0.0F);
            }
            weatherApp.setHumidity(Integer.parseInt(jsonObject_curr.getString("humidity")));
            weatherApp.setUVI(Float.parseFloat(jsonObject_curr.getString("uvi")));
            vis = (0.0006F * Float.parseFloat(jsonObject_curr.getString("visibility")));
            vis = Math.round(vis * 100.0f) / 100.0f;
            weatherApp.setVisibility(vis);



            /*
            try {
                weatherApp.setTemperature(Float.parseFloat(jsonObject_curr.getString("temp")));
            } catch(JSONException e) {
                weatherApp.setTemperature(0.0F);
            }

            try {
                weatherApp.setFeelLike(Float.parseFloat(jsonObject_curr.getString("feels_like")));
            } catch(JSONException e) {
                weatherApp.setFeelLike(0.0F);
            }

            try {
                weatherApp.setWeatherDescription(weatherarray.getJSONObject(0).getString("description"));
            } catch(JSONException e) {
                weatherApp.setWeatherDescription("Not Availiable");
            }

            try {
                weatherApp.setDegree(Float.parseFloat(jsonObject_curr.getString("wind_deg")));
            } catch(JSONException e) {
                weatherApp.setDegree(0.0F);
            }

            try {
                weatherApp.setSpeed(Float.parseFloat(jsonObject_curr.getString("wind_speed")));
            } catch(JSONException e) {
                weatherApp.setSpeed(0.0F);
            }

            try {
                weatherApp.setGust(Float.parseFloat(jsonObject_curr.getString("wind_gust")));
            } catch (JSONException e) {
                weatherApp.setGust(0.0F);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherApp;
    }
}
