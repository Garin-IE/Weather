package com.example.destr.weather20;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataLoader extends AsyncTask<String, Void, JSONArray> {

    IDataResult callback;


    public interface IDataResult {
        void result(JSONArray res0);
    }


    public DataLoader(IDataResult callback) {
        this.callback = callback;
    }


    @Override
    protected JSONArray doInBackground(String ...  params) {
        //toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        HttpURLConnection connection = null;
        JSONArray jsonArray = null;
        char ja_att = '[';
        char ja_end = ']';
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null){
                    builder.append(line);
                }
                //Log.d("myLogs", "doInBackground: dataloader" + builder.charAt(0));
                Log.d("myLogs", "boolean " + (builder.charAt(0) == ja_att));
                if (builder.charAt(0) == ja_att){
                    jsonArray = new JSONArray(builder.toString());
                    Log.d("myLogs", "first load const: " + jsonArray.toString());
                }
                else {
                    StringBuilder builder2 = new StringBuilder();
                    builder2.append(ja_att);
                    builder2.append(builder.toString());
                    builder2.append(ja_end);
                    jsonArray  = new JSONArray(builder2.toString());
                    Log.d("myLogs", "JsonArray const: " + jsonArray.toString());
                }
            }
            else {
                Log.d("myLogs", "Http Error: "+ response);
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                return null;
            }
        }
        catch (Exception e) {e.printStackTrace();}
        finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        this.callback.result(jsonArray);
    }
}
