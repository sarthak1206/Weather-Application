package com.e.weatherappassignment.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.e.weatherappassignment.view.activity.addlocation.AddLocationActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceParser {
    public static String latitude, longitude;

    public void getAddress(Context context, String params) {
        new ExecuteAddress(context).execute(params);
    }


    private class ExecuteAddress extends AsyncTask<String, String, String> {

        Context mContext;

        ExecuteAddress(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String place_url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
            StringBuilder sb = new StringBuilder(place_url + params[0]);
            sb.append("&key=" + AddLocationActivity.API_KEY);

            HttpHandler httpHandler = new HttpHandler();
            String json = httpHandler.makeServiceCall(sb.toString());
            Log.e("Url", sb + "");

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.e("Addressss", s + "");
                JSONObject jsonObj = new JSONObject(s);
                JSONObject jsonObject = jsonObj.getJSONObject("result");
                JSONObject jsonObject1 = jsonObject.getJSONObject("geometry");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("location");
                latitude = jsonObject2.getString("lat");
                longitude = jsonObject2.getString("lng");

                Constants.serachlat = latitude;
                Constants.serachlng = longitude;

                EventBus.getDefault().post(new Event(Constants.LATLONG,""));


            } catch (JSONException e) {


                Log.e("", "Cannot process JSON results", e);

            }
        }
    }
}
