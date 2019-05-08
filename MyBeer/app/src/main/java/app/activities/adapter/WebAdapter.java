package app.activities.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.R;
import java.util.Objects;
import app.activities.main.MyBeerMain;
import app.model.status.Status;

public abstract class WebAdapter extends MyBeerMain {

    protected LinearLayout webAdpaterProgress;
    private TextView webAdpaterTextProgress;
    protected WebView webAdapterWebView;

    protected abstract String htmlUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        setAppBar();
        inicializateView();

        setProgressBar(getString(R.string.loading), this.webAdapterWebView, this.webAdpaterProgress, this.webAdpaterTextProgress);
        webViewConfiguration();
        removeProgressBar(this.webAdpaterProgress, this.webAdapterWebView);
    }

    @SuppressLint({"ResourceType", "SetJavaScriptEnabled"})
    private void webViewConfiguration() {

        this.webAdapterWebView.getSettings().setJavaScriptEnabled(true);
        this.webAdapterWebView.loadUrl(htmlUrl());

        WebViewClient wvc = new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view,url);

                webAdapterWebView.evaluateJavascript("document.body.style.color = '#" + getString(R.color.primary_light).substring(3) + "';",null);
                webAdapterWebView.evaluateJavascript("document.body.style.backgroundColor = '#" + getString(R.color.primary).substring(3) + "';",null);

                onPageLoaded();
            }
        };

        this.webAdapterWebView.setWebViewClient(wvc);
    }

    @Override
    protected void setAppBar() {

        Toolbar addBeerToolBar = findViewById(R.id.web_layout_toolbar);
        addBeerToolBar.setTitle(R.string.informationBeer);
        setSupportActionBar(addBeerToolBar);
        ActionBar addBeerActionBar = getSupportActionBar();
        Objects.requireNonNull(addBeerActionBar).setHomeAsUpIndicator(R.drawable.ic_arrow_back_red);
        addBeerActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void inicializateView(){

        this.webAdpaterProgress = findViewById(R.id.web_layout_progress);
        this.webAdpaterTextProgress = findViewById(R.id.web_layout_progress_text);
        this.webAdapterWebView = findViewById(R.id.web_layout_webview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { finish(); }

    /**
     * Set the main tittle into web page
     *
     * @param h1 message to main tittle
     */
    protected void setH1(String h1){ this.webAdapterWebView.evaluateJavascript("document.getElementById(\"h1Text\").innerText = '"+h1+"';",null); }

    /**
     * Set the secondary tittle into web page
     *
     * @param h2 message to secondary tittle
     */
    protected void setH2(String h2){ this.webAdapterWebView.evaluateJavascript("document.getElementById(\"h2Text\").innerText = '" + h2 + "';",null); }

    /**
     * Set the additional tittle into web page
     *
     * @param h3 message to additional tittle
     */
    protected void setH3(String h3){ this.webAdapterWebView.evaluateJavascript("document.getElementById(\"h3Text\").innerText = '" + h3 + "';",null); }

    /**
     * Set the icon with the status of beer or brewery
     *
     * @param status the object of beer or brewery
     */
    protected void setStatus(Status status){

        switch (status){

            case LIKED: this.webAdapterWebView.evaluateJavascript("document.getElementById('likeIm').src='res/visited.png'",null);break;
            case DISLIKED: this.webAdapterWebView.evaluateJavascript("document.getElementById('likeIm').src='res/disliked.png'",null);break;
            default: this.webAdapterWebView.evaluateJavascript("document.getElementById('likeIm').src='res/no_visited.png'",null);break;
        }
    }

    /**
     * Get the necessary data in data phone and load the web page
     */
    protected abstract void onPageLoaded();
}
