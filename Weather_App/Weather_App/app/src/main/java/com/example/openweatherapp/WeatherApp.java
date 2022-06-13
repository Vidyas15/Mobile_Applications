package com.example.openweatherapp;

import org.json.JSONObject;

import java.util.List;

public class WeatherApp {
    private String weatherCondition;
    private String weatherDescription, sunrise, sunset;
    private String weatherIconStr;
    private static float temperature;
    private static float feelLike;
    private float degree, speed, gust, uvi, visibility;
    private int hum;
    private List<Float> Templist;
    private JSONObject obj;

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

    public List<Float> getTemplist() {
        return Templist;
    }

    public void setTemplist(List<Float> lst) {
        this.Templist = lst;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setJsonObj(JSONObject obj) {
        this.obj = obj;
    }

    public JSONObject getObj() {
        return obj;
    }

}
