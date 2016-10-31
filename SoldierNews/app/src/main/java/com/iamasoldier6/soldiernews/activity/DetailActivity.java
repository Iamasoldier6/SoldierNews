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

    private WebView mWebView;
    private NewsItem mNewsItem;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.iamasoldier6.soldiernews.R.layout.activity_details);
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(com.iamasoldier6.soldiernews.R.id.webview);
        mToolbar = (Toolbar) findViewById(com.iamasoldier6.soldiernews.R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(com.iamasoldier6.soldiernews.R.id.progressbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        mNewsItem = (NewsItem) bundle.getSerializable(Constant.NEWS_ITEM);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (mProgressBar.getVisibility() == View.INVISIBLE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.loadUrl(mNewsItem.getUrl());
    }
}
