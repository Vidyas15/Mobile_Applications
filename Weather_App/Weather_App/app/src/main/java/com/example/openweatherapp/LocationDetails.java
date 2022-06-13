package com.example.openweatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class LocationDetails {
    private String getLocationName(String userProvidedLocation, Context mContext) {
        Geocoder geocoder = new Geocoder(mContext); // Here, “this” is an Activity
        try {
            List<Address> address = geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1 = ""; String p2 = "";
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            } else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            String locale = p1 + ", " + p2; 
            return locale; 
        } catch (IOException e) {
            // Failure to get an Address object
            Log.e("Location Details", "Failed to get an address object");
            return null; 
        } 
    }

    private double[] getLatLon(String userProvidedLocation, Context mContext) {
        Geocoder geocoder = new Geocoder(mContext); // Here, “this” is an Activity
        try {
            List<Address> address = geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            double lat = address.get(0).getLatitude();
            double lon = address.get(0).getLongitude();
            return new double[] {lat, lon};
        } catch (IOException e) {
            // Failure to get an Address object
            Log.e("Location Details", "Failed to get latitude & longitude");
            return null;
        }
    }
}
