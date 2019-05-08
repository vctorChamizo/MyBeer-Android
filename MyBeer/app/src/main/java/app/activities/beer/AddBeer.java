package app.activities.beer;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.R;
import java.util.Objects;
import app.activities.main.MyBeerMain;
import app.model.beer.Beer;
import app.model.beer.BeerSA;
import app.model.status.Status;

@SuppressLint("Registered")
public class AddBeer extends MyBeerMain implements View.OnClickListener {

    private LinearLayout addBeerProgress;
    private TextView addBeerTextProgress;
    private LinearLayout addBeerRelative;

    private EditText addBeerName, addBeerManufacturer, addBeerStyle;
    private RadioButton addBeerLiked, addBeerDisliked, addBeerNotTested;

    private Button addBeerButton;

    private ActionBar addBeerActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);

        setAppBar();
        inicializateView();

        this.addBeerButton.setOnClickListener(this);
    }

    @Override
    protected void setAppBar() {

        Toolbar addBeerToolBar = findViewById(R.id.add_beer_toolbar);
        setSupportActionBar(addBeerToolBar);
        this.addBeerActionBar = getSupportActionBar();
        Objects.requireNonNull(this.addBeerActionBar).setHomeAsUpIndicator(R.drawable.ic_arrow_back_red);
        this.addBeerActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void inicializateView(){

        this.addBeerProgress = findViewById(R.id.add_beer_progress);
        this.addBeerTextProgress = findViewById(R.id.add_beer_progress_text);
        this.addBeerRelative = findViewById(R.id.add_beer_relative);

        this.addBeerName = findViewById(R.id.add_beer_name);
        this.addBeerManufacturer = findViewById(R.id.add_beer_manufacturer);
        this.addBeerStyle = findViewById(R.id.add_beer_style);

        this.addBeerLiked = findViewById(R.id.add_beer_rb_liked);
        this.addBeerDisliked = findViewById(R.id.add_beer_rb_disliked);
        this.addBeerNotTested = findViewById(R.id.add_beer_rb_notested);

        this.addBeerButton = findViewById(R.id.add_beer_button);
    }

    @Override
    public void onClick(View v) {

        if (this.addBeerName.getText().toString().isEmpty() && this.addBeerManufacturer.getText().toString().isEmpty() && this.addBeerStyle.getText().toString().isEmpty() && (!this.addBeerLiked.isChecked() && !this.addBeerDisliked.isChecked() && !this.addBeerNotTested.isChecked())) throwToast(R.string.errDataEmptyAddBeer);
        else if (this.addBeerName.getText().toString().isEmpty()) throwToast(R.string.errBeerNameEmpty);
        else if (this.addBeerManufacturer.getText().toString().isEmpty()) throwToast(R.string.errBeerManufacturerEmpty);
        else if (this.addBeerStyle.getText().toString().isEmpty()) throwToast(R.string.errBeerStyleEmpty);
        else if (!this.addBeerLiked.isChecked() && !this.addBeerDisliked.isChecked() && !this.addBeerNotTested.isChecked()) throwToast(R.string.errBeerStatusEmpty);
        else {

            this.addBeerActionBar.setDisplayHomeAsUpEnabled(false);
            setProgressBar(getString(R.string.loadingAdd), this.addBeerRelative, this.addBeerProgress, this.addBeerTextProgress);

            String name_beer = this.addBeerName.getText().toString();
            String manufacturer_beer = this.addBeerManufacturer.getText().toString();
            String style_beer = this.addBeerStyle.getText().toString();

            Status status_beer;
            if (this.addBeerLiked.isChecked()) status_beer = Status.LIKED;
            else if (this.addBeerDisliked.isChecked()) status_beer = Status.DISLIKED;
            else status_beer = Status.NOTTESTED;

            Beer new_beer = new Beer(name_beer, manufacturer_beer, style_beer, status_beer);

            @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> at = new AsyncTask<Void,Void,Void>() {

                String error = null;

                @Override
                protected Void doInBackground(Void... v) {
                    try {((new BeerSA(getApplicationContext()))).addBeer(new_beer);}
                    catch (Exception e) {error = e.getMessage();}
                    return null;
                }

                @Override
                protected void onPostExecute(Void v) {
                    removeProgressBar(addBeerProgress,addBeerRelative);
                    if(error != null) throwToast(this.error);
                    else{
                        throwToast(R.string.successfulBeerAdded);
                        finish();
                    }
                }
            };

            at.execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { finish(); }
}