package com.example.openweatherapp;

public class WeatherApp {
    private String weatherCondition;
    private String weatherDescription;
    private String weatherIconStr;
    private static float temperature;
    private static float feelLike;
    private float degree, speed, gust, uvi, visibility;
    private int hum;

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition)
    {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherDescription()
    {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription)
    {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIconStr() {
        return weatherIconStr;
    }

    public void setWeatherIconStr(String weatherIconStr) {
        this.weatherIconStr = weatherIconStr;
    }

    public static float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public static float getFeelLike() {
        return feelLike;
    }

    public void setFeelLike(float temp) {
        this.feelLike = temp;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGust() {
        return gust;
    }

    public void setGust(float gust) {
        this.gust = gust;
    }

    public int getHumidity() {
        return hum;
    }

    public void setHumidity(int humidity) {
        this.hum = humidity;
    }

    public float getUVI() {
        return uvi;
    }

    public void setUVI(float uvi) {
        this.uvi = uvi;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }
}
