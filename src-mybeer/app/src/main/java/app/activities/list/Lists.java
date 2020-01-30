package app.activities.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.R;
import java.util.Objects;
import app.activities.main.MyBeerMain;

public abstract class Lists extends MyBeerMain implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout listDrawer;
    private NavigationView listNavigation;

    protected RecyclerView listRecycler;
    private LinearLayout listProgress, listLinear;
    private TextView listProgressText;

    protected Toolbar listToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setAppBar();
        inicializateView();

        setProgressBar(getString(R.string.loading), this.listProgress, this.listLinear, this.listProgressText);
        createListView();
        removeProgressBar(this.listProgress, this.listLinear);

        this.listNavigation.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void setAppBar() {

        this.listToolBar = findViewById(R.id.list_toolbar);
        setSupportActionBar(this.listToolBar);
        ActionBar listActionBar = getSupportActionBar();
        Objects.requireNonNull(listActionBar).setHomeAsUpIndicator(R.drawable.ic_menu_white);
        listActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        this.listDrawer.openDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void inicializateView() {

        this.listNavigation = findViewById(R.id.list_nav);
        this.listDrawer = findViewById(R.id.list_drawer);

        this.listRecycler = findViewById(R.id.list_recycler);
        this.listLinear = findViewById(R.id.list_linear);
        this.listProgress = findViewById(R.id.list_progress);
        this.listProgressText = findViewById(R.id.list_progress_text);
    }

    @Override
    public abstract boolean onNavigationItemSelected(@NonNull MenuItem menuItem);

    @Override
    public void onBackPressed() { finish(); }

    /**
     * Make a list with beers or breweries in data phone
     */
    protected void createListView() {

        RecyclerView recycler = findViewById(R.id.list_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
    }
}
