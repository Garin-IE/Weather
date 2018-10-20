package com.example.destr.weather20;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class ForeCast {

    public long epochdate;
    public int min_temp;
    public int max_temp;
    public int day_icon;
    public  int night_icon;

    public void init(long edate, int i, int i2, int i3, int i4){
        epochdate = edate;
        min_temp = i;
        max_temp = i2;
        day_icon = i3;
        night_icon = i4;
    }

    public long getEpochdate() {return epochdate;}

    public int getMin_temp() {return min_temp;}

    public int getMax_temp() {return max_temp;}

    public int getDay_icon() {return day_icon;}

    public int getNight_icon() {return night_icon;}

    public ForeCast parse(JSONArray jsonArray) {

        JSONObject jsonObject;
        ForeCast forecast;
        long edate;
        int min_temp;
        int max_temp;
        int day_icon;
        int night_icon;

        forecast = new ForeCast();

        try {
            jsonObject = jsonArray.getJSONObject(0);
            edate = jsonObject.getJSONObject("Headline").getLong("EffectiveEpochDate");
            min_temp = jsonObject.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
            max_temp = jsonObject.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
            day_icon = jsonObject.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Day").getInt("Icon");
            night_icon = jsonObject.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Night").getInt("Icon");
            //Log.d("myLogs", "icons: " + day_icon + " " + night_icon);
            forecast.init(edate, min_temp, max_temp, day_icon, night_icon);
            //Log.d("myLogs", "edat in forecast " + forecast.getEpochdate());
        } catch (Exception e) {e.printStackTrace();}
        return forecast;
    }

}
