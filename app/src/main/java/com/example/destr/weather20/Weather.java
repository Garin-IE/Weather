package com.example.destr.weather20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Weather extends AppCompatActivity implements View.OnClickListener{

    String city_key;
    String url;
    String test;
    JSONArray jsonForecast;
    ForeCast foreCast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        TextView epochdate = (TextView) findViewById(R.id.tv_epochdate);
        TextView tvmaxtemp = (TextView) findViewById(R.id.tv_max_temp);
        TextView tvmintemp = (TextView) findViewById(R.id.tv_min_temp);

        Intent intent = getIntent();


        city_key = intent.getStringExtra("city_key");
        url = getString(R.string.forecast_base_url) + city_key + "?apikey=" + getString(R.string.api_key) + "&language=ru-ru" + "&metric=true";

        DataLoader loader = new DataLoader(new DataLoader.IDataResult() {
            @Override
            public void result(JSONArray res0) {
                jsonForecast = res0;
                foreCast = foreCast.parse(jsonForecast,0);
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

        TextView epochdate = (TextView) findViewById(R.id.tv_epochdate);
        TextView tvmaxtemp = (TextView) findViewById(R.id.tv_max_temp);
        TextView tvmintemp = (TextView) findViewById(R.id.tv_min_temp);

        tvmaxtemp.setText(foreCast.getMax_temp());
        tvmintemp.setText(foreCast.getMin_temp());
    }
}
