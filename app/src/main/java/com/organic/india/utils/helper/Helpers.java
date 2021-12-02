package com.organic.india.utils.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.organic.india.singletone.Organic_india;

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


    public static void open_google_map(Context context){


        try {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("geo:22.7749,70.4194"));
            intent.setComponent(new ComponentName(
                    "com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity"));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {

            try {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.google.android.apps.maps")));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps")));
            }

            e.printStackTrace();
        }


//        Uri gmmIntentUri = Uri.parse("geo:22.7749,70.4194");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        if (mapIntent.resolveActivity(Organic_india.getInstance().getPackageManager()) != null) {
//            context.startActivity(mapIntent);
//        }

//        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", "", "");
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        context.startActivity(intent);




//        String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + locationName + ")";
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
//        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(mapIntent);
//        }else{
//            Toast.makeText(context, "please install google map to your device", Toast.LENGTH_SHORT).show();
//        }
    }

    public static String getRealPathFromURI(Uri contentURI, Activity activity) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
