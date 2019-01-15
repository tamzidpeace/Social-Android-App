package com.example.arafat.socialnetwork;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private NavigationView navigationView;
   private DrawerLayout drawerLayout;
   private RecyclerView postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.navigation_view);

        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                UserMenuSelector(menuItem);
                return false;
            }
        });
    }

    private void UserMenuSelector(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_find_friens:
                Toast.makeText(this, "find friends", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_friends:
                Toast.makeText(this, "friends", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_post:
                Toast.makeText(this, "post", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_messages:
                Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
