package com.example.travelbot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    ListSingleton ls;

    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_nav);

        ls = ListSingleton.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
        menuItem = navigationView.getMenu().findItem(R.id.nav_login);
        Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();
        if(currentUser != null)
        {
            menuItem.setTitle("Log Out");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Chat()).commit();
            navigationView.setCheckedItem(R.id.nav_message);

        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Chat()).commit();
                break;
//            case R.id.nav_chat:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new Login()).commit();
//                break;
            case R.id.nav_login:
                switch (item.getTitle().toString())
                {
                    case "Agency Login":
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Login()).commit();
                        break;
                    case "Log Out":
                        FirebaseAuth.getInstance().signOut();
                        menuItem.setTitle("Agency Login");
                        break;
                }
                break;
            case R.id.nav_register:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Register()).commit();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.nav_login:
//                FirebaseAuth.getInstance().signOut();
//                break;
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

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Chat()).commit();

        }


        else {
            getSupportFragmentManager().popBackStack();
        }
    }
}