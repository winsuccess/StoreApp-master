package com.example.storeapp.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.controller.CartAdapter;
import com.example.storeapp.controller.DonHangController;
import com.example.storeapp.model.request.DonHangRequest;
import com.example.storeapp.model.request.GioHangDetail;
import com.example.storeapp.model.request.MatHangSoLuong;
import com.example.storeapp.model.response.DonHangResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CartActivity extends AppCompatActivity {

    private List<MatHangSoLuong> gioHang = MainActivity.gioHang;
    private static RecyclerView recyclerView;
    private static CartAdapter cartAdapter;
    private static double tongTienGioHang = 0;
    private static TextView tongTien;
    private Button thanhToan;
    private DonHangResponse donHangResponse;
    private DonHangController donHangController;
    EditText diaChi;
    Button guiDonHang;
    Button quayLai;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Giỏ hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        AnhXa();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        cartAdapter = new CartAdapter(MainActivity.gioHang, this);
        recyclerView.setAdapter(cartAdapter);

        EventTongtien();
        EventThanhToan();
        EventDialog();
    }

    private void EventDialog() {

    }

    private void EventThanhToan() {
        thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHang.size() == 0){
                    Toast toast= Toast.makeText(CartActivity.this, "Giỏ hàng đang chưa có sản phẩm nào.",   Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{dialog = new Dialog(CartActivity.this);
                    dialog.setTitle("Thêm thông tin đơn hàng");
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delivery_address);
                    diaChi = dialog.findViewById(R.id.txAddress);
                    guiDonHang = dialog.findViewById(R.id.btGui);
                    quayLai = dialog.findViewById(R.id.btQuaylai);
                    quayLai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    guiDonHang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DonHangRequest donHangRequest = new DonHangRequest();
                            donHangRequest.setGioHangDetail(new GioHangDetail("",MainActivity.gioHang));
                            donHangRequest.setTongTien(tongTienGioHang);
                            donHangRequest.setMaKhachHang("KH0001");
                            try {
                                donHangResponse = donHangController.createDonHang(donHangRequest);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(!"".equals(donHangResponse.getMessage())&&null != donHangResponse.getMessage()){
                                diaChi.setText(donHangResponse.getMessage());
                                dialog.cancel();
                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Đặt hàng không thành công");
                                builder.setMessage(donHangResponse.getMessage());
                                builder.setCancelable(false);
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }else {
                                MainActivity.gioHang = new ArrayList<>();
                                CartActivity.EventLoadCart();
                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Đặt hàng thành công");
                                builder.setMessage("Bạn đã đặt hàng thàng công. Cảm ơn quý khách đã ủng hộ cửa hàng");
                                builder.setCancelable(false);
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                tongTienGioHang = 0;
                                EventTongtien();
                                dialog.cancel();
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    private void AnhXa() {


        tongTien = findViewById(R.id.txTongTien);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerView);
        thanhToan = findViewById(R.id.btThanhToan);
        donHangController = new DonHangController();
    }

    public static void EventLoadCart() {
        cartAdapter.setMatHangSoLuongs(MainActivity.gioHang);
        recyclerView.setAdapter(cartAdapter);
    }

    public static void EventTongtien() {
        tongTienGioHang = 0;
        for(int i = 0 ; i < MainActivity.gioHang.size(); i++) {
            tongTienGioHang = tongTienGioHang + MainActivity.gioHang.get(i).getSoLuong() * MainActivity.gioHang.get(i).getMatHang().getGia();
        }
        if(tongTienGioHang == 0){
            tongTien.setText("Giỏ hàng đang chưa có sản phẩm");
            }
            else{
        DecimalFormat format = new DecimalFormat("###,###,###");
        tongTien.setText("Thành tiền: "+format.format(tongTienGioHang) + " VNĐ");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

}
