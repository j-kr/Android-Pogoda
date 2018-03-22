package com.example.jakub.zadanie3b;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends ActionBarActivity implements LocationListener {

    private LocationManager locationManager;
    Button domParserButton;
    EditText szukajMiasto;
    TextView szukajSz;
    TextView szukajDl;
    //String szukaneMiasto;
    String szukaneSz;
    String szukaneDl;
    int znalezioneSz;
    int znalezioneDl;
    private ListView listView;
    List<Pogoda> pogoda_list;
    ProgressDialog waitProgress;
    private CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
    }

    private void initControls() {

        domParserButton = (Button) findViewById(R.id.button);
        listView=(ListView) findViewById(R.id.listView);
        //szukajMiasto=(EditText) findViewById(R.id.editMiasto);
        szukajSz=(TextView) findViewById(R.id.editszerokosc) ;
        szukajDl=(TextView) findViewById(R.id.editdlugosc) ;

        domParserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //szukaneMiasto = szukajMiasto.getText().toString();
                //szukaneSz = szukajSz.getText().toString();
                //szukaneDl = szukajDl.getText().toString();
                new BackgroundTask().execute();

            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, 1, this);

    }

    public class BackgroundTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            waitProgress = ProgressDialog.show(MainActivity.this, "",
                    "Proszę czekać...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (waitProgress != null) {
                waitProgress.dismiss();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            displayData();
            if (waitProgress != null) {
                waitProgress.dismiss();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                synchronized (this) {

                    domParser();

                    publishProgress(25);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    private void domParser() {

        try {
            pogoda_list = new ArrayList<Pogoda>();
            String url = "http://api.openweathermap.org/data/2.5/weather?lat="+znalezioneSz+"&lon="+znalezioneDl+"&units=metric&mode=xml&APPID=191ccc6e173f58a240625baa3eb9ebe2";

            DomXmlParser domParser = new DomXmlParser();
            Document document = domParser.getDocument(url);
            document.getDocumentElement().normalize();
            NodeList nodelist = document.getElementsByTagName("current");
            Log.i("DomParser", "nodelist: " + nodelist.getLength());

            for (int i = 0; i < nodelist.getLength(); i++) {

                Node node = nodelist.item(i);
                Pogoda pogoda = new Pogoda();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String miasto = domParser.getAtributeNameValue(element, "city");
                    String temperatura = domParser.getAtributeValueValue(element, "temperature");
                    String wilgotnosc = domParser.getAtributeValueValue(element, "humidity");
                    String cisnienie = domParser.getAtributeValueValue(element, "pressure");
                    String zachmurzenie = domParser.getAtributeNameValue(element, "clouds");
                    String opad = domParser.getAtributeValueValue(element, "weather");
                    pogoda.setMiasto(miasto);
                    pogoda.setTemperatura(temperatura);
                    pogoda.setWilgotnosc(wilgotnosc);
                    pogoda.setCisnienie(cisnienie);
                    pogoda.setZachmurzenie(zachmurzenie);
                    pogoda.setOpad(opad);

                    pogoda_list.add(pogoda);


                }
                Log.i("DomParser", "pogodaList: " + pogoda_list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void displayData(){
        adapter = new CustomListAdapter(this, pogoda_list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    @Override
    public void onLocationChanged(Location location) {
        //String msg1 = String.valueOf(location.getLatitude());
        //String msg2 = String.valueOf(location.getLongitude());

        String msg1 = Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);
        String msg2 = Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);

        szukajSz.setText(msg1);
        szukajDl.setText(msg2);
        znalezioneSz = Integer.parseInt(szukajSz.getText().toString());
        znalezioneDl = Integer.parseInt(szukajDl.getText().toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }


}
