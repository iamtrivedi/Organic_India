package com.organic.india.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.organic.india.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Functions_common {

    ProgressDialog progressDialog;
    Context context;
    Permission_handle permission_handle;

    public Functions_common(Context context) {
        this.context = context;
        progressDialog=new ProgressDialog(context);
    }

    public Functions_common(Context context, Permission_handle permission_handle) {
        this.context = context;
        this.permission_handle = permission_handle;
        progressDialog=new ProgressDialog(context);
    }

    public void toast(String msg){
        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
    }

    public void fail_request(){
        Toast.makeText(context, "please try again", Toast.LENGTH_SHORT).show();
    }

    public void show_loader(String message){
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }


    public void dismiss_loader(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }


    public  void handle_permission(Activity activity, List<String> permissions){
        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            permission_handle.granted();
                        }else{
                            permission_handle.denied();
                           // Toast.makeText(activity, "please allow the require permission", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public interface Permission_handle{
        void granted();
        void denied();
    }



    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<Integer> slides(){
        List<Integer> slides=new ArrayList<>();
        slides.add(R.drawable.slide1);
        slides.add(R.drawable.slide2);
        slides.add(R.drawable.slide3);
        slides.add(R.drawable.slide4);
        slides.add(R.drawable.slide5);
        slides.add(R.drawable.slide6);
        slides.add(R.drawable.slide7);
        slides.add(R.drawable.slide8);
        slides.add(R.drawable.slide9);
        slides.add(R.drawable.slide10);
        slides.add(R.drawable.slide11);
        slides.add(R.drawable.slide12);
        return slides;
    }
}
