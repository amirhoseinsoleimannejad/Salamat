package com.example.amhso.salamat.otherclass;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by amhso on 17/04/2018.
 */

public class Area {

    String title;
    String id;
    LatLng latlng [];

    public Area(String id, String title, LatLng latLng []){
        this.title=title;
        this.id=id;
        this.latlng=latLng;
    }



    public String getTitle(){
        return this.title;
    }


    public String getId(){
        return this.id;
    }


    public LatLng [] getLatlng(){
        return this.latlng;
    }

}
