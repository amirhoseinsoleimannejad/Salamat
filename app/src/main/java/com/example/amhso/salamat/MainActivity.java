package com.example.amhso.salamat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amhso.salamat.otherclass.G;
import com.example.amhso.salamat.service.MessageService;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

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

public class MainActivity extends AppCompatActivity {




    public String imgs[];

    public String url;
    private DrawerLayout drawer;
    private NavigationView navigat;


    private String address="";
    private boolean danger;
    private TextView tv;
    private TextView tv_alarm;

    private String id_range="-1";
    private String tell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        G.activity=this;





        //ser drawer
        this.drawer = (DrawerLayout) findViewById(R.id.DL);
        //set click item drawer
        this.navigat = (NavigationView) findViewById(R.id.NA);






        this.navigat.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return itemClickNavigat(item);
            }
        });


        this.navigat.setItemIconTintList(null);



        ImageView send=(ImageView) findViewById(R.id.icon_menu);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                drawer.openDrawer(Gravity.RIGHT);

            }
        });







        tv=(TextView) findViewById(R.id.text_message);




        tv_alarm=(TextView) findViewById(R.id.free_alarm);


        Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/IRANSansWeb(FaNum)_UltraLight.ttf");
        tv.setTypeface(type2);


        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/IRANSansWeb(FaNum)_Bold.ttf");
        tv_alarm.setTypeface(type3);


        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver + "fetch_baner");

    }











    private boolean itemClickNavigat(MenuItem item){
        switch (item.getItemId()){

            case R.id.message :

                this.drawer.closeDrawer(Gravity.RIGHT);
                Intent i = new Intent(G.activity,MessageActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);

                return true;






            case R.id.edit :

                this.drawer.closeDrawer(Gravity.RIGHT);


                i = new Intent(G.activity,Exam1Activity.class);
                i.putExtra("edit_profile",true);
                startActivity(i);
                overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);

                return true;








            case R.id.exit :
                this.drawer.closeDrawer(Gravity.RIGHT);

                MessageService.start=false;

                try {
                    stopService(new Intent(getBaseContext(), MessageService.class));
                }
                catch (Exception e){

                }

               SharedPreferences sharedpreferences = getSharedPreferences("salamat", Context.MODE_PRIVATE);
                sharedpreferences.edit().clear().apply();
                i = new Intent(G.activity,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
                finish();

                return true;




            case R.id.lab:

                this.drawer.closeDrawer(Gravity.RIGHT);
                i = new Intent(G.activity,ReportlabActivity.class);
                i.putExtra("danger",danger);
                startActivity(i);
                overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);

                return true;

        }
        return false;
    }









    private class TestLoopAdapter extends LoopPagerAdapter {



        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!url.startsWith("https://") && !url.startsWith("http://")){
                        url = "http://" + url;
                    }

                    Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    G.activity.startActivity(openUrlIntent);

                }
            });



            Picasso.with(G.activity)
                                .load(G.urlimage+imgs[position])
                                .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                                .into(view);

            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }





    ////
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




                JSONArray baner;
                JSONObject jsonObj = new JSONObject(result);
                baner = jsonObj.getJSONArray("baner");


                imgs=new String[baner.length()];
                for (int i = 0; i < baner.length(); i++) {

                    JSONObject c = baner.getJSONObject(i);
                    String img = c.getString("img");
                    String url2=c.getString("url");
                    imgs[i]=img;
                    url=url2;

                }


                com.jude.rollviewpager.RollPagerView mRollViewPager=(com.jude.rollviewpager.RollPagerView) G.activity.findViewById(R.id.baner);
                mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));




                JSONArray doctor;
                JSONObject jsonObjd = new JSONObject(result);
                doctor = jsonObjd.getJSONArray("doctor");



                for (int i = 0; i < doctor.length(); i++) {

                    JSONObject b = doctor.getJSONObject(i);
                    address = b.getString("adress");
                    tell = b.getString("tell");

                }


                JSONArray sick;
                JSONObject jsonObjs = new JSONObject(result);
                sick = jsonObjs.getJSONArray("sick");


                for (int i = 0; i < sick.length(); i++) {

                    JSONObject a = sick.getJSONObject(i);
                    String danger_sick = a.getString("danger_sick");
                    id_range= a.getString("id_range");

                    if(danger_sick.equals("1")){
                       danger=true;
                    }
                    else if(danger_sick.equals("0")){
                        danger=false;
                    }

                }




                if(danger){
                    if(id_range.equals("-1")){
                        tv.setText("برای انجام آزمایش و معاینه بیشتر حضورا تشریف بیاورید." + "\n متاسفانه درمانگری در منطقه شما وجود ندارد برای مشخص شدن درمانگر خود به این شماره تماس بگیرید 09369896983 با تشکر");
                    }
                    else{
                        tv.setText("برای انجام آزمایش و معاینه بیشتر حضورا تشریف بیاورید." + "\n آدرس آزمایشگاه:"+ address +"\n تلفن:"+tell);
                    }
                    tv_alarm.setText("(ویزیت وآزمایش خلط رایگان می باشد)");
                }


                else {
                    tv_alarm.setText("(ویزیت   پزشک   رایگان   است)");

                    tv.setText("شما باتوجه به علایم  نیازمند بررسی بیشتر هستید ولازم است توسط پزشک نزدیکترین مرکزبهداشتی  ودرمانی  معاینه شوید.");
                }





            }
            catch (Exception e){


                Log.i("eeeeee", "errrrrrrrror: "+e.toString());
            }


        }





        @Override
        protected void onPreExecute() {


            try {
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("در حال جستجو ..."); // Setting Title
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
                nameValuePairs = new ArrayList<NameValuePair>(5);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("name", "name".trim()));

                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);

                nameValuePairs.add(new BasicNameValuePair("id_user",shpref.getString("id_user","-1").trim()));
                nameValuePairs.add(new BasicNameValuePair("phone",shpref.getString("phone","-1").trim()));
//
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
