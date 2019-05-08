package app.activities.beer;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import app.activities.adapter.WebAdapter;
import app.model.beer.Beer;
import app.model.beer.BeerSA;

public class InformationBeer extends WebAdapter {

    @Override
    protected String htmlUrl() {
        return "File:///android_asset/webViews/beerInformation.html";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPageLoaded() {

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void,Void,Beer> at = new AsyncTask<Void, Void, Beer>() {

            private String error = null;

            @Override
            protected Beer doInBackground(Void... voids) {

                try {return (new BeerSA(getApplicationContext())).getBeerById(getIntent().getStringExtra("id"));}
                catch (Exception e) {error = e.getMessage();}
                return null;
            }

            @Override
            protected void onPostExecute(Beer result) {

                if(error != null){

                    throwToast(error);
                    finish();
                }
                else {

                    setH1(result.getName());
                    setH2(result.getStyle());
                    setH3(result.getManufacturer());
                    setStatus(result.getStatus());
                }
            }
        };

        at.execute();
    }
}
