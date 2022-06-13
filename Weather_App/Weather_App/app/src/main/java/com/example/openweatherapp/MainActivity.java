package com.example.openweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherAppListener{

    private String mKey = "5d6796be3cf636d1009b5964d28ae6bb";
    private double lat = 41.8675766;
    private double lon = -87.616232;
    public static String units_selection = "imperial";
    private String loc = "Chicago, Illinois";
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, hh:mm a yyyy");
    private String dt = dateFormat.format(date);
    public static Context context;
    TextView Location, uviDisp, visDisp, mornDisp, dayDisp, eveDisp, ngtDisp;
    TextView time, feelsLikeDisp, descDisp, windInfoDisp, humidityDisp, srView, ssView;
    EditText tempDisp;
    private String temp;
    private RVAdapter adapter;
    JSONObject obj;
    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //Log.e("DEBUG ", "date : " +dt);
        this.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        ArrayList<String> dy = new ArrayList<>();
        dy.add("Today");
        dy.add("Today");
        dy.add("Today");
        dy.add("Today");
        dy.add("Today");

        ArrayList<String> tm = new ArrayList<>();
        tm.add("11 AM");
        tm.add("1 PM");
        tm.add("3 PM");
        tm.add("5 PM");
        tm.add("7 PM");

        ArrayList<Float> tmp = new ArrayList<>();
        tmp.add(71.3F);
        tmp.add(71.4F);
        tmp.add(71.5F);
        tmp.add(71.6F);
        tmp.add(71.7F);

        ArrayList<String> desc = new ArrayList<>();
        desc.add("Clear");
        desc.add("Sunny");
        desc.add("Rainy");
        desc.add("Clear");
        desc.add("Light rain");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new RVAdapter(this, dy, tm, tmp, desc);
        recyclerView.setAdapter(adapter);

        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + String.valueOf(lat) +
                "&lon=" + String.valueOf(lon) + "&appid=" + mKey + "&units=" + units_selection +
                "&lang=en&exclude=minutely";
        new WeatherListener(this).execute(url);

        Location = findViewById(R.id.location);
        time = findViewById(R.id.date);
        Location.setText(loc);
        time.setText(dt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.weather_app, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMyWeatherTaskPreExecute() {

    }

    @Override
    public void onMyWeatherTaskPostExecute(WeatherApp weather) {
        //Log.e("DEBUG", "In post execute ");
        String temp, temp_morn, temp_day, temp_eve, temp_ngt;
        String feelsLike;
        String windInfo;

        obj = weather.getObj();
        if (units_selection == "imperial") {
            temp = String.valueOf(weather.getTemperature()) + " ºF";
            feelsLike = "Feels Like "+ String.valueOf(weather.getFeelLike()) + " ºF";
        } else {
            temp = String.valueOf(weather.getTemperature()) + " ºC";
            feelsLike = "Feels Like "+ String.valueOf(weather.getFeelLike()) + " ºC";
        }
        tempDisp = findViewById(R.id.tempDisp);
        tempDisp.setText(temp);

        feelsLikeDisp = findViewById(R.id.Feelslike);
        feelsLikeDisp.setText(feelsLike);

        descDisp = findViewById(R.id.cloudinfo);
        descDisp.setText(weather.getWeatherDescription());
        //Log.e("DEBUG","Desc "+weather.getWeatherDescription());

        windInfo = "Winds: " + getDirection(weather.getDegree()) + " at " + weather.getSpeed() + " mph";
        //Log.e("DEBUG", "Wind Info "+windInfo);
        windInfoDisp = findViewById(R.id.windinfo);
        windInfoDisp.setText(windInfo);

        humidityDisp = findViewById(R.id.humidity);
        humidityDisp.setText("Humidity: " + String.valueOf(weather.getHumidity()) + "%");

        uviDisp = findViewById(R.id.uvindex);
        uviDisp.setText("UV Index: " + String.valueOf(weather.getUVI()));

        visDisp = findViewById(R.id.visibility);
        visDisp.setText("Visibility: " +String.valueOf(weather.getVisibility()) + " mi");

        mornDisp = findViewById(R.id.MornTemp);
        dayDisp = findViewById(R.id.DayTemp);
        eveDisp = findViewById(R.id.EveTemp);
        ngtDisp = findViewById(R.id.nightTemp);

        List<Float> Templist = weather.getTemplist();
        if (units_selection == "imperial") {
            temp_morn = String.valueOf(Templist.get(0)) + " ºF";
            temp_day = String.valueOf(Templist.get(1)) + " ºF";
            temp_eve = String.valueOf(Templist.get(2)) + " ºF";
            temp_ngt = String.valueOf(Templist.get(3)) + " ºF";
        } else {
            temp_morn = String.valueOf(Templist.get(0)) + " ºC";
            temp_day = String.valueOf(Templist.get(1)) + " ºC";
            temp_eve = String.valueOf(Templist.get(2)) + " ºC";
            temp_ngt = String.valueOf(Templist.get(3)) + " ºC";
        }
        mornDisp.setText(temp_morn);
        dayDisp.setText(temp_day);
        eveDisp.setText(temp_eve);
        ngtDisp.setText(temp_ngt);

        srView = findViewById(R.id.sunrise);
        srView.setText("Sunrise: "+weather.getSunrise());

        ssView = findViewById(R.id.sunset);
        ssView.setText("Sunset: "+weather.getSunset());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.units) {

        } else if (item.getItemId() == R.id.cal) {
            Intent intent = new Intent(MainActivity.this, daily_forecast.class);
            intent.putExtra("UNITS", units_selection);
            intent.putExtra("OBJ", obj.toString());
            startActivity(intent);
        } else if (item.getItemId() == R.id.location) {

        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private String getDirection(float degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }
}