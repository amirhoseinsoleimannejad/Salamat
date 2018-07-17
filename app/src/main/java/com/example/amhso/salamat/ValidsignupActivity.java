package com.example.amhso.salamat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amhso.salamat.otherclass.G;
import com.example.amhso.salamat.service.MessageService;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;






public class ValidsignupActivity extends AppCompatActivity {



    String valid_number;
    EditText edvalid;

    String id_user;
    String valid_original;

    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validsignup);


        G.activity=this;


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            id_user=bundle.getString("id_user");
            valid_original=bundle.getString("valid_number");
            phone=bundle.getString("phone");

        }
        else{
            finish();
        }


        Button validbutton=(Button)findViewById(R.id.verify);

        validbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                edvalid=(EditText)findViewById(R.id.validnumber);

                valid_number=edvalid.getText().toString();

                if(valid_number.length() == 4 && valid_number.equals(valid_original)){
                    HttpPostAsyncTask task = new HttpPostAsyncTask();
                    task.execute(G.urlserver+"valid_sign");
                }
                else{
                    edvalid.setError("کد نا معتبر است");
                }


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



            if(result.equals("1")){

                SharedPreferences shpref_login1 = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
                SharedPreferences.Editor sh_edit = shpref_login1.edit();
                sh_edit.putString("id_user", id_user);
                sh_edit.putString("phone",phone);
                sh_edit.apply();


                Intent intent = new Intent(G.activity,MessageService.class);
                G.activity.startService(intent);
//                startService(new Intent(getBaseContext(), MessageService.class));


                Intent i = new Intent(G.activity,Exam1Activity.class);
                startActivity(i);
                finish();

            }



            else{
                edvalid.setError("کد نامعتبر می باشد");
            }


        }







        @Override
        protected void onPreExecute() {



        }



        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {

            try {


                httpclient=new DefaultHttpClient();
                httppost= new HttpPost(params[0]); // make sure the url is correct.
                //add your data
                Log.i("uuuuuu", "urluuuuuuuuuuuu "+params[0]);
                nameValuePairs = new ArrayList<NameValuePair>(2);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("code_sms",valid_number.trim()));
                nameValuePairs.add(new BasicNameValuePair("id_user",id_user.trim()));
                nameValuePairs.add(new BasicNameValuePair("phone",phone.trim()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //Execute HTTP Post Request
//                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
//                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return null;
        }
    }
}
