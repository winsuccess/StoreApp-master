package com.example.storeapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.controller.DownloadImageTask;
import com.example.storeapp.model.MatHang;
import com.example.storeapp.model.request.MatHangSoLuong;

import java.text.DecimalFormat;

public class ItemDetailActivity extends AppCompatActivity {
    Button themVaoGioHang;
    boolean exist = false;
    MatHang mh;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");
        mh = (MatHang) getIntent().getSerializableExtra("mathang");

        preprareDetailItem(mh);

        anhXa();
        eventThemHangVaoGio();
        themVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHang.size()<0){
                    MatHangSoLuong matHangSoLuong = new MatHangSoLuong(mh,1);
                    MainActivity.gioHang.add(matHangSoLuong);
                    Toast toast= Toast.makeText(ItemDetailActivity.this, "Đã thêm hàng vào giỏ",   Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    for(int i = 0; i < MainActivity.gioHang.size(); i++){
                        if(MainActivity.gioHang.get(i).getMatHang().getMaMatHang().equals(mh.getMaMatHang()) ){
                            MainActivity.gioHang.get(i).setSoLuong(MainActivity.gioHang.get(i).getSoLuong()+1);
                            exist = true;
                            Toast toast= Toast.makeText(ItemDetailActivity.this, "Đã thêm hàng vào giỏ",   Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        if(MainActivity.gioHang.get(i).getSoLuong() > 10) MainActivity.gioHang.get(i).setSoLuong(10);
                    }
                    if(exist == false){
                        MatHangSoLuong matHangSoLuong = new MatHangSoLuong(mh,1);
                        MainActivity.gioHang.add(matHangSoLuong);
                        Toast toast= Toast.makeText(ItemDetailActivity.this, "Đã thêm hàng vào giỏ",   Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }

    private void eventThemHangVaoGio() {
//        themVaoGioHang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(MainActivity.gioHang.size()<0){
//                    MatHangSoLuong matHangSoLuong = new MatHangSoLuong(mh,1);
//                }
//                else{
//                    for(int i = 0; i < MainActivity.gioHang.size(); i++){
//                        if(MainActivity.gioHang.get(i).getMatHang().getMaMatHang() == mh.getMaMatHang()){
//                            MainActivity.gioHang.get(i).setSoLuong(MainActivity.gioHang.get(i).getSoLuong()+1);
//                            exist = true;
//                        }
//                        if(MainActivity.gioHang.get(i).getSoLuong() > 10) MainActivity.gioHang.get(i).setSoLuong(10);
//                    }
//                    if(exist = true){
//                        MatHangSoLuong matHangSoLuong = new MatHangSoLuong(mh,1);
//                    }
//                }
//            }
//        });
    }

    private void anhXa() {
        themVaoGioHang = (Button) findViewById(R.id.btthemGioHang);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submain, menu);
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


    public void preprareDetailItem(MatHang mh) {
        ImageView imgView = findViewById(R.id.imageView);
        TextView detailItemName = findViewById(R.id.itemDetailName);
        TextView detailItemCost = findViewById(R.id.itemDetailCost);
        TextView detailItemDescription = findViewById(R.id.itemDetailDescription);

        new DownloadImageTask(imgView)
                .execute(mh.getAnh());
        detailItemName.setText(mh.getTenMatHang());
        DecimalFormat format = new DecimalFormat("###,###,###,###");
        detailItemCost.setText(format.format(mh.getGia()) + "VNĐ");
        detailItemDescription.setText(mh.getMoTa());
    }
}
