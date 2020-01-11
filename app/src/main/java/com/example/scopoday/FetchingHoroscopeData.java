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

    // Success Callback um Antwort des Webserver abzuwarten um Daten danach anzuzeigen zu können
    public interface ServerCallback{
        //void onSuccess(JSONObject result);
        void  onSuccess(String result);
    }

    // Funktion die Horoskop Texte von Webseite holt
    public void loadDailyHoroscopeData(final String zodiac, Context con, final ServerCallback callback){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
            URL_DATA_HOROSCOPE, // String = "https://www.horoscopes-and-astrology.com/json"
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //JSON Objekt erstellen
                        JSONObject jsonObject = new JSONObject(response);

                        //Reinspringen in horoskop-Jason-Objekt auf dem Webserver
                        JSONObject zodicaHoroscopeJSON = jsonObject.getJSONObject("dailyhoroscope");

                        //Reinspringen in Sternzeichen-JSon-Objekt des Webservers
                        //Richitges Sternzeichen auswählen (Sternzeichen-String wird aus Contact Activity mitgegeben -> String zodiac)
                        String zodicaHoroscopeText = zodicaHoroscopeJSON.getString(zodiac);



                        dailyHoroscopeText = zodicaHoroscopeText.substring(0, zodicaHoroscopeText.indexOf("<"));
                        Log.i("FETCH", "horosko text " + zodiac +  ": " + dailyHoroscopeText);

                        //Bei Erfolg zurückgeben
                        callback.onSuccess(response);

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
