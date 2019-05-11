package com.example.travelbot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AgencyNavDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    ListSingleton ls;

    MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_nav_drawer);

        ls = ListSingleton.getInstance();

        Toolbar toolbar = findViewById(R.id.agency_toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.agency_drawer_layout);
        NavigationView navigationView = findViewById(R.id.agency_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //getWindow().setStatusBarColor(this.getResources().getColor(R.color.white1));
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        toggle.syncState();

        FirebaseUser currentUser = ls.mAuth.getCurrentUser();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.agency_fragment_container,
                    new Services()).commit();
            navigationView.setCheckedItem(R.id.nav_services);

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_services:
                getSupportFragmentManager().beginTransaction().replace(R.id.agency_fragment_container,
                        new Services()).commit();
                break;
            case R.id.nav_reviews:
                getSupportFragmentManager().beginTransaction().replace(R.id.agency_fragment_container,
                        new Reviews()).commit();
                break;
//            case R.id.nav_profile:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new Login()).commit();
//                break;
//            case R.id.nav_register:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new Register()).commit();
//                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, NavDrawer.class);
                startActivity(i);
                finish();
                break;
            case R.id.nav_quit:
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        else {
//            super.onBackPressed();
//        }

        else if(ls.fragmentFlag){

            super.onBackPressed();
        }

        else if (!(ls.fragmentFlag)) {

            getSupportFragmentManager().beginTransaction().replace(R.id.agency_fragment_container,
                    new Services()).commit();

        }


        else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
