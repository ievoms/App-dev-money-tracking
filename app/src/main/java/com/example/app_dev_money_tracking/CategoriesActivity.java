package com.example.app_dev_money_tracking;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    private LinearLayout category_text_linear;

    private RecyclerView categoryRecycler;
    private CategoriesAdapter adapter;

    private ArrayList<Categories> categoriesList;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        User_settings settings = User_settings.instanciate("user1", getApplicationContext());

        categoryRecycler=findViewById(R.id.recViewCategories);
        categoriesList = settings.retrieveRecordList();
        if(categoriesList == null) {
            categoriesList = Categories.getData(this);
        }
        adapter = new CategoriesAdapter(categoriesList, this);
        categoryRecycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryRecycler.setLayoutManager(manager);
        categoryRecycler.setAdapter(adapter);

        // Menu navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_categories);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(CategoriesActivity.this, Home_activity.class));
                    break;
                case R.id.nav_new_record:
                    startActivity(new Intent(CategoriesActivity.this, NewRecord.class));
                    break;
                case R.id.nav_categories:
                    startActivity(new Intent(CategoriesActivity.this, CategoriesActivity.class));
                    break;
            }

            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

}