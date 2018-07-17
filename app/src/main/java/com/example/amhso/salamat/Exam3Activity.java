package com.example.amhso.salamat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.amhso.salamat.otherclass.Area;
import com.example.amhso.salamat.otherclass.G;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Exam3Activity extends FragmentActivity implements
        OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener {



    private GoogleMap googleMap;
    PolylineOptions polylineOptions;
    private boolean checkClick = false;
    public Area a[];

    public Button b;
    public String q10;
    private CheckBox box1,box2,box3,box4,box5,box6;

    private LatLng myLocation_latlng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam3);

        G.activity=this;








        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        googleMap = fm.getMap();

        // display zoom map


        myLocation_latlng=new LatLng(35.6811434,51.3781643);
        getLocation();


        Log.i("rrrrrrrrrrrrr", "rrrrrrrrrrrrr: "+myLocation_latlng.latitude+"--------"+myLocation_latlng.longitude);



        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMarkerClickListener(this);


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation_latlng, 14));






        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver + "range");


        Button sendlocation=(Button) findViewById(R.id.send2);
        sendlocation.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                box1=(CheckBox)findViewById(R.id.box1);
                if(box1.isChecked()){
                    q10 +="بیماری ایدز \n";
                }

                box2=(CheckBox)findViewById(R.id.box2);
                if(box2.isChecked()){
                    q10 +="بیماری اضطراب \n";
                }

                box3=(CheckBox)findViewById(R.id.box3);
                if(box3.isChecked()){
                    q10 +="بیماری اعتیاد \n";
                }

                box4=(CheckBox)findViewById(R.id.box4);
                if(box4.isChecked()){
                    q10 +="بیماری افسردگی \n";
                }

                box5=(CheckBox)findViewById(R.id.box5);
                if(box5.isChecked()){
                    q10 +="بیماری وبا \n";
                }

                box6=(CheckBox)findViewById(R.id.box6);
                if(box6.isChecked()){
                    q10 +="فلج اطفال \n";
                }

                    HttpPostAsyncTask2 task = new HttpPostAsyncTask2();
                    task.execute(G.urlserver + "select_area_latlng");

            }


        });


    }










    public void getLocation() {

        try {


            LocationManager locationManager = (LocationManager) G.activity.getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();


            }


            if (ActivityCompat.checkSelfPermission(G.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(G.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                Log.i("bbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                return;
            }

//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            //get last known location to start with
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (myLocation != null) {
                myLocation_latlng=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                Log.i("bbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            }



        }
        catch (Exception s){

            Log.i("errrror", "get EEEEEEEEEEEEEEEEEEEEEEEE: "+s.toString());
        }


    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {



        Log.i("rrrrrrrrrrrrrrr", "onRequestPermissionsResult: "+requestCode);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i("uuuuuuuuuuuuuuuuuuu", "truuuuuuuuuuuuuuu: "+requestCode);

                    LocationManager locationManager = (LocationManager) G.activity.getSystemService(Context.LOCATION_SERVICE);

                    //get last known location to start with
                    Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                    if (myLocation != null) {
                        myLocation_latlng=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                        Log.i("bbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                    }



                } else {
                    Intent i = new Intent(G.activity,Exam2Activity.class);
                    startActivity(i);

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }






    @Override
    public void onMapClick(LatLng point) {





    }





    @Override
    public void onMapLongClick(LatLng point) {
//
    }



    public void countPolygonPoints() {


        for (int i=0; i < a.length; i++){



            ArrayList<LatLng> arrayPoints = null;
            arrayPoints = new ArrayList<LatLng>();
            LatLng lng[]=a[i].getLatlng();



            for (int j=0; j< lng.length ; j++){

//                if(j==0){
//                    googleMap.addMarker(new MarkerOptions().position(lng[j]).icon(
//                            BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//                }



                arrayPoints.add(lng[j]);

            }

            if (arrayPoints.size() >= 3) {





                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.addAll(arrayPoints);
                polygonOptions.strokeColor(Color.argb(90,0,200,136));
                polygonOptions.strokeWidth(1);
                polygonOptions.fillColor(Color.argb(90,0,200,136));

                Polygon polygon = googleMap.addPolygon(polygonOptions);
            }
        }

    }




    @Override
    public boolean onMarkerClick(Marker marker) {



//        LatLng marker_click=marker.getPosition();
//        Log.i("ddd", "aaaaaaaaaaaaaaaaaaa"+marker_click.longitude);


//        for (int i=0; i < a.length; i++){
//
//
//            LatLng lng[]=a[i].getLatlng();
//
//            for (int j=0; j< lng.length ; j++){
//
//                if(lng[j].longitude==marker_click.longitude && lng[j].latitude==marker_click.latitude){
//                    id_range_click=a[i].getId();
//                }
//            }
//        }


        return false;
    }







    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("نیاز به GPS داریم آن را فعال کنید")
                .setCancelable(false)
                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("نیازی ندارم", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }









    public class HttpPostAsyncTask2 extends AsyncTask<String, String, String> {



        HttpPost httppost;


        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;


        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);



            if(result.equals("1")){


                SharedPreferences shpref_login1 = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
                SharedPreferences.Editor sh_edit = shpref_login1.edit();
                sh_edit.putBoolean("select_area", true);
                sh_edit.apply();


                Intent i=new Intent(G.activity,MainActivity.class);
                G.activity.startActivity(i);


            }
            else{

                Toast.makeText(G.activity,"خطایی در سرور رخ داده است لطفا شبکه خود را چک کنید", Toast.LENGTH_SHORT).show();

            }


        }







        @Override
        protected void onPreExecute() {



        }



        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {

            try {


                Log.i("urluuuuuuuuuuuuuuu", "doInBackground: "+params[0]);

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost(params[0]); // make sure the url is correct.
                //add your data

                Log.i("uuuuuu", "urluuuuuuuuuuuu "+params[0]);
                nameValuePairs = new ArrayList<NameValuePair>(15);

                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("lat_lng", (myLocation_latlng.latitude +":"+myLocation_latlng.longitude).trim()));
                nameValuePairs.add(new BasicNameValuePair("q10", q10.trim()));


                Log.i("lanlng", "lanlnglanlnglanlnglanlnglanlnglanlng: "+myLocation_latlng.latitude +":"+myLocation_latlng.longitude);

                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);

                nameValuePairs.add(new BasicNameValuePair("id_user",shpref.getString("id_user","-1").trim()));
                nameValuePairs.add(new BasicNameValuePair("phone",shpref.getString("phone","-1").trim()));

                Log.i("ddddddd", "ddddddddddd: "+shpref.getString("id_user","-1").trim()+"gggg"+shpref.getString("phone","-1").trim());


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
                //Execute HTTP Post Request
//                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return null;
        }
    }



    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {



        HttpPost httppost;


        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;





        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);
//


            try {


                JSONArray range;
                JSONObject jsonObj = new JSONObject(result);
                range = jsonObj.getJSONArray("range");

                a=new Area[range.length()];

                LatLng latLng[];

                for (int i = 0; i < range.length(); i++) {


                    JSONObject c = range.getJSONObject(i);
                    String idr = c.getString("id");
                    String title = c.getString("name");
//                    String lng = c.getString("lng");
//                    String id_range = c.getString("id_range");




                    try {


                        JSONArray coordinate;
                        JSONObject jsonObjc = new JSONObject(result);
                        coordinate = jsonObjc.getJSONArray(idr);


                        latLng =new LatLng[coordinate.length()];

                        for (int j = 0; j < coordinate.length(); j++) {


                            JSONObject b = coordinate.getJSONObject(j);
                            String id = b.getString("id");
                            String lat = b.getString("lat");
                            String lng = b.getString("lng");

                            latLng[j]=new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
                        }

                        a[i]=new Area(idr,title,latLng);


                    }
                    catch (Exception e){


                        Log.i("eeeeee", "errrrrrrrror: "+e.toString());
                    }

                }


                countPolygonPoints();


            }
            catch (Exception e){


                Log.i("eeeeee", "errrrrrrrror: "+e.toString());
            }


        }







        @Override
        protected void onPreExecute() {



        }



        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {

            try {


                Log.i("urluuuuuuuuuuuuuuu", "doInBackground: "+params[0]);

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost(params[0]); // make sure the url is correct.
                //add your data

                Log.i("uuuuuu", "urluuuuuuuuuuuu "+params[0]);
                nameValuePairs = new ArrayList<NameValuePair>(15);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("test", "test".trim()));


//                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
//
//                nameValuePairs.add(new BasicNameValuePair("id_user",shpref.getString("id_user","-1").trim()));
//                nameValuePairs.add(new BasicNameValuePair("phone",shpref.getString("phone","-1").trim()));
//
//                Log.i("ddddddd", "ddddddddddd: "+shpref.getString("id_user","-1").trim()+"gggg"+shpref.getString("phone","-1").trim());


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
                //Execute HTTP Post Request
//                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return null;
        }
    }
}
