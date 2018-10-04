package com.example.destr.weather20;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityParser {
    public JSONArray jsonArray;

    public static City parse(JSONArray jsonArray, Integer i) {
        JSONObject jsonObject;
        JSONObject jsonObject1;
        City city = null;

        try {
            jsonObject = jsonArray.getJSONObject(i);
            jsonObject1 = jsonObject.getJSONObject("AdministrativeArea");
            city = new City();
            city.init(jsonObject.getString("LocalizedName"),jsonObject1.getString("LocalizedName"),jsonObject.getInt("Key"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

}
