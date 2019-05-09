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
import com.example.storeapp.Activity.OrderActivity;
import com.example.storeapp.R;
import com.example.storeapp.model.request.MatHangSoLuong;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterHolder>{

    private List<MatHangSoLuong> matHangSoLuongs;
    private Context context;

    public void setMatHangSoLuongs(List<MatHangSoLuong> matHangSoLuongnew){
        this.matHangSoLuongs = matHangSoLuongnew;
    }

    public OrderAdapter(List<MatHangSoLuong> matHangSoLuongs, Context context) {
        this.matHangSoLuongs = matHangSoLuongs;
        this.context = context;
    }

    @Override
    public OrderAdapterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.order_item, viewGroup, false);
        return new OrderAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapterHolder orderAdapterHolder, int position) {

        final MatHangSoLuong matHang = matHangSoLuongs.get(position);
        new DownloadImageTask(orderAdapterHolder.imgMatHang)
                .execute(matHang.getMatHang().getAnh());
        final DecimalFormat format = new DecimalFormat("###,###,###,###");
        orderAdapterHolder.tenMatHang.setText("Mặt Hàng: "+matHang.getMatHang().getTenMatHang());
        orderAdapterHolder.tongGia.setText("Tổng tiền: "+format.format(matHang.getSoLuong() * matHang.getMatHang().getGia()));
        orderAdapterHolder.soLuongMatHang.setText(String.valueOf(matHang.getSoLuong()));
        final OrderActivity orderActivity = new OrderActivity();

        orderAdapterHolder.loaiBoMatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matHangSoLuongs.remove(matHang);
                OrderActivity.EventTongtien();
                OrderActivity.EventLoadCart();
            }
        });
    }

    @Override
    public int getItemCount() {
        return matHangSoLuongs.size();
    }

    public static class OrderAdapterHolder extends RecyclerView.ViewHolder {
        TextView tenMatHang, tongGia;
        ImageView imgMatHang;
        Button tangMatHang,giamMatHang,soLuongMatHang;
        ImageButton loaiBoMatHang;

        public OrderAdapterHolder(View v) {
            super(v);
            tenMatHang = itemView.findViewById(R.id.order_itemName);
            tongGia = itemView.findViewById(R.id.order_itemCost);
            soLuongMatHang = itemView.findViewById(R.id.btSoLuongMatHang);
            imgMatHang = itemView.findViewById(R.id.imageViewOrder);
            loaiBoMatHang = itemView.findViewById(R.id.removeButton);
        }
    }
}
