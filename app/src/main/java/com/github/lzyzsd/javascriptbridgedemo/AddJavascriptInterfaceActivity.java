package com.github.lzyzsd.javascriptbridgedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


public class AddJavascriptInterfaceActivity extends ActionBarActivity {

    WebView webView;
    Button callJsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_javascript_interface);

        callJsButton = (Button) findViewById(R.id.btn_call_js);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl("file:///android_asset/index.html");


        callJsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:myjavascriptfunc()");
            }
        });
    }

    public void javascriptCallFinished(String result) {
        Toast.makeText(this, "javascriptCallFinished: " + result, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_javascript_interface, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class WebAppInterface {
        AddJavascriptInterfaceActivity activity;

        /** Instantiate the interface and set the context */
        WebAppInterface(AddJavascriptInterfaceActivity c) {
            activity = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void setResult(String val){
            this.activity.javascriptCallFinished(val);
        }
    }
}
