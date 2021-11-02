package com.organic.india.utils.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.Locale;

public class Helpers {

    public static void open_google_map(Double latitude, Double longitude,String locationName, Context context){


        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);

//        String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + locationName + ")";
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
//        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(mapIntent);
//        }else{
//            Toast.makeText(context, "please install google map to your device", Toast.LENGTH_SHORT).show();
//        }
    }
}
