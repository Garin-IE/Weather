package com.example.destr.weather20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Weather extends AppCompatActivity implements View.OnClickListener{

    String city_key;
    String url;
    String test;
    JSONArray jsonForecast;
    int days_count;

    String[] base_urls = {
            "http://dataservice.accuweather.com/forecasts/v1/daily/1day/",
            "http://dataservice.accuweather.com/forecasts/v1/daily/5day/",
            "http://dataservice.accuweather.com/forecasts/v1/daily/10day/",
            "http://dataservice.accuweather.com/forecasts/v1/daily/15day/"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        Intent intent = getIntent();
        city_key = intent.getStringExtra("city_key");
        days_count = intent.getIntExtra("days", 1);
        switch (days_count) {
            case 1: url = base_urls[0]+city_key+"?apikey="+getString(R.string.api_key)+"&language=ru-ru"+"&metric=true";
            break;
            case 5: url = base_urls[1]+city_key+"?apikey="+getString(R.string.api_key)+"&language=ru-ru"+"&metric=true";
            break;
            case 10: url = base_urls[2]+city_key+"?apikey="+getString(R.string.api_key)+"&language=ru-ru"+"&metric=true";
            break;
            case 15: url = base_urls[3]+city_key+"?apikey="+getString(R.string.api_key)+"&language=ru-ru"+"&metric=true";
            break;
        }
        Log.d("myLogs", "url " + url);
        DataLoader loader = new DataLoader(new DataLoader.IDataResult() {
            @Override
            public void result(JSONArray res0) {
                try {
                    jsonForecast = res0.getJSONObject(0).getJSONArray("DailyForecasts");
                }
                catch (Exception e) {e.printStackTrace();}
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

    public void OnLoadComplete () {
        List<ForeCast> foreCasts = new ArrayList<ForeCast>();
        if (jsonForecast != null) {
            for (int i = 0; i < days_count; i++) {
                foreCasts.add(new ForeCast().parse2(jsonForecast, i));
            }
        }
        LinearLayout linearLayout = findViewById(R.id.lin);
        LayoutInflater inflater = getLayoutInflater();
        for (int i=0; i<days_count; i++) {
            View item = inflater.inflate(R.layout.forecast_layout, linearLayout, false);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String df = simpleDateFormat.format(new Date(foreCasts.get(i).getEpochdate()*1000));
            ((TextView) item.findViewById(R.id.day_date)).setText(df);
            ((TextView) item.findViewById(R.id.day_temp)).setText(String.valueOf(foreCasts.get(i).getMax_temp()));
            ((TextView) item.findViewById(R.id.night_temp)).setText(String.valueOf(foreCasts.get(i).getMin_temp()));
            ((TextView) item.findViewById(R.id.day_phrase)).setText(String.valueOf(foreCasts.get(i).getDay_phrase()));
            ((TextView) item.findViewById(R.id.night_phrase)).setText(String.valueOf(foreCasts.get(i).getNight_phrase()));
            ((ImageView) item.findViewById(R.id.day_icon)).setImageResource(get_day_draw(foreCasts.get(i)));
            ((ImageView) item.findViewById(R.id.night_icon)).setImageResource(get_night_draw(foreCasts.get(i)));
            linearLayout.addView(item);
        }
    }

    private int get_day_draw (ForeCast foreCast) {
        switch (foreCast.getDay_icon()){
            case 1: return R.drawable.i01;
            case 2: return R.drawable.i02;
            case 3: return R.drawable.i03;
            case 4: return R.drawable.i04;
            case 5: return R.drawable.i05;
            case 6: return R.drawable.i06;
            case 7: return R.drawable.i07;
            case 8: return R.drawable.i08;
            case 11: return R.drawable.i11;
            case 12: return R.drawable.i12;
            case 13: return R.drawable.i13;
            case 14: return R.drawable.i14;
            case 15: return R.drawable.i15;
            case 16: return R.drawable.i16;
            case 17: return R.drawable.i17;
            case 18: return R.drawable.i18;
            case 19: return R.drawable.i19;
            case 20: return R.drawable.i20;
            case 21: return R.drawable.i21;
            case 22: return R.drawable.i22;
            case 23: return R.drawable.i23;
            case 24: return R.drawable.i24;
            case 25: return R.drawable.i25;
            case 26: return R.drawable.i26;
            case 29: return R.drawable.i29;
            case 30: return R.drawable.i30;
            case 31: return R.drawable.i31;
            case 32: return R.drawable.i32;
            default: return R.drawable.ic_launcher_foreground;
        }
    }

    private int get_night_draw (ForeCast foreCast) {
        switch (foreCast.getNight_icon()) {
            case 7: return R.drawable.i07;
            case 8: return R.drawable.i08;
            case 11: return R.drawable.i11;
            case 12: return R.drawable.i12;
            case 15: return R.drawable.i15;
            case 18: return R.drawable.i18;
            case 19: return R.drawable.i19;
            case 22: return R.drawable.i22;
            case 24: return R.drawable.i24;
            case 25: return R.drawable.i25;
            case 26: return R.drawable.i26;
            case 29: return R.drawable.i29;
            case 30: return R.drawable.i30;
            case 31: return R.drawable.i31;
            case 32: return R.drawable.i32;
            case 33: return R.drawable.i33;
            case 34: return R.drawable.i34;
            case 35: return R.drawable.i35;
            case 36: return R.drawable.i36;
            case 37: return R.drawable.i37;
            case 38: return R.drawable.i38;
            case 39: return R.drawable.i39;
            case 40: return R.drawable.i40;
            case 41: return R.drawable.i41;
            case 42: return R.drawable.i42;
            case 43: return R.drawable.i43;
            case 44: return R.drawable.i44;
            default: return R.drawable.ic_launcher_foreground;
        }
    }
}
