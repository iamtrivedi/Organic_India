package com.organic.india.ui.activites.view_pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.organic.india.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class Viewpdf_page extends AppCompatActivity implements DownloadFile.Listener{

    RemotePDFViewPager remotePDFViewPager;
    PDFPagerAdapter adapter;
    @BindView(R.id.remote_pdf_root)LinearLayout root;
    @BindView(R.id.pb_progress)ProgressBar pb_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf_page);
        ButterKnife.bind(this);

        download_pdf();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    protected void download_pdf() {
        final Context ctx = this;
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(ctx, getIntent().getStringExtra("pdf_link"), listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    public void updateLayout() {
        root.removeAllViewsInLayout();
        root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
        pb_progress.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        pb_progress.setVisibility(View.GONE);
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

}