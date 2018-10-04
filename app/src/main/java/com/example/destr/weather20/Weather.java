package com.example.destr.weather20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather extends AppCompatActivity implements View.OnClickListener{

    String city_key;
    String url;
    String test;
    JSONArray jsonForecast;
    ForeCast foreCast;
    TextView epochdate;
    TextView tvmaxtemp;
    TextView tvmintemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);


        Intent intent = getIntent();


        city_key = intent.getStringExtra("city_key");
        url = getString(R.string.forecast_base_url) + city_key + "?apikey=" + getString(R.string.api_key) + "&language=ru-ru" + "&metric=true";

        DataLoader loader = new DataLoader(new DataLoader.IDataResult() {
            @Override
            public void result(JSONArray res0) {
                jsonForecast = res0;
                OnLoadComplete();
            }
        });
        loader.execute(url);

    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this, test, Toast.LENGTH_LONG).show();
        Log.d("myLogs", "JA main const: " + jsonForecast.length());
    }

    public void OnLoadComplete (){
        JSONObject object = new JSONObject();
        JSONObject object1 = new JSONObject();
        String s = new String();
        long epochdat = 0;
        Date date;

        try {
            object = jsonForecast.getJSONObject(0);
            object1 = object.getJSONObject("Headline");
            s = object1.getString("EffectiveEpochDate").toString();
            //epochdat = object1.getLong("EffectiveEpochDate");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("myLogs", "json convert fail" + e.getMessage());
        }
        Log.d("myLogs", "OnLoadComplete: " + object.toString());

        epochdate = findViewById(R.id.tv_epochdate);

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        //date = new Date(epochdat);

        //epochdate.setText(date.toString());
    }
}
