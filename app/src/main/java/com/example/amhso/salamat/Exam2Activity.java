package com.example.amhso.salamat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.amhso.salamat.otherclass.G;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Exam2Activity extends AppCompatActivity {


    private String q1="-1",
            q2="-1",
            q3="-1",
            q4="-1",
            q5="-1",
            q6="-1",
            q7="-1",
            q8="-1",
            q9="-1",
            q11="-1",
            q12="-1",
            q13="-1";


    private RadioGroup radioQGroup;
    private RadioButton radioQButton;


    private Boolean full=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam2);



        Button editprofilebutton=(Button) findViewById(R.id.send);
        editprofilebutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                try {



                    radioQGroup = (RadioGroup) findViewById(R.id.q1);
                    int selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q1 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q2);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q2 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q3);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q3 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q4);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q4 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q5);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q5 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q6);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q6 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q7);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q7 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q8);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q8 = radioQButton.getText().toString();


                    radioQGroup = (RadioGroup) findViewById(R.id.q9);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q9 = radioQButton.getText().toString();



                    radioQGroup = (RadioGroup) findViewById(R.id.q11);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q11 = radioQButton.getText().toString();




                    radioQGroup = (RadioGroup) findViewById(R.id.q12);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q12 = radioQButton.getText().toString();





                    radioQGroup = (RadioGroup) findViewById(R.id.q13);
                    selectedId = radioQGroup.getCheckedRadioButtonId();

                    radioQButton = (RadioButton) findViewById(selectedId);
                    q13 = radioQButton.getText().toString();

//                Log.i("dddddddddd", "onCreate: "+q1+"----"+q2);

                    full=true;

                }
                catch (Exception e){
                    Log.i("eeeeee", "eeeeeeeeee"+e.toString());
                    full=false;

                }


                LocationManager locationManager = (LocationManager) G.activity.getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                }
                else{

                    if(full){
                        HttpPostAsyncTask task = new HttpPostAsyncTask();
                        task.execute(G.urlserver + "qustion_one");
                    }
                    else{
                        Toast.makeText(G.activity, "به تمام سوالات با دقت جواب دهید با تشکر", Toast.LENGTH_LONG).show();

                    }
                }

            }


        });


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








    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;

        public ProgressDialog progressDialog;

        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        ProgressDialog dialog = null;





        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);
//

            try{
                progressDialog.dismiss();

            }
            catch (Exception e){

            }

            if(result.equals("1")){


                Intent i = new Intent(G.activity,Exam3Activity.class);
                startActivity(i);
                G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
//                finish();

            }


            else{


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("در وارد کردن اطلاعات خطایی رخ داده است ");
                // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("آیا دوباره امتحان می کنید؟");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        HttpPostAsyncTask task = new HttpPostAsyncTask();
                        task.execute(G.urlserver+"question_one");
                    }
                });

                alertDialogBuilder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }

        }







        @Override
        protected void onPreExecute() {

            try{
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("در حال ذخیره اطلاعات"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
            }
            catch (Exception e){

            }



            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(20000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }).start();

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
                nameValuePairs.add(new BasicNameValuePair("q1", q1.trim()));
                nameValuePairs.add(new BasicNameValuePair("q2", q2.trim()));
                nameValuePairs.add(new BasicNameValuePair("q3", q3.trim()));
                nameValuePairs.add(new BasicNameValuePair("q4", q4.trim()));
                nameValuePairs.add(new BasicNameValuePair("q5", q5.trim()));
                nameValuePairs.add(new BasicNameValuePair("q6", q6.trim()));
                nameValuePairs.add(new BasicNameValuePair("q7", q7.trim()));
                nameValuePairs.add(new BasicNameValuePair("q8", q8.trim()));
                nameValuePairs.add(new BasicNameValuePair("q9", q9.trim()));
                nameValuePairs.add(new BasicNameValuePair("q11", q11.trim()));
                nameValuePairs.add(new BasicNameValuePair("q12", q12.trim()));
                nameValuePairs.add(new BasicNameValuePair("q13", q13.trim()));



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



}
