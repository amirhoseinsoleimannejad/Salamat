package com.example.amhso.salamat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class Exam1Activity extends AppCompatActivity {


    private String name,phone,age,national,weight,job,address,female_male,single_marriage;

    private EditText nameE,phoneE,addressE,ageE,nationalE,weightE,jobE;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Boolean edit_profile=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1);

        G.activity=this;



        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){


            edit_profile=bundle.getBoolean("edit_profile");
        }





        Button editprofilebutton=(Button) findViewById(R.id.send);
        editprofilebutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                nameE=(EditText)findViewById(R.id.name);
                name=nameE.getText().toString();


                phoneE=(EditText)findViewById(R.id.phone);
                phone=phoneE.getText().toString();


                addressE=(EditText)findViewById(R.id.address);
                address=addressE.getText().toString();



                ageE=(EditText)findViewById(R.id.age);
                age=ageE.getText().toString();


                nationalE=(EditText)findViewById(R.id.national);
                national=nationalE.getText().toString();




                weightE=(EditText) findViewById(R.id.weight);
                weight=weightE.getText().toString();





                jobE=(EditText) findViewById(R.id.job);
                job=jobE.getText().toString();



                radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                radioSexButton = (RadioButton) findViewById(selectedId);
                female_male=radioSexButton.getText().toString();




                radioSexGroup = (RadioGroup) findViewById(R.id.marriageS);
                selectedId = radioSexGroup.getCheckedRadioButtonId();

                radioSexButton = (RadioButton) findViewById(selectedId);
                single_marriage=radioSexButton.getText().toString();




                HttpPostAsyncTask task = new HttpPostAsyncTask();
                task.execute(G.urlserver+"insert_info");

//
            }


        });
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


                if(edit_profile){
                    Intent i = new Intent(G.activity,MainActivity.class);
                    startActivity(i);
                    G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
                    finish();
                }
                else{
                    Intent i = new Intent(G.activity,Exam2Activity.class);
                    startActivity(i);
                    G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
//                finish();
                }


            }


            else{


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("اطلاعات وارد شده اشتباه می باشد");
                // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("آیا دوباره امتحان می کنید؟");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       HttpPostAsyncTask task = new HttpPostAsyncTask();
                       task.execute(G.urlserver+"insert_info");
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



//        public  boolean isNumeric(String str)
//        {
//            try
//            {
//                double d = Double.parseDouble(str);
//            }
//            catch(NumberFormatException nfe)
//            {
//                return false;
//            }
//            return true;
//        }




        @Override
        protected void onPreExecute() {

            try {
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
                nameValuePairs = new ArrayList<NameValuePair>(2);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("name", name.trim()));
                nameValuePairs.add(new BasicNameValuePair("age", age.trim()));
                nameValuePairs.add(new BasicNameValuePair("national",national.trim()));
                nameValuePairs.add(new BasicNameValuePair("weight", weight.trim()));
                nameValuePairs.add(new BasicNameValuePair("job", job.trim()));
                nameValuePairs.add(new BasicNameValuePair("address", address.trim()));
                nameValuePairs.add(new BasicNameValuePair("female_male", female_male.trim()));
                nameValuePairs.add(new BasicNameValuePair("single_marriage", single_marriage.trim()));
                nameValuePairs.add(new BasicNameValuePair("phone2", phone.trim()));


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
