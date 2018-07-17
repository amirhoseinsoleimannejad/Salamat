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
import android.widget.ListView;

import com.example.amhso.salamat.adapter.MessageAdapter;
import com.example.amhso.salamat.adapter.ReportAdapter;
import com.example.amhso.salamat.otherclass.G;
import com.example.amhso.salamat.service.MessageService;

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

public class ReportlabActivity extends AppCompatActivity {



    private ListView listView;


    private List<String> listreport;
    private ReportAdapter reportAdapter;

    private Boolean danger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlab);



        G.activity=this;







        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){


            danger=bundle.getBoolean("danger");
        }
        else{
            finish();
        }

        listView=(ListView) findViewById(R.id.list_report);
        listreport = new ArrayList<>();



        Button no = (Button)findViewById(R.id.no_ccontinue);
        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(G.activity);
                builder.setTitle("از عملیات خود اطمینان دارید؟");
                builder.setMessage("در صورتی که از ادامه درمان خود پشیمان شوید اطلاعات شما پاک خواهد شد.")
                        .setCancelable(false)
                        .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HttpPostAsyncTask task = new HttpPostAsyncTask();
                                task.execute(G.urlserver + "no_connect");
                            }
                        })
                        .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });




        Button con = (Button)findViewById(R.id.ccontinue);
        con.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(G.activity);
                builder.setTitle("از اعتماد شما سپاس گذارم.");
                builder.setMessage("برای ادامه مراحل درمان حتما باید دستورات درمانگر را گوش کنید.")
                        .setCancelable(false)
                        .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });



        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver + "fetch_request");
    }








    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        public ProgressDialog progressDialog;






        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);

            try {
                progressDialog.dismiss();

            }
            catch (Exception e){

            }



            try {


                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("request");



                String html="";
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);


                    String date = c.getString("date");

                    String exam1 = c.getString("exam1");
                    String exam2 = c.getString("exam2");
                    String need_tiboro = c.getString("need_tiboro");
                    String child = c.getString("child");
                    String infectious = c.getString("infectious");
                    String result_lab = c.getString("result");
                    String pcr = c.getString("pcr");


                    html +="معاینات شما در تاریخ:";
                    html +=date;
                    html +="\n";

                    if(!exam1.equals("-1")){
                        html +="معاینه اول: ";
                        html +=exam1;
                        html +="\n";

                    }

                    if(!exam2.equals("-1")) {
                        html += "معاینه دوم: ";
                        html += exam2;
                        html += "\n";
                    }

                    if(!need_tiboro.equals("-1")) {

                        html += "نیاز به تبویرانتی: ";
                        html += need_tiboro;
                        html += "\n";
                    }

                    if(!child.equals("-1")) {

                        html += "متخصص اطفال: ";
                        html += child;
                        html += "\n";
                    }

                    if(!infectious.equals("-1")) {

                        html += "متخصص عفونی: ";
                        html += infectious;
                        html += "\n";
                    }

                    html +="\n";
                    html +="\n";



                    html +="نتیجه آزمایش شما در تاریخ: ";
                    html +=date;
                    html +="\n";



                    if(danger){
                        String phlegm_one = c.getString("phlegm_one");
                        String phlegm_two = c.getString("phlegm_two");
                        String pic_riyeh = c.getString("pic_riyeh");
                        String shire_medeh = c.getString("shire_medeh");
                        String sono_riyeh = c.getString("sono_riyeh");
                        String bronkoskopy = c.getString("bronkoskopy");



                        if(!phlegm_one.equals("-1")) {

                            html += "خلط سینه اول: ";
                            html += phlegm_one;
                            html += "\n";
                        }


                        if(!phlegm_two.equals("-1")) {

                            html += "خلط سینه دوم: ";
                            html += phlegm_two;
                            html += "\n";
                        }

                        if(!pic_riyeh.equals("-1")) {

                            html += "عکس ریه: ";
                            html += pic_riyeh;
                            html += "\n";
                        }


                        if(!shire_medeh.equals("-1")) {

                            html += "شیره معده: ";
                            html += shire_medeh;
                            html += "\n";
                        }

                        if(!sono_riyeh.equals("-1")) {

                            html += "سنوگرافی ریه: ";
                            html += sono_riyeh;
                            html += "\n";
                        }

                        if(!bronkoskopy.equals("-1")) {

                            html += "برن کس کپی: ";
                            html += bronkoskopy;
                            html += "\n";
                        }


                    }
                    else{
                        String pdd = c.getString("pdd");
                        String sonography_shekam = c.getString("sonography_shekam");
                        String pic_chest = c.getString("pic_chest");
                        String sitiscan_riyeh = c.getString("sitiscan_riyeh");

                        if(!pdd.equals("-1")) {

                            html += "پی دی دی:";
                            html += pdd;
                            html += "\n";
                        }

                        if(!sonography_shekam.equals("-1")) {

                            html += "سنو گرافی شکم: ";
                            html += sonography_shekam;
                            html += "\n";
                        }

                        if(!pic_chest.equals("-1")) {

                            html += "عکس شکم: ";
                            html += pic_chest;
                            html += "\n";
                        }

                        if(!sitiscan_riyeh.equals("-1")) {

                            html += "سی تی اسکن: ";
                            html += sitiscan_riyeh;
                            html += "\n";
                        }


                    }

                    if(!pcr.equals("-1")) {

                        html += "جین اکسپرت:";
                        html += pcr;
                        html += "\n";
                    }

                    if(!result_lab.equals("-1")) {

                        html += "نتیجه آزمایش: ";
                        html += result_lab;
                        html += "\n";
                    }



                    listreport.add(html);

                }

                reportAdapter = new ReportAdapter(G.activity,listreport);
                listView.setAdapter(reportAdapter);

            }
            catch (Exception e){

                if (result.equals("1")){
                    MessageService.start=false;

                    try {
                        stopService(new Intent(getBaseContext(), MessageService.class));
                    }
                    catch (Exception er){

                    }

                    SharedPreferences sharedpreferences = getSharedPreferences("salamat", Context.MODE_PRIVATE);
                    sharedpreferences.edit().clear().apply();
                    Intent i = new Intent(G.activity,LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
                    finish();
                }
                else{

                }
            }

        }






        @Override
        protected void onPreExecute() {

            try {
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("در حال گرفتن پیام ها شما"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog


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
            catch (Exception e){

            }
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




                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
//
                nameValuePairs.add(new BasicNameValuePair("id_user",shpref.getString("id_user","-1").trim()));


                Log.i("dddddddddd", "doInBackground: "+shpref.getString("id_user","-1").trim());
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return "0";
        }
    }
}
