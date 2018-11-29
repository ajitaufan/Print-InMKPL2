package papb.learn.fauzan.printin;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import papb.learn.fauzan.printin.parser.JSONParserTask;

public class OrderMapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private LatLng deliveryLatLng = new LatLng(-7.9576019, 112.606947);
    private LatLng printInOfficeLatLng = new LatLng(-7.9471291, 112.613251);

    private Button btn_simpan_lokasi_kirim;
    private TextView tv_nilai_jarak,tv_nilai_biaya_antar;

    private String deliveryDistance;
    private String deliveryAdress;
    private String deliveryFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(printInOfficeLatLng).title("Print-In"));
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).
                        title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));

                calculateDistance(printInOfficeLatLng, place.getLatLng());
                Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                printInOfficeLatLng, deliveryLatLng));
                deliveryAdress = (String) place.getName();
            }

            @Override
            public void onError(Status status) {

            }
        });

        btn_simpan_lokasi_kirim = findViewById(R.id.btn_simpan_lokasi_kirim);
        btn_simpan_lokasi_kirim.setOnClickListener(this);
        tv_nilai_jarak = findViewById(R.id.tv_nilai_jarak);
        tv_nilai_biaya_antar = findViewById(R.id.tv_nilai_harga_antar);
    }

    public String calculateDistance(LatLng delhi, LatLng place){
        Location printInOffeiceLoc = new Location("Print-In");
        printInOffeiceLoc.setLatitude(delhi.latitude);
        printInOffeiceLoc.setLongitude(delhi.longitude);

        Location your_location = new Location("You");
        your_location.setLatitude(place.latitude);
        your_location.setLongitude(place.longitude);

        double distance = (printInOffeiceLoc.distanceTo(your_location)) * 0.000621371 ;
        String stringDistance = String.format("%.2f", distance)+" "+getString(R.string.dist_measure_unit);

        if(distance <= 2){
            deliveryFee = "Rp. 2000";
        } else if(distance < 4){
            deliveryFee ="Rp. 4000";
        } else {
            deliveryFee = "Rp. 5000";
        }

        tv_nilai_jarak.setText(stringDistance);
        tv_nilai_biaya_antar.setText(deliveryFee);

        deliveryDistance = stringDistance;

        return stringDistance;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simpan_lokasi_kirim:
                Intent toOrderSummary = new Intent(this,OrderSummaryActivity.class);
                toOrderSummary.putExtra("deliveryFee",deliveryFee);
                toOrderSummary.putExtra("deliveryDistance",deliveryDistance);
                toOrderSummary.putExtra("deliveryAdress",deliveryAdress);
                startActivity(toOrderSummary);
                break;
        }
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";

            try {
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(s);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(deliveryLatLng).title("Chandigarh"));
        mMap.addMarker(new MarkerOptions().position(printInOfficeLatLng).title("Print-In"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(printInOfficeLatLng));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("You");

                // Clears the previously touched position
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(printInOfficeLatLng).title("Print-In"));

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

                calculateDistance(printInOfficeLatLng, latLng);
                Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                printInOfficeLatLng, latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });

        String str_origin = "origin=" + printInOfficeLatLng.latitude + "," + printInOfficeLatLng.longitude;
        String str_dest = "destination=" + deliveryLatLng.latitude + "," + deliveryLatLng.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + JSONParserTask.MY_API_KEY;

        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl = new FetchUrl();
        FetchUrl.execute(url);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveryLatLng, 15));

        calculateDistance(printInOfficeLatLng, deliveryLatLng);
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        printInOfficeLatLng, deliveryLatLng));
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        //TODO (1) : read and understand the code below
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                JSONParserTask parser = new JSONParserTask();
                Log.d("ParserTask", parser.toString());
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }
}
