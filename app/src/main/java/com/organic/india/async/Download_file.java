package com.organic.india.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Download_file extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;
    Download_result download_result;
    Activity context;

    public Download_file(Download_result download_result, Activity context) {
        this.download_result = download_result;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {

        int count;
        try{

            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            File path = context.getCacheDir();
            File file = new File(path, "downloaded_pdf.pdf");
            try{
                path.mkdirs();
            }catch (Exception e){
                Log.e("Error: ", "mkdir: "+e.getMessage());
            }

            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int)((total*100/lengthOfFile)));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            download_result.getPath(file.getAbsolutePath());
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            download_result.failed(e.getMessage());
        }
        return null;
    }



    /**
     * After completing background task
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        System.out.println("Downloaded");
        pDialog.dismiss();
    }

    public interface Download_result{
        void getPath(String path);
        void failed(String cause);
    }
}
