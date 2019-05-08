package app.activities.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import app.activities.beer.AddBeer;
import app.activities.brewery.AddBrewery;
import app.activities.list.ListBeers;
import app.activities.list.ListBreweries;
import app.model.brewery.Brewery;
import app.model.brewery.BrewerySA;
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
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

@SuppressLint("MissingPermission")
public class Main extends MyBeerMain implements NavigationView.OnNavigationItemSelectedListener,
                                                OnMapReadyCallback,
                                                PermissionsListener,
                                                MapboxMap.OnMapClickListener, View.OnClickListener {

    private static final double MINDISTANCECLICK = 40;
    private DrawerLayout mainDrawer;
    private NavigationView mainNavigation;

    private FloatingActionButton mainButton;

    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private CameraPosition cp;

    private List<Brewery> breweries = new ArrayList<>();

    private Bitmap noVisited;
    private Bitmap liked;
    private Bitmap disliked;
    private Bitmap bubble;
    private Float[] padding;
    private Float[] offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Mapbox.getInstance(this, getString(R.string.access_token));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAppBar();
        inicializateView();

        this.mapView.onCreate(savedInstanceState);
        loadImages();
        getBreweries();

        this.mainButton.setOnClickListener(this);
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        this.mapView.onStart();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        this.mapView.onStart();
        getBreweries();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }

    @Override
    protected void setAppBar() {

        Toolbar mainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);
        ActionBar mainActionBar = getSupportActionBar();
        Objects.requireNonNull(mainActionBar).setHomeAsUpIndicator(R.drawable.ic_menu_white);
        mainActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void inicializateView() {

        this.mainNavigation = findViewById(R.id.main_nav);
        this.mainDrawer = findViewById(R.id.main_drawer);

        this.mapView = findViewById(R.id.main_map);

        this.mainButton = findViewById(R.id.main_button);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.drawer_main_brewery:
                startActivity(new Intent(this, ListBreweries.class));
                break;

            case R.id.drawer_main_beer:
                startActivity(new Intent(this, ListBeers.class));
                break;

            case R.id.drawer_main_add_brewey:
                startActivity(new Intent(this, AddBrewery.class));
                break;

            case R.id.drawer_main_add_beer:
                startActivity(new Intent(this, AddBeer.class));
                break;
        }

        this.mainDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        this.mainDrawer.openDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) { this.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp),4000); }

    @Override
    public void onBackPressed() { finish(); }

    /**
     * Get a list with all breweries in data phone
     */
    private void getBreweries() {

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void,Void,List<Brewery>> at = new AsyncTask<Void, Void, List<Brewery>>() {

            private String error = null;

            @Override
            protected List<Brewery> doInBackground(Void... voids) {

                try {return (new BrewerySA(getApplicationContext())).getAllBreweries();}
                catch (Exception e) {error = e.getMessage();}
                return null;
            }

            protected void onPostExecute(List<Brewery>list_breweries){

                if(error != null) throwToast(error);
                else if(list_breweries.size() == 0) throwToast(R.string.errListBreweriesEmptyInformation);
                else breweries = (list_breweries);

                mainNavigation.setNavigationItemSelectedListener(Main.this);
                mapView.getMapAsync(Main.this);
            }
        };

        at.execute();
    }

    /**
     * Put the breweries icons into the map
     *
     * @param breweries list of all breweries in the data phone
     */
    private void addBreweries(List<Brewery> breweries) {

        ArrayList<Feature> likedPoints = new ArrayList<>();
        ArrayList<Feature> dislikedPoints = new ArrayList<>();
        ArrayList<Feature> noVisitedPoints = new ArrayList<>();

        for(Brewery b: breweries) {

            switch (b.getStatus()) {

                case NOTTESTED: noVisitedPoints.add(Feature.fromGeometry(Point.fromLngLat(b.getCoordinates()[0], b.getCoordinates()[1]))); break;
                case DISLIKED: dislikedPoints.add(Feature.fromGeometry(Point.fromLngLat(b.getCoordinates()[0], b.getCoordinates()[1]))); break;
                default: likedPoints.add(Feature.fromGeometry(Point.fromLngLat(b.getCoordinates()[0], b.getCoordinates()[1])));break;
            }
        }

        Objects.requireNonNull(this.mapboxMap.getStyle()).addImage("liked", liked);
        Objects.requireNonNull(this.mapboxMap.getStyle()).addImage("disliked", disliked);
        Objects.requireNonNull(this.mapboxMap.getStyle()).addImage("noVisited", noVisited);

        if(this.mapboxMap.getStyle().getLayer("liked"+"-layer") != null) mapboxMap.getStyle().removeLayer("liked"+"-layer");
        if(this.mapboxMap.getStyle().getLayer("disliked"+"-layer") != null) mapboxMap.getStyle().removeLayer("disliked"+"-layer");
        if(this.mapboxMap.getStyle().getLayer("noVisited"+"-layer") != null) mapboxMap.getStyle().removeLayer("noVisited"+"-layer");

        if(this.mapboxMap.getStyle().getSource("liked"+"-source") != null) mapboxMap.getStyle().removeSource("liked"+"-source");
        if(this.mapboxMap.getStyle().getSource("disliked"+"-source") != null) mapboxMap.getStyle().removeSource("disliked"+"-source");
        if(this.mapboxMap.getStyle().getSource("noVisited"+"-source") != null) mapboxMap.getStyle().removeSource("noVisited"+"-source");

        this.mapboxMap.getStyle().addSource(new GeoJsonSource("liked"+"-source", FeatureCollection.fromFeatures(likedPoints)));
        this.mapboxMap.getStyle().addSource(new GeoJsonSource("disliked"+"-source", FeatureCollection.fromFeatures(dislikedPoints)));
        this.mapboxMap.getStyle().addSource(new GeoJsonSource("noVisited"+"-source", FeatureCollection.fromFeatures(noVisitedPoints)));

        this.mapboxMap.getStyle().addLayer(new SymbolLayer("liked"+"-layer", "liked"+"-source").withProperties(PropertyFactory.iconImage("liked")).withProperties(PropertyFactory.iconAllowOverlap(true)));
        this.mapboxMap.getStyle().addLayer(new SymbolLayer("disliked"+"-layer", "disliked"+"-source").withProperties(PropertyFactory.iconImage("disliked")).withProperties(PropertyFactory.iconAllowOverlap(true)));
        this.mapboxMap.getStyle().addLayer(new SymbolLayer("noVisited"+"-layer", "noVisited"+"-source").withProperties(PropertyFactory.iconImage("noVisited")).withProperties(PropertyFactory.iconAllowOverlap(true)));
    }

    /**
     * Load the icons to put in the brewery´s location into the map
     */
    private void loadImages() {

        this.liked = BitmapFactory.decodeResource(getResources(), R.drawable.visited);
        this.noVisited = BitmapFactory.decodeResource(getResources(), R.drawable.no_visited);
        this.disliked = BitmapFactory.decodeResource(getResources(), R.drawable.disliked);
        this.bubble = BitmapFactory.decodeResource(getResources(), R.drawable.bubble);

        this.liked = Bitmap.createScaledBitmap(liked,110,110,false);
        this.noVisited = Bitmap.createScaledBitmap(noVisited,110,110,false);
        this.disliked = Bitmap.createScaledBitmap(disliked,110,110,false);
        this.bubble = Bitmap.createScaledBitmap(bubble,110,110,false);

        this.offset = new Float[2];
        this.offset[0] = 1.5f;
        this.offset[1] = -2.5f;

        this.padding = new Float[4];
        this.padding[0] = 5f; //up
        this.padding[1] = 5f; //left
        this.padding[2] = 12f;//bottom
        this.padding[3] = 5f; //right
    }

    /*********************************************************************************************************************
     MAPBOX **/

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;

        this.mapboxMap.getUiSettings().setCompassEnabled(false);
        this.mapboxMap.getUiSettings().setLogoEnabled(false);

        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {

            enableLocationComponent(style);
            this.mapboxMap.addOnMapClickListener(this);
            Main.this.addBreweries(breweries);
        });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);

            cp = new CameraPosition.Builder()
                    .target(new LatLng(Objects.requireNonNull(locationComponent.getLastKnownLocation()).getLatitude(), locationComponent.getLastKnownLocation().getLongitude()))
                    .zoom(14)
                    .tilt(20)
                    .build();
        }
        else {

            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        PointF clicked = this.mapboxMap.getProjection().toScreenLocation(point);

        LatLng coordinatesTmp = new LatLng();
        PointF projectionTmp;

        boolean found = false;

        for(int i = 0; i<this.breweries.size() && !found; i++) {

            coordinatesTmp.setLatitude(breweries.get(i).getCoordinates()[1]);
            coordinatesTmp.setLongitude(breweries.get(i).getCoordinates()[0]);
            projectionTmp = mapboxMap.getProjection().toScreenLocation(coordinatesTmp);

            if (Math.sqrt(Math.pow(clicked.x - projectionTmp.x, 2) + Math.pow(clicked.y - projectionTmp.y, 2)) <= MINDISTANCECLICK) {

                found = true;

                if(Objects.requireNonNull(mapboxMap.getStyle()).getLayer("labels")!=null)mapboxMap.getStyle().removeLayer("labels");
                if(mapboxMap.getStyle().getSource("labels")!=null)mapboxMap.getStyle().removeSource("labels");

                mapboxMap.getStyle().addSource(new GeoJsonSource("labels",FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(breweries.get(i).getCoordinates()[0]+0.00001, breweries.get(i).getCoordinates()[1]+0.00001)))));

                Objects.requireNonNull(mapboxMap.getStyle()).addImage("bubble", bubble);
                mapboxMap.getStyle().addLayer(new SymbolLayer("labels","labels")
                        .withProperties(PropertyFactory.textField(breweries.get(i).getName()))
                        .withProperties(PropertyFactory.textOffset(offset))
                        .withProperties(PropertyFactory.iconImage("bubble"))
                        .withProperties(PropertyFactory.iconTextFit(Property.ICON_TEXT_FIT_BOTH))
                        .withProperties(PropertyFactory.iconTextFitPadding(padding))
                        .withProperties(PropertyFactory.iconAllowOverlap(true))
                        .withProperties(PropertyFactory.textAllowOverlap(true)));
            }
        }

        if(!found) {

            if (Objects.requireNonNull(mapboxMap.getStyle()).getLayer("labels") != null) mapboxMap.getStyle().removeLayer("labels");
            if (mapboxMap.getStyle().getSource("labels") != null) mapboxMap.getStyle().removeSource("labels");
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { this.permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults); }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) { Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show(); }

    @Override
    public void onPermissionResult(boolean granted) {

        if (granted) mapboxMap.getStyle(this::enableLocationComponent);
        else {

            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
