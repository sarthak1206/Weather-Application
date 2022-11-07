package com.e.weatherappassignment.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;
import java.util.Locale;

public class Utils {
    public static Utils mInstanse;

    public Utils() {
        mInstanse = this;
    }

    public static Utils getInstanse() {
        return mInstanse;
    }


    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null&&addresses.size()>0) {
                Address returnedAddress = addresses.get(0);
                Log.e("fullllllllll", returnedAddress + "");
                StringBuilder strReturnedAddress = new StringBuilder("");

                String country = returnedAddress.getCountryName();
                String city = returnedAddress.getLocality();
                Log.e("dddddf", country + "   " + city);
                strReturnedAddress.append(returnedAddress.getAddressLine(0));

                if (returnedAddress.getSubLocality() != null)
                    strReturnedAddress.append(", ").append(returnedAddress.getSubLocality());

                strAdd = strReturnedAddress.toString();
                // Log.e(TAG, "address-- "+strReturnedAddress.toString());
            } else {
                Log.e("", "No address returned");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }


    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean checkGPSStatus(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    };
}
