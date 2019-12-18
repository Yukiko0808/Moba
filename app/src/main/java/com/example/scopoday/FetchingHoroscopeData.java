package com.example.scopoday;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchingHoroscopeData {

    private static final String URL_DATA_HOROSCOPE = "https://www.horoscopes-and-astrology.com/json";

    private Context con;
    private String dailyHoroscopeText = "";

    public String getDailyHoroscopeText() {
        return dailyHoroscopeText;
    }

    public FetchingHoroscopeData(){

    }

    public void loadDailyHoroscopeData(final String zodiac, Context con){


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
            URL_DATA_HOROSCOPE,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject zodicaHoroscopeJSON = jsonObject.getJSONObject("dailyhoroscope");
                        String zodicaHoroscopeText = zodicaHoroscopeJSON.getString(zodiac);
                        dailyHoroscopeText = zodicaHoroscopeText;
                        Log.i("FETCH", "horosko text " + zodiac +  ": " + dailyHoroscopeText);
                    }
                    catch (JSONException jsonE){
                        jsonE.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        RequestQueue requestQueue = Volley.newRequestQueue(con);
        requestQueue.add(stringRequest);

    }
}
