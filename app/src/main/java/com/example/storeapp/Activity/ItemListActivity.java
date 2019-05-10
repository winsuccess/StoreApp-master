package com.example.storeapp.Activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.storeapp.R;
import com.example.storeapp.controller.ItemListAdapter;
import com.example.storeapp.controller.MatHangController;
import com.example.storeapp.model.MatHang;
import com.example.storeapp.model.request.MatHangRequest;
import com.example.storeapp.model.response.MatHangResponse;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<MatHang> mhList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemListAdapter mAdapter;

    public static String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Các mặt hàng");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        category = intent.getStringExtra("danhmuc");
        prepareItemsList(category);
        recyclerView = (RecyclerView) findViewById(R.id.rv_itemList);
        mAdapter = new ItemListAdapter(mhList,this);
        GridLayoutManager mGridManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mGridManager);
        recyclerView.setAdapter(mAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart)
            startActivity(new Intent(this, CartActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homePage) {

        } else if (id == R.id.nav_category) {
            startActivity(new Intent(this, CategoryActivity.class));
        } else if (id == R.id.nav_orderManagement) {
            startActivity(new Intent(this, ListOrderActivity.class));
        } else if (id == R.id.nav_accountManagement) {
            startActivity(new Intent(this, ChangeProfile.class));
        }
        else if (id == R.id.btnLogout){
            startActivity(new Intent(this, Login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        category = savedInstanceState.getString(category);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(!TextUtils.isEmpty(category)){
            Intent intent = getIntent();
            if(intent!=null)
            outState.putString(intent.getStringExtra("danhmuc"), category);
        }
        super.onSaveInstanceState(outState);
    }

    public void prepareItemsList(String danhmuc) {
        MatHangController matHangController = new MatHangController();
        MatHangRequest mhRequest;
        if (danhmuc.equalsIgnoreCase("new"))
            mhRequest = new MatHangRequest("", 0, "");
        else if (danhmuc.equalsIgnoreCase("hot"))
            mhRequest = new MatHangRequest("", 0, "0");
        else
            mhRequest = new MatHangRequest(danhmuc, 0, "");
        MatHangResponse mhResponse = new MatHangResponse();
        try {
            mhResponse = matHangController.getBanChay(mhRequest);
        } catch (Exception e) {
        }
        if (mhResponse != null) {
            for (MatHang mh : mhResponse.getMatHang()) {
                mhList.add(mh);
            }
        }
        //mAdapter.notifyDataSetChanged();
    }
}
