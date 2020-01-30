package app.activities.brewery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.R;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import app.activities.main.MyBeerMain;
import app.model.address.Address;
import app.model.brewery.Brewery;
import app.model.brewery.BrewerySA;
import app.model.map.Map;
import app.model.status.Status;

@SuppressLint("Registered")
public class AddBrewery extends MyBeerMain implements View.OnClickListener,
                                                        OnMapReadyCallback,
                                                        PermissionsListener,
                                                        TextWatcher,
                                                        AdapterView.OnItemClickListener {

    private LinearLayout addBreweryProgress;
    private TextView addBreweryTextProgress;
    private LinearLayout addBreweryRelative;
    private ActionBar addBreweryActionBar;

    private EditText addBreweryName;
    private AutoCompleteTextView addBreweryAddress;
    private RadioButton addBreweryLiked, addBreweryDisliked, addBreweryNotTested;
    private Button addBreweryButton;

    private Address addBreweyFullAddress;
    private List<Address> adminMainSearchResult;
    private Boolean addBreweryDestinySelected;

    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;

    @Override
    protected void onDestroy(){
        this.mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        this.mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStart(){
        this.mapView.onStart();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Mapbox.getInstance(this, getString(R.string.access_token));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brewery);

        setAppBar();
        inicializateView();

        this.mapView.onCreate(savedInstanceState);

        this.addBreweryButton.setOnClickListener(this);
        this.addBreweryAddress.addTextChangedListener(this);
        this.addBreweryAddress.setOnItemClickListener(this);
        this.mapView.getMapAsync(this);
    }

    @Override
    protected void setAppBar() {

        Toolbar addBeerToolBar = findViewById(R.id.add_brewery_toolbar);
        setSupportActionBar(addBeerToolBar);
        this.addBreweryActionBar = getSupportActionBar();
        Objects.requireNonNull(this.addBreweryActionBar).setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        this.addBreweryActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void inicializateView() {

        this.addBreweryProgress = findViewById(R.id.add_brewery_progress);
        this.addBreweryTextProgress = findViewById(R.id.add_brewery_progress_text);
        this.addBreweryRelative = findViewById(R.id.add_brewery_relative);

        this.addBreweryName = findViewById(R.id.add_brewery_name);
        this.addBreweryAddress = findViewById(R.id.add_brewery_address);

        this.addBreweryLiked = findViewById(R.id.add_brewery_rb_liked);
        this.addBreweryDisliked = findViewById(R.id.add_brewery_rb_disliked);
        this.addBreweryNotTested = findViewById(R.id.add_brewery_rb_notested);

        this.mapView = findViewById(R.id.add_brewery_map);

        this.addBreweryButton = findViewById(R.id.add_brewery_button);

        this.addBreweryDestinySelected = false;
    }

    @Override
    public void onClick(View v) {

        if (this.addBreweryName.getText().toString().isEmpty() && this.addBreweryAddress.getText().toString().isEmpty() && (!this.addBreweryLiked.isChecked() && !this.addBreweryDisliked.isChecked() && !this.addBreweryNotTested.isChecked())) throwToast(R.string.errDataEmptyAddBeer);
        else if (this.addBreweryName.getText().toString().isEmpty()) throwToast(R.string.errBreweryNameEmpty);
        else if (this.addBreweryAddress.getText().toString().isEmpty()) throwToast(R.string.errBreweryAddressEmpty);
        else if (!this.addBreweryLiked.isChecked() && !this.addBreweryDisliked.isChecked() && !this.addBreweryNotTested.isChecked()) throwToast(R.string.errBreweryStatusEmpty);
        else {

            this.addBreweryActionBar.setDisplayHomeAsUpEnabled(false);
            setProgressBar(getString(R.string.loadingAdd), this.addBreweryRelative, this.addBreweryProgress, this.addBreweryTextProgress);

            String name_brewery = this.addBreweryName.getText().toString();
            String address_brewery = this.addBreweryAddress.getText().toString().split(",")[0];
            Double[] coordinates_brewery = this.addBreweyFullAddress.getCoordinates().toArray(new Double[0]);

            Status status_brewery;
            if (this.addBreweryLiked.isChecked()) status_brewery = Status.LIKED;
            else if (this.addBreweryDisliked.isChecked()) status_brewery = Status.DISLIKED;
            else status_brewery = Status.NOTTESTED;

            Brewery new_brewery = new Brewery(name_brewery, coordinates_brewery, address_brewery, status_brewery);

            @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> at = new AsyncTask<Void,Void,Void>() {

                String error = null;

                @Override
                protected Void doInBackground(Void... v) {
                    try {((new BrewerySA(getApplicationContext()))).addBrewery(new_brewery);}
                    catch (Exception e) {error = e.getMessage();}
                    return null;
                }

                @Override
                protected void onPostExecute(Void v) {
                    removeProgressBar(addBreweryProgress,addBreweryRelative);
                    if(error != null) throwToast(this.error);
                    else{
                        throwToast(R.string.successfulBreweryAdded);
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

    /*********************************************************************************************************************
     MAPBOX **/

    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;

        this.mapboxMap.getUiSettings().setCompassEnabled(false);
        this.mapboxMap.getUiSettings().setLogoEnabled(false);


        mapboxMap.setStyle(Style.OUTDOORS, style ->  {

            this.mapboxMap = mapboxMap;

            mapboxMap.setStyle(Style.MAPBOX_STREETS);
            CameraPosition cp = new CameraPosition.Builder().target(new LatLng(40.0000000, -4.0000000)).zoom(4).tilt(20).build();
            this.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp),3000);
        });
    }

    @SuppressLint("MissingPermission")
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = this.mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
        }
        else {
            this.permissionsManager = new PermissionsManager(this);
            this.permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { this.permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults); }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) { Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show(); }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) enableLocationComponent(Objects.requireNonNull(this.mapboxMap.getStyle()));
        else { Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show(); finish(); }
    }

    /**
     * Change the camera position the user add a new location
     */
    @SuppressWarnings("deprecation")
    private void moveMap(List<Double> coordinates) {

        CameraPosition cp = new CameraPosition.Builder()
                .target(new LatLng(coordinates.get(1), coordinates.get(0)))
                .zoom(15)
                .tilt(20)
                .build();

        this.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp),3000);

        ArrayList<Feature> newBeer = new ArrayList<>();
        newBeer.add(Feature.fromGeometry(Point.fromLngLat(coordinates.get(0), coordinates.get(1))));

        Bitmap newBeerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.new_beer);

        newBeerIcon = Bitmap.createScaledBitmap(newBeerIcon,110,110,true);

        Objects.requireNonNull(mapboxMap.getStyle()).addImage("new", newBeerIcon);

        if(mapboxMap.getStyle().getLayer("new"+"-layer")!=null) mapboxMap.getStyle().removeLayer("new"+"-layer");

        if(mapboxMap.getStyle().getSource("new"+"-source") != null) mapboxMap.getStyle().removeSource("new"+"-source");

        mapboxMap.getStyle().addSource(new GeoJsonSource("new"+"-source", FeatureCollection.fromFeatures(newBeer)));
        mapboxMap.getStyle().addLayer(new SymbolLayer("new"+"-layer", "new"+"-source").withProperties(PropertyFactory.iconImage("new")));
    }

    /*********************************************************************************************************************
     Bar to put the destiny into the map **/

    @Override
    public void afterTextChanged(Editable s) {

        if (this.addBreweryDestinySelected) this.addBreweryDestinySelected = false;
        else {

            if (s.toString().matches(".*\\s")) Map.getInstance(getApplicationContext()).getFullAddress(s.toString()).addOnCompleteListener(task-> {

                this.adminMainSearchResult = task.getResult();
                ArrayList<String> fullAddresses = new ArrayList<>();

                for (Address address : this.adminMainSearchResult) fullAddresses.add(address.getAddress());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fullAddresses);
                this.addBreweryAddress.setThreshold(1);
                this.addBreweryAddress.setAdapter(adapter);
                this.addBreweryAddress.showDropDown();
            });
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.addBreweryDestinySelected = true;

        int i = 0;
        String text = this.addBreweryAddress.getText().toString();

        while(i < this.adminMainSearchResult.size() && !adminMainSearchResult.get(i).getAddress().equals(text)) i++;

        if(i >= this.adminMainSearchResult.size()) throwToast(R.string.errDestinyNotExisit);
        else {

            this.addBreweryAddress.setSelection(0);

            this.addBreweyFullAddress = this.adminMainSearchResult.get(i);
            moveMap(this.adminMainSearchResult.get(i).getCoordinates());
        }
    }
}
