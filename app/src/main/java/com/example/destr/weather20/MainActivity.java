package com.example.destr.weather20;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    City city = new City();
    Button btnOK;

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
        /*if (city != null) {
            Toast.makeText(this, city.getKey().toString(), Toast.LENGTH_LONG).show();
        }*/

        Intent intent = new Intent(this, Weather.class);
        intent.putExtra("city_key", city.key.toString());
        startActivity(intent);
    }

}
