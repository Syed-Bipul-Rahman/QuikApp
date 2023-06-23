package com.roomdb.dynmicwebview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //declare variables
    WebView fullpage;
    RelativeLayout relativeLayout;
    Button refresh;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//initializing variables
        fullpage = (WebView) findViewById(R.id.fullpage);
        relativeLayout = findViewById(R.id.connection);
        refresh = findViewById(R.id.retrybtn);
        progressBar = findViewById(R.id.progressbar);


//setting action to work when refresh button is clicked
        refresh.setOnClickListener(v -> {
            isNetworkConnected();


        });
        fullpage.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                isNetworkConnected();
                super.onReceivedError(view, request, error);
            }
        });


        fullpage.loadUrl("http://sohoz-learning.online");
        WebSettings webSettings = fullpage.getSettings();
        webSettings.setJavaScriptEnabled(true);

        isNetworkConnected();
    }

    //creating a method to check if the device is connected to the internet
    public void isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //check both mobile data and wifi
        NetworkInfo mobileData = connectivityManager.getActiveNetworkInfo();

        if (mobileData != null && mobileData.isConnected()) {
//            fullpage.loadUrl("http://sohoz-learning.online/");

            fullpage.setVisibility(View.VISIBLE);

            relativeLayout.setVisibility(RelativeLayout.GONE);
            progressBar.setVisibility(ProgressBar.GONE);
            fullpage.reload();
        } else {
            relativeLayout.setVisibility(RelativeLayout.VISIBLE);
            progressBar.setVisibility(ProgressBar.GONE);


            fullpage.setVisibility(View.GONE);
            Toast.makeText(this, "Please turn on your wifi or mobile data", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {
        if (fullpage.canGoBack()) {
            fullpage.goBack();

        } else {
            super.onBackPressed();
        }
    }

}