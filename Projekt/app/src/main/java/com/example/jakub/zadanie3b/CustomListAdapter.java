package com.example.jakub.zadanie3b;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class CustomListAdapter extends BaseAdapter {
    List<Pogoda> pogoda_list;
    Activity activity;

    public CustomListAdapter(Activity activity, List<Pogoda> pogoda_list) {
        this.activity = activity;
        this.pogoda_list = pogoda_list;
    }

    @Override
    public int getCount() {
        return pogoda_list.size();
    }

    @Override
    public Object getItem(int position) {
        return pogoda_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView miastoView = (TextView) convertView.findViewById(R.id.miasto);
        TextView temperaturaView = (TextView) convertView.findViewById(R.id.temperatura);
        TextView wilgotnoscView = (TextView) convertView.findViewById(R.id.wilgotnosc);
        TextView cisnienieView = (TextView) convertView.findViewById(R.id.cisnienie);
        TextView zachmurzenieView = (TextView) convertView.findViewById(R.id.zachmurzenie);
        TextView opadView = (TextView) convertView.findViewById(R.id.opad);

        Pogoda pogoda = pogoda_list.get(position);

        miastoView.setText("Miasto: " + pogoda.getMiasto());
        temperaturaView.setText("Temperatura: " + pogoda.getTemperatura() + "°C");
        wilgotnoscView.setText("Wilgotnosc: " + pogoda.getWilgotnosc()+ "%");
        cisnienieView.setText("Ciśnienie: " + pogoda.getCisnienie()+ "hPa");
        zachmurzenieView.setText("Zachmurzenie: " + pogoda.getZachmurzenie());
        opadView.setText("Opad: " + pogoda.getOpad());


        return convertView;
    }
}
