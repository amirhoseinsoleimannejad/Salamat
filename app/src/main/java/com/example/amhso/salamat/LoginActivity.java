package com.example.amhso.salamat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginActivity extends AppCompatActivity {

    EditText phone;
    String phoneS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        G.activity=this;

        Button buttonphone=(Button) findViewById(R.id.bphone);
        buttonphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                phone=(EditText) findViewById(R.id.phone);
                phoneS=phone.getText().toString();


                if(phoneS.length() != 11){
                    phone.setError("شماره موبایل معتبر نیست");

                }
                else if(true) {

                    HttpPostAsyncTask task = new HttpPostAsyncTask();
                    task.execute(G.urlserver+"signup_sick");



                }

                else{

                    showAlert("مشکل در ارتباط با سرور","ابتدا اینترنت را وصل کنید بعد وارد شوید با تشکر");
                }



            }
        });
    }



    public void showAlert(final String Title, final String Text){
        G.activity.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(G.activity);
                builder.setTitle(Title);
                builder.setMessage(Text)
                        .setCancelable(false)
                        .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }






    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;


        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        ProgressDialog dialog = null;





        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);


            try{
                String iduser_codevalid[]=result.split(":");


                if(iduser_codevalid[0].equals("1")){


                    Notification("تاییده ثبت نام",iduser_codevalid[2]);

                    Intent i = new Intent(G.activity,ValidsignupActivity.class);
                    i.putExtra("valid_number",iduser_codevalid[2]);
                    i.putExtra("id_user",iduser_codevalid[1]);
                    i.putExtra("phone",phoneS);
                    startActivity(i);
                    finish();

                }

                else{
                    phone.setError("کد نامعتبر می باشد");

                }
            }


            catch (Exception e){
                phone.setError("شماره نامعتبر می باشد");
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
                nameValuePairs.add(new BasicNameValuePair("phone",phoneS.trim()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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










    public void Notification(String Title,String Text){


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(G.activity)
                        .setSmallIcon(R.drawable.icon_message)
                        .setContentTitle(Title)   //this is the title of notification
                        .setColor(101)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setContentText(Text);


//        Intent intent = new Intent(G.activity, Exam1Activity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(G.activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
//        builder.setContentIntent(contentIntent);
        // Add as notification

        NotificationManager manager = (NotificationManager) G.activity.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


    }




}
