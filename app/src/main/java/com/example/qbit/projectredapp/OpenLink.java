package com.example.qbit.projectredapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class OpenLink extends ActionBarActivity{
    SaveSharedPreference pm = new SaveSharedPreference();
    private WebView open_link_view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_link_layout);
        open_link_view= (WebView) findViewById(R.id.open_link_layout);
        open_link_view.getSettings().setJavaScriptEnabled(true);
        final Bundle extras= getIntent().getExtras();
        String url = extras.getString("url");
        open_link_view.getSettings().setLoadWithOverviewMode(true);
        open_link_view.getSettings().setUseWideViewPort(true);
        open_link_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        open_link_view.loadUrl(url);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontpage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()){
            case R.id.submit:
                SubmitThread submitThread = new SubmitThread();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.open_link_layout,submitThread, "submitThread");
                transaction.commit();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.logout:
                Intent i = new Intent(getApplicationContext(), LoginAndRegister.class);
                pm.logout(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Logging you out!", Toast.LENGTH_LONG).show();
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
