package com.example.storeapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.controller.DonHangController;
import com.example.storeapp.controller.MatHangController;
import com.example.storeapp.controller.OrderItemAdapter;
import com.example.storeapp.controller.OrderListAdapter;
import com.example.storeapp.model.DonHang;
import com.example.storeapp.model.MatHang;
import com.example.storeapp.model.request.GioHangDetail;
import com.example.storeapp.model.request.MatHangRequest;
import com.example.storeapp.model.request.MatHangSoLuong;
import com.example.storeapp.model.response.DonHangResponse;
import com.example.storeapp.model.response.MatHangResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrderActivity extends AppCompatActivity {

    private List<MatHangSoLuong> matHangSoLuongs = new ArrayList<>();
    private String maDonHang;
    private static RecyclerView recyclerView;
    private static OrderItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        maDonHang = getIntent().getStringExtra("maDonHang");

        TextView maDonHangText = findViewById(R.id.order_id);
        TextView tienDonHangText = findViewById(R.id.order_price);
        TextView ngayDonHangText = findViewById(R.id.order_time);
        TextView diaChiText = findViewById(R.id.order_address);
        maDonHangText.setText("Mã đơn hàng: "+maDonHang);
        tienDonHangText.setText(getIntent().getStringExtra("tongTien"));
        ngayDonHangText.setText(getIntent().getStringExtra("ngayDat"));
        diaChiText.setText(getIntent().getStringExtra("diaChi"));

        prepareItemList(maDonHang);
        recyclerView = (RecyclerView) findViewById(R.id.order_recyclerView);
        mAdapter = new OrderItemAdapter(matHangSoLuongs, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

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

    public void eventHuyDonHang(View v)
    {
        DonHangController donHangController = new DonHangController();
        try {
            donHangController.deleteDonHang̣̣(maDonHang);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(v.getContext(),ListOrderActivity.class));
    }

    public void prepareItemList(String maDonHang) {
        DonHangController donHangController = new DonHangController();
        DonHangResponse donHangResponse = new DonHangResponse();
        try {
            donHangResponse = donHangController.getDonHangFormServer(maDonHang);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (donHangResponse != null) {
            {
                for (MatHangSoLuong matHangSoLuong : donHangResponse.getGioHangDetail().getListMH()) {
                    matHangSoLuongs.add(matHangSoLuong);
                }
            }
        }
    }
}