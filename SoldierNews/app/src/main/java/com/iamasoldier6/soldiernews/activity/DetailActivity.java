package com.iamasoldier6.soldiernews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.iamasoldier6.soldiernews.bean.Constant;
import com.iamasoldier6.soldiernews.bean.NewsItem;

/**
 * Created by Iamasoldier6 on 2015/11/18.
 */
public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private NewsItem newsItem;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.iamasoldier6.soldiernews.R.layout.activity_details);
        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(com.iamasoldier6.soldiernews.R.id.webView);
        toolbar = (Toolbar) findViewById(com.iamasoldier6.soldiernews.R.id.toolbar);
        progressBar = (ProgressBar) findViewById(com.iamasoldier6.soldiernews.R.id.progressBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        newsItem = (NewsItem) bundle.getSerializable(Constant.NEWS_ITEM);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (progressBar.getVisibility() == View.INVISIBLE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl(newsItem.getUrl());
    }
}
