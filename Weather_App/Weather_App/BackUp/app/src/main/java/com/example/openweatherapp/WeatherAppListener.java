package com.example.openweatherapp;

public interface WeatherAppListener {
    void onMyWeatherTaskPreExecute();
    void onMyWeatherTaskPostExecute(WeatherApp myWeather);
}
