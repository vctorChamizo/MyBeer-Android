package app.activities.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.R;
import java.util.ArrayList;
import app.activities.adapter.RecyclerViewAdapter;
import app.activities.beer.AddBeer;
import app.activities.brewery.AddBrewery;
import app.activities.main.Main;
import app.model.beer.Beer;
import app.model.beer.BeerSA;

@SuppressLint("Registered")
public class ListBeers extends Lists {

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() { super.onStart(); }

    @Override
    protected void onRestart(){

        super.onRestart();
        inicializateView();
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onPause() { super.onPause(); }

    @Override
    protected void onStop() { super.onStop(); }

    @Override
    protected void onSaveInstanceState(Bundle outState) { super.onSaveInstanceState(outState); }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    @Override
    public void onLowMemory() { super.onLowMemory(); }
    @Override

    protected void setAppBar() {

        super.setAppBar();

        this.listToolBar.setTitle(R.string.myBeers);
    }

    @Override
    protected void createListView() {

        super.createListView();

        @SuppressLint("StaticFieldLeak") AsyncTask<Object,Object,ArrayList<Beer>> at = new AsyncTask<Object, Object, ArrayList<Beer>>() {

            private String error = null;

            @Override
            protected ArrayList<Beer> doInBackground(Object... objects) {

                try { return (new BeerSA(getApplicationContext())).getAllBeers(); }
                catch (Exception e) { this.error = e.getMessage(); }

                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Beer> list_beers) {

                if( this.error != null) throwToast(this.error);
                else if (list_beers.size() == 0) throwToast(R.string.errListBreweriesEmptyInformation);
                else {

                    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> adapter = new RecyclerViewAdapter(list_beers);
                    listRecycler.setAdapter(adapter);
                }
            }
        };

        at.execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.drawer_main_home:
                startActivity(new Intent(this, Main.class));
                break;

            case R.id.drawer_main_brewery:
                startActivity(new Intent(this, ListBreweries.class));
                break;

            case R.id.drawer_main_add_brewey:
                startActivity(new Intent(this, AddBrewery.class));
                break;

            case R.id.drawer_main_add_beer:
                startActivity(new Intent(this, AddBeer.class));
                break;

            default:
                this.listDrawer.closeDrawer(GravityCompat.START);
                return true;
        }

        this.listDrawer.closeDrawer(GravityCompat.START);

        finish();

        return true;
    }
}
