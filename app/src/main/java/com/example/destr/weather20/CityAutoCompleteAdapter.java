package com.example.destr.weather20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CityAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private List<City> mResults;

    public CityAutoCompleteAdapter(Context context){
        mContext = context;
        mResults = new ArrayList<City>();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int index) {
        return mResults.get(index);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        City city = (City) getItem(position);
        ((TextView) convertView.findViewById(R.id.cityname)).setText(city.getName());
        ((TextView) convertView.findViewById(R.id.regionname)).setText(city.getAaname());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<City> citys = findCitys(constraint.toString() /*"Ивант"*/); //загрузка данных
                    filterResults.values = citys;
                    filterResults.count = citys.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResults = (List<City>) results.values;
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    // сюда обработчик загрузки данных!!!
    private List<City> findCitys(String cityName) {
        String url = mContext.getString(R.string.AC_base_url) + mContext.getString(R.string.api_key) + "&q=" + cityName + "&language=ru-ru";

        DataLoader loader;
        loader = new DataLoader(new DataLoader.IDataResult() {
            @Override
            public void result(JSONArray res0) {
                mResults.clear();
                for (int i = 0; i < res0.length(); i++){
                    mResults.add(CityParser.parse(res0, i));
                }
                notifyDataSetChanged();
            }
        });
        loader.execute(url);
        return mResults;
    }

}
