package com.example.destr.weather20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    TextView epochdate;
    TextView tvmaxtemp;
    TextView tvmintemp;
    ImageView iv_day;


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

        Date date;

        ForeCast foreCast;
        foreCast = new ForeCast();
        foreCast = foreCast.parse(jsonForecast);

        epochdate = findViewById(R.id.tv_epochdate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        date = new Date(foreCast.getEpochdate()*1000);
        String df = simpleDateFormat.format(date);
        epochdate.setText(df);

        Log.d("myLogs", "forecast " + foreCast.getEpochdate() + " " + foreCast.getMax_temp() + " " + foreCast.getMin_temp() + " " +foreCast.getDay_icon() + " " + foreCast.getNight_icon());

        tvmaxtemp = findViewById(R.id.tv_max_temp);
        tvmaxtemp.setText(String.valueOf(foreCast.getMax_temp()));
        tvmintemp = findViewById(R.id.tv_min_temp);
        tvmintemp.setText(String.valueOf(foreCast.getMin_temp()));
        iv_day = findViewById(R.id.iv_day_icon);
        switch (foreCast.getDay_icon()){
            case 1: iv_day.setImageResource(R.drawable.i01);
            break;
            case 2: iv_day.setImageResource(R.drawable.i02);
            break;
            case 3: iv_day.setImageResource(R.drawable.i03);
            break;
            case 4: iv_day.setImageResource(R.drawable.i04);
            break;
            case 5: iv_day.setImageResource(R.drawable.i05);
            break;
            case 6: iv_day.setImageResource(R.drawable.i06);
            break;
            case 7: iv_day.setImageResource(R.drawable.i07);
            break;
            case 8: iv_day.setImageResource(R.drawable.i08);
            break;
            case 11: iv_day.setImageResource(R.drawable.i11);
            break;
            case 12: iv_day.setImageResource(R.drawable.i12);
            break;
            case 13: iv_day.setImageResource(R.drawable.i13);
            break;
            case 14: iv_day.setImageResource(R.drawable.i14);
            break;
            case 15: iv_day.setImageResource(R.drawable.i15);
            break;
            case 16: iv_day.setImageResource(R.drawable.i16);
            break;
            case 17: iv_day.setImageResource(R.drawable.i17);
            break;
            case 18: iv_day.setImageResource(R.drawable.i18);
            break;
            case 19: iv_day.setImageResource(R.drawable.i19);
            break;
            case 20: iv_day.setImageResource(R.drawable.i20);
            break;
            case 21: iv_day.setImageResource(R.drawable.i21);
            break;
            case  22: iv_day.setImageResource(R.drawable.i22);
            break;
            case 23: iv_day.setImageResource(R.drawable.i23);
            break;
            case 24: iv_day.setImageResource(R.drawable.i24);
            break;
            case 25: iv_day.setImageResource(R.drawable.i25);
            break;
            case 26: iv_day.setImageResource(R.drawable.i26);
            break;
            case 29: iv_day.setImageResource(R.drawable.i29);
            break;
            case 30: iv_day.setImageResource(R.drawable.i30);
            break;
            case 31: iv_day.setImageResource(R.drawable.i31);
            break;
            case 32: iv_day.setImageResource(R.drawable.i32);
            break;
        }

        ImageView iv_night;
        iv_night = findViewById(R.id.iv_night_icon);
        switch (foreCast.getNight_icon()) {
            case 7: iv_night.setImageResource(R.drawable.i07);
            break;
            case 8: iv_night.setImageResource(R.drawable.i08);
            break;
            case 11: iv_night.setImageResource(R.drawable.i11);
            break;
            case 12: iv_night.setImageResource(R.drawable.i12);
            break;
            case 15: iv_night.setImageResource(R.drawable.i15);
            break;
            case 18: iv_night.setImageResource(R.drawable.i18);
            break;
            case 19: iv_night.setImageResource(R.drawable.i19);
            break;
            case 22: iv_night.setImageResource(R.drawable.i22);
            break;
            case 24: iv_night.setImageResource(R.drawable.i24);
            break;
            case 25: iv_night.setImageResource(R.drawable.i25);
            break;
            case 26: iv_night.setImageResource(R.drawable.i26);
            break;
            case 29: iv_night.setImageResource(R.drawable.i29);
            break;
            case 30: iv_night.setImageResource(R.drawable.i30);
            break;
            case 31: iv_night.setImageResource(R.drawable.i31);
            break;
            case 32: iv_night.setImageResource(R.drawable.i32);
            break;
            case 33: iv_night.setImageResource(R.drawable.i33);
            break;
            case 34: iv_night.setImageResource(R.drawable.i34);
            break;
            case 35: iv_night.setImageResource(R.drawable.i35);
            break;
            case 36: iv_night.setImageResource(R.drawable.i36);
            break;
            case 37: iv_night.setImageResource(R.drawable.i37);
            break;
            case 38: iv_night.setImageResource(R.drawable.i38);
            break;
            case 39: iv_night.setImageResource(R.drawable.i39);
            break;
            case 40: iv_night.setImageResource(R.drawable.i40);
            break;
            case 41: iv_night.setImageResource(R.drawable.i41);
            break;
            case 42: iv_night.setImageResource(R.drawable.i42);
            break;
            case 43: iv_night.setImageResource(R.drawable.i43);
            break;
            case 44: iv_night.setImageResource(R.drawable.i44);
            break;
        }
    }
}
