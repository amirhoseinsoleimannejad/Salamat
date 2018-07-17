package com.example.amhso.salamat.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.amhso.salamat.MessageActivity;
import com.example.amhso.salamat.R;
import com.example.amhso.salamat.otherclass.G;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MessageService extends Service {


    public static Boolean start=true;
    public static WebSocketClient mWebSocketClient;
    public String id_user="bbbb";
//    public Boolean first=true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        try{
            SharedPreferences shpref = getApplicationContext().getSharedPreferences("salamat", Context.MODE_PRIVATE);
            id_user= shpref.getString("id_user", "booos");



        }
        catch (Exception e){

        }



        connectWebSocket();





        new Thread( new Runnable() {
            @Override
            public void run() {


                while (start){

                    try {






                        Thread.sleep(1000);


                        mWebSocketClient.send(id_user);






                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        }).start();



        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        mWebSocketClient.close();
//        start=false;

    }















    private void connectWebSocket() {
        URI uri;



        try {
            uri = new URI(G.Service);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Open" );


//                mWebSocketClient.send("ping");

            }

            @Override
            public void onMessage(String s) {
                final String message = s;


//                addNotification_message();

                if (s.equals("1")){
                    addNotification_message();
                }




            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };



        try {
            mWebSocketClient.connect();

        }
        catch (Exception a){

        }


    }



    private void addNotification_message() {


        android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.icon_message)
                        .setContentTitle("پیام ")
                        .setContentText("پیامی از طرف درمانگر برای شما ارسال شده");


        Intent notificationIntent = new Intent(this, MessageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);



        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }












    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;







        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);


            if(result.equals("1")){

                addNotification_message();
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
                nameValuePairs = new ArrayList<NameValuePair>(5);


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


