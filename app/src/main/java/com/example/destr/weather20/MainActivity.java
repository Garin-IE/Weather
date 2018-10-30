package com.example.destr.weather20;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    City city = new City();
    Button btnOK;
    int days_count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DelayAutoCompleteTextView cityName = (DelayAutoCompleteTextView) findViewById(R.id.cityname);
        cityName.setThreshold(4);
        cityName.setAdapter(new CityAutoCompleteAdapter(this));
        cityName.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar));
        cityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
                city = (City) adapterview.getItemAtPosition(position);
                cityName.setText(city.getName());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cityName.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        btnOK = (Button) findViewById(R.id.OK);
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Weather.class);
        intent.putExtra("city_key", city.key.toString());
        intent.putExtra("days", days_count);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.days_1 :
                days_count = 1;
                item.setChecked(true);
                return true;
            case R.id.days_5 :
                days_count = 5;
                item.setChecked(true);
                return true;
            case R.id.days_10 :
                days_count = 10;
                item.setChecked(true);
                return true;
            case R.id.days_15 :
                days_count = 15;
                item.setChecked(true);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}
