package org.webworks.datatool.Activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import org.webworks.datatool.Fragment.HIVTestingFragment;
import org.webworks.datatool.R;
import org.webworks.datatool.Repository.UserRepository;
import org.webworks.datatool.Session.SessionManager;
import org.webworks.datatool.Utility.ApiGetFacility;

public class TestingActivity extends SessionManager
        implements NavigationView.OnNavigationItemSelectedListener, HIVTestingFragment.OnFragmentInteractionListener {
    Context context;
    UserRepository userRepository;
    DrawerLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_testing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userRepository = new UserRepository(context);

        FloatingActionButton fab = findViewById(R.id.fab_testing);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TestingFormActivity.class);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_testing);
        navigationView.setNavigationItemSelectedListener(this);

        container = (DrawerLayout) findViewById(R.id.drawer_layout);

        initializeDefaultFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        /*initializeViews();
        setClickListeners();
        assignValues();*/
        super.onResume();

        Intent intent;
        if (!userRepository.userSessionValid()) {
            finish();
            intent = new Intent(context, LoginActivity.class);
            intent.putExtra("SESSION-LOGIN", 1);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.testing, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(context, SearchClientActivity.class)));


        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_tested) {
            // Handle testing action
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////            HIVTestingFragment fragment = new HIVTestingFragment();
////            transaction.replace(R.id.form_fragment_container, fragment);
////            transaction.disallowAddToBackStack();
////            transaction.commit();
        }

        if (id == R.id.nav_pos){

        }

        if (id == R.id.nav_refresh_facilities){
            ApiGetFacility apiGetFacility = new ApiGetFacility(context);
            apiGetFacility.execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    /**
     * Method initializes the testing fragment as default
     * */
    private void initializeDefaultFragment() {
        HIVTestingFragment hivTestingFragment = new HIVTestingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.form_fragment_container, hivTestingFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onRssItemSelected(String x) {

    }
}
