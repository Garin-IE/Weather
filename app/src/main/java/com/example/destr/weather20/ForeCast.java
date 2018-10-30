package com.example.destr.weather20;

import org.json.JSONArray;

public class ForeCast {

    public long epochdate;
    public int min_temp;
    public int max_temp;
    public int day_icon;
    public  int night_icon;
    private String day_phrase;
    private String night_phrase;

    public void init(long edate, int i, int i2, int i3, int i4, String s1, String s2){
        epochdate = edate;
        min_temp = i;
        max_temp = i2;
        day_icon = i3;
        night_icon = i4;
        day_phrase = s1;
        night_phrase = s2;
    }

    public long getEpochdate() {return epochdate;}

    public int getMin_temp() {return min_temp;}

    public int getMax_temp() {return max_temp;}

    public int getDay_icon() {return day_icon;}

    public int getNight_icon() {return night_icon;}

    public String getDay_phrase() {return day_phrase;}

    public String getNight_phrase() {return night_phrase;}

    public ForeCast parse(JSONArray jsonArray) {

        ForeCast forecast;
        long edate;
        int min_temp;
        int max_temp;
        int day_icon;
        int night_icon;
        String day_phrase;
        String night_phrase;

        forecast = new ForeCast();

        try {
            edate = jsonArray.getJSONObject(0).getLong("EpochDate");
            min_temp = jsonArray.getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
            max_temp = jsonArray.getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
            day_icon = jsonArray.getJSONObject(0).getJSONObject("Day").getInt("Icon");
            day_phrase = jsonArray.getJSONObject(0).getJSONObject("Day").getString("IconPhrase");
            night_icon = jsonArray.getJSONObject(0).getJSONObject("Night").getInt("Icon");
            night_phrase = jsonArray.getJSONObject(0).getJSONObject("Night").getString("IconPhrase");
            forecast.init(edate, min_temp, max_temp, day_icon, night_icon, day_phrase, night_phrase);
        } catch (Exception e) {e.printStackTrace();}
        return forecast;
    }

    public ForeCast parse2(JSONArray jsonArray, int i) {

        ForeCast forecast;
        long edate;
        int min_temp;
        int max_temp;
        int day_icon;
        int night_icon;
        String day_phrase;
        String night_phrase;

        forecast = new ForeCast();

        try {
            edate = jsonArray.getJSONObject(i).getLong("EpochDate");
            min_temp = jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
            max_temp = jsonArray.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
            day_icon = jsonArray.getJSONObject(i).getJSONObject("Day").getInt("Icon");
            day_phrase = jsonArray.getJSONObject(i).getJSONObject("Day").getString("IconPhrase");
            night_icon = jsonArray.getJSONObject(i).getJSONObject("Night").getInt("Icon");
            night_phrase = jsonArray.getJSONObject(i).getJSONObject("Night").getString("IconPhrase");
            forecast.init(edate, min_temp, max_temp, day_icon, night_icon, day_phrase, night_phrase);
        } catch (Exception e) {e.printStackTrace();}
        return forecast;
    }

}
