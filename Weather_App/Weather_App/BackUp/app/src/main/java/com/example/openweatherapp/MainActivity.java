package com.example.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    TextView Location, uviDisp, visDisp;
    TextView time, feelsLikeDisp, descDisp, windInfoDisp, humidityDisp;
    EditText tempDisp;
    private String temp;
    WeatherApp weatherApp = new WeatherApp();

    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //Log.e("DEBUG ", "date : " +dt);
        this.context = getApplicationContext();
        setContentView(R.layout.activity_main);

        Location = findViewById(R.id.location);
        time = findViewById(R.id.date);
        tempDisp = findViewById(R.id.tempDisp);

        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + String.valueOf(lat) +
                "&lon=" + String.valueOf(lon) + "&appid=" + mKey + "&units=" + units_selection +
                "&lang=en&exclude=minutely";
        new WeatherListener(this).execute(url);

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
        String temp;
        String feelsLike;
        String windInfo;

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