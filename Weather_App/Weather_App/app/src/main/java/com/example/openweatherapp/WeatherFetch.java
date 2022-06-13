package com.example.openweatherapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class WeatherFetch {

    public static String fetchWeather(String weatherurl)
    {
        String json = null;
        HttpsURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;


        try {
            URL url = new URL(weatherurl);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }
            if (stringBuilder.length() != 0)
            {
                json = stringBuilder.toString();
            }
        }catch (IOException ignored)
        {
        }finally {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

//    private boolean hasNetworkConnection() {
//        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
//    }
}
