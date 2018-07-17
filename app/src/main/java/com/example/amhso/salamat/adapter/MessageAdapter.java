package com.example.amhso.salamat.adapter;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.amhso.salamat.R;
import com.example.amhso.salamat.otherclass.G;

import java.util.List;


public class MessageAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String>  itemname;

    public MessageAdapter(Activity context, List<String> itemname) {
        super(context, R.layout.listmessage, itemname);
        this.context=context;
        this.itemname=itemname;
    }




    public View getView(int position,View view,ViewGroup parent) {





        View listItem = view;
        MyWrapper wrapper = null;


        try {

            if (listItem == null) {

                listItem = LayoutInflater.from(context).inflate(R.layout.listmessage, parent, false);
                wrapper = new MyWrapper(listItem);
                listItem.setTag(wrapper);

            } else {
                wrapper = (MyWrapper) listItem.getTag();
            }

            String getnameseller = itemname.get(position);

            wrapper.getName().setText(getnameseller);

        }
        catch (Exception e){
            Log.i("eeeeee", "eeeeeeeeeeeeeeee"+e.toString());
        }



        return listItem;


    };







    class MyWrapper
    {
        private View base;
        private TextView message;


        public MyWrapper(View base)
        {
            this.base = base;
        }



        public TextView getName(){
            if(message == null){
                message = (TextView) base.findViewById(R.id.name);
                Typeface type2 = Typeface.createFromAsset(G.activity.getAssets(),"fonts/IRANSansWeb(FaNum)_UltraLight.ttf");
                message.setTypeface(type2);
            }
            return message;
        }



    }
}
