package com.example.storeapp.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storeapp.Activity.CartActivity;
import com.example.storeapp.R;
import com.example.storeapp.model.request.MatHangSoLuong;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartApdapterHolder>{

    private List<MatHangSoLuong> matHangSoLuongs;
    private Context context;

    public void setMatHangSoLuongs(List<MatHangSoLuong> matHangSoLuongnew){
        this.matHangSoLuongs = matHangSoLuongnew;
    }

    public CartAdapter(List<MatHangSoLuong> matHangSoLuongs, Context context) {
        this.matHangSoLuongs = matHangSoLuongs;
        this.context = context;
    }

    @Override
    public CartApdapterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cart_item, viewGroup, false);
        return new CartApdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartApdapterHolder cartApdapterHolder, int position) {

        final MatHangSoLuong matHang = matHangSoLuongs.get(position);
        new DownloadImageTask(cartApdapterHolder.imgMatHang)
                .execute(matHang.getMatHang().getAnh());
        final DecimalFormat format = new DecimalFormat("###,###,###,###");
        cartApdapterHolder.tenMatHang.setText("Mặt Hàng: "+matHang.getMatHang().getTenMatHang());
        cartApdapterHolder.tongGia.setText("Tổng tiền: "+format.format(matHang.getSoLuong() * matHang.getMatHang().getGia()));
        cartApdapterHolder.soLuongMatHang.setText(String.valueOf(matHang.getSoLuong()));
        final CartActivity cartActivity = new CartActivity();
        cartApdapterHolder.tangMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(matHang.getSoLuong() < 10) matHang.setSoLuong(matHang.getSoLuong()+1);
                else{
                    matHang.setSoLuong(10);
                }
                final DecimalFormat format = new DecimalFormat("###,###,###,###");
                cartApdapterHolder.tenMatHang.setText("Mặt Hàng: "+matHang.getMatHang().getTenMatHang());
                cartApdapterHolder.tongGia.setText("Tổng tiền: "+format.format(matHang.getSoLuong() * matHang.getMatHang().getGia()));
                cartApdapterHolder.soLuongMatHang.setText(String.valueOf(matHang.getSoLuong()));
                cartActivity.EventTongtien();
            }
        });

        cartApdapterHolder.giamMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(matHang.getSoLuong() > 1) matHang.setSoLuong(matHang.getSoLuong()-1);
                else{
                    matHang.setSoLuong(1);
                }
                final DecimalFormat format = new DecimalFormat("###,###,###,###");
                cartApdapterHolder.tenMatHang.setText("Mặt Hàng: "+matHang.getMatHang().getTenMatHang());
                cartApdapterHolder.tongGia.setText("Tổng tiền: "+format.format(matHang.getSoLuong() * matHang.getMatHang().getGia()));
                cartApdapterHolder.soLuongMatHang.setText(String.valueOf(matHang.getSoLuong()));
                cartActivity.EventTongtien();
            }
        });

        cartApdapterHolder.loaiBoMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matHangSoLuongs.remove(matHang);
                cartActivity.EventTongtien();
                cartActivity.EventLoadCart();
            }
        });
    }

    @Override
    public int getItemCount() {
        return matHangSoLuongs.size();
    }

    public static class CartApdapterHolder extends RecyclerView.ViewHolder {
        TextView tenMatHang, tongGia;
        ImageView imgMatHang;
        Button tangMatHang,giamMatHang,soLuongMatHang;
        ImageButton loaiBoMatHang;

        public CartApdapterHolder(View v) {
            super(v);
            tenMatHang = itemView.findViewById(R.id.cart_itemName);
            tongGia = itemView.findViewById(R.id.cart_itemCost);
            tangMatHang = itemView.findViewById(R.id.btTangMatHang);
            giamMatHang = itemView.findViewById(R.id.btGiamMatHang);
            soLuongMatHang = itemView.findViewById(R.id.btSoLuongMatHang);
            imgMatHang = itemView.findViewById(R.id.imageViewCart);
            loaiBoMatHang = itemView.findViewById(R.id.removeButton);
        }
    }
}
