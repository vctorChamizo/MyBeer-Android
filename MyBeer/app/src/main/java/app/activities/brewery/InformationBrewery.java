package app.activities.brewery;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import app.activities.adapter.WebAdapter;
import app.model.brewery.Brewery;
import app.model.brewery.BrewerySA;

public class InformationBrewery extends WebAdapter {

    @Override
    protected String htmlUrl() {
        return "File:///android_asset/webViews/breweryInformation.html";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPageLoaded() {

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void,Void,Brewery> at = new AsyncTask<Void, Void, Brewery>() {

            private String error = null;

            @Override
            protected Brewery doInBackground(Void... voids) {

                try {return (new BrewerySA(getApplicationContext())).getBreweryById(getIntent().getStringExtra("id"));}
                catch (Exception e) {error = e.getMessage();}
                return null;
            }

            @Override
            protected void onPostExecute(Brewery result) {

                if(error != null){

                    throwToast(error);
                    finish();
                }
                else {

                    setH1(result.getName());
                    setH2(result.getAddress());
                    setStatus(result.getStatus());
                }
            }
        };

        at.execute();
    }
}
