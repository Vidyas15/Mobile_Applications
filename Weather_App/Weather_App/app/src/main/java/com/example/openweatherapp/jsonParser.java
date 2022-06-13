package com.example.openweatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class jsonParser {
    public static WeatherApp getWeather(String json)
    {
        WeatherApp weatherApp = new WeatherApp();
        float vis = 0.0F;
        String dailyTemp;
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            weatherApp.setJsonObj(jsonObject);
            //JSONArray currarray = jsonObject.getJSONArray("current");
            JSONObject jsonObject_curr = jsonObject.getJSONObject("current");
            JSONArray dailyArr = jsonObject.getJSONArray("daily");
            JSONArray weatherarray = jsonObject_curr.getJSONArray("weather");
            //JSONArray TempArr = dailyArr.getJSONObject(0).getString("temp");

            //Log.e("DEBUG", "Json value " +obj.getString("day"));
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

            JSONObject obj = new JSONObject(dailyArr.getJSONObject(0).getString("temp"));
            List<Float> Templist = new ArrayList<>();
            Templist.add(Float.parseFloat(obj.getString("morn")));
            Templist.add(Float.parseFloat(obj.getString("day")));
            Templist.add(Float.parseFloat(obj.getString("eve")));
            Templist.add(Float.parseFloat(obj.getString("night")));
            weatherApp.setTemplist(Templist);

            String sunrise = jsonObject_curr.getString("sunrise");
            long dv = Long.valueOf(sunrise)*1000;
            Date df = new java.util.Date(dv);
            String vv = new SimpleDateFormat("hh:mma").format(df);
            //Log.e("DEBUG", "Sunrise "+vv);
            weatherApp.setSunrise(vv);

            String sunset = jsonObject_curr.getString("sunset");
            dv = Long.valueOf(sunset)*1000;
            df = new java.util.Date(dv);
            vv = new SimpleDateFormat("hh:mma").format(df);
            weatherApp.setSunset(vv);
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
