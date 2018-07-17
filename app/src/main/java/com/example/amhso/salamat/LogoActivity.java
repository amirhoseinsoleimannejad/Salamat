package com.example.amhso.salamat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amhso.salamat.otherclass.G;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        G.activity=this;



        SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);




        if(shpref.getBoolean("select_area",false)){
//
            Intent i=new Intent(LogoActivity.this  , MainActivity.class);
            startActivity(i);
            finish();
        }





        final Animation a = AnimationUtils.loadAnimation(this, R.anim.animation_scale);
        a.reset();

        android.support.constraint.ConstraintLayout layout = (android.support.constraint.ConstraintLayout) findViewById(R.id.con_bg);
        layout.startAnimation(a);




        final TextView rText = (TextView) findViewById(R.id.des_logo);


        final Animation b = AnimationUtils.loadAnimation(this, R.anim.animation_alpha);
        b.reset();


       final Button primece =(Button) findViewById(R.id.primese);
        Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/IRANSansWeb(FaNum)_UltraLight.ttf");
        primece.setTypeface(type2);

        final Animation c = AnimationUtils.loadAnimation(this, R.anim.animation_translate);
        c.reset();


        b.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {


                primece.setVisibility(View.VISIBLE);
                primece.startAnimation(c);
            }
        });


        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/IRANSansWeb(FaNum).ttf");
        rText.setTypeface(type);


        rText.startAnimation(b);





//        final Animation c = AnimationUtils.loadAnimation(this, R.anim.animation_translate);
//        c.reset();
//        final ImageView imgview = (ImageView) findViewById(R.id.imagelogo);
//        imgview.startAnimation(c);









        primece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogoActivity.this,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
                finish();
            }
        });





    }
}
