package com.example.destr.weather20;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    City city = new City();
    Button btnOK;
    int days_count = 1;
    String geolocation_url =
            "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
    LocationManager locationManager;
    Boolean gpsflag = false;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(getString(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);

        final DelayAutoCompleteTextView cityName = findViewById(R.id.cityname);
        cityName.setThreshold(4);
        cityName.setAdapter(new CityAutoCompleteAdapter(this));
        cityName.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar));
        cityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
                city = (City) adapterview.getItemAtPosition(position);
                cityName.setText(city.getName(), false);
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow
                        (cityName.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        btnOK = findViewById(R.id.OK);
        btnOK.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    @Override
    public void onClick(View v) {
       if (city.getKey() != null) {
            Intent intent = new Intent(this, Weather.class);
            intent.putExtra("city_key", city.key.toString());
            intent.putExtra("days", days_count);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Выберите Город", Toast.LENGTH_LONG).show();
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (mSettings.contains(getString(R.string.DAYS_PREFERENCE)) & mSettings.contains(getString(R.string.GPS_PREFERENCE))) {
            menu.findItem(R.id.menu_gps).setChecked(mSettings.getBoolean(getString(R.string.GPS_PREFERENCE), false));
            switch (mSettings.getInt(getString(R.string.DAYS_PREFERENCE),1)){
                case 1: menu.findItem(R.id.days_1).setChecked(true); break;
                case 5: menu.findItem(R.id.days_5).setChecked(true); break;
                case 10: menu.findItem(R.id.days_10).setChecked(true); break;
                case 15: menu.findItem(R.id.days_15).setChecked(true); break;
            }
        }
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

    public void menu_gps_onClickListner(MenuItem menuItem){
        if (!menuItem.isChecked()) {
            menuItem.setChecked(true);
            gpsflag = true;
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        10*1000, 10, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        10*1000, 10, locationListener);
            }
            else ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else  {
            menuItem.setChecked(false);
            gpsflag = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10*1000, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10*1000, 10, locationListener);
        }
        else ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (mSettings.contains(getString(R.string.DAYS_PREFERENCE)) & mSettings.contains(getString(R.string.GPS_PREFERENCE))) {
            gpsflag = mSettings.getBoolean(getString(R.string.GPS_PREFERENCE), false);
            days_count = mSettings.getInt(getString(R.string.DAYS_PREFERENCE),1);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(getString(R.string.GPS_PREFERENCE), gpsflag);
        editor.putInt(getString(R.string.DAYS_PREFERENCE), days_count);
        editor.apply();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            cityLoader_byLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void cityLoader_byLocation(Location location){
        String url;
        if (gpsflag){
            if (location != null) {
                url= geolocation_url+"?apikey="+getString(R.string.api_key) +"&q="
                        +String.valueOf(location.getLatitude())+"%2C"
                        +String.valueOf(location.getLongitude())+"&language=ru-ru";
                DataLoader loader = new DataLoader(new DataLoader.IDataResult() {
                @Override
                public void result(JSONArray res0) {
                    JSONObject jsonObject = null;
                        try {
                            jsonObject = res0.getJSONObject(0);
                        }
                        catch (Exception e) {e.printStackTrace();}
                        city = OnLoadComplete_GPS(jsonObject);
                    }
                });
                Log.d("myLogs", url);
                loader.execute(url);
            }
        }
    }

    private City OnLoadComplete_GPS(JSONObject jsonObject) {
        City city = new City();
        try {
            city.init(jsonObject.getString("LocalizedName"),
                    jsonObject.getJSONObject("Country").getString("LocalizedName"),
                    jsonObject.getInt("Key"));
        }
        catch (Exception e) {e.printStackTrace();}
        Log.d("myLogs", city.getAaname() +" "+city.getName()+" "+city.getKey());
        final DelayAutoCompleteTextView cityName = findViewById(R.id.cityname);
        cityName.setText(city.getName(), false);
        return city;
    }
}
