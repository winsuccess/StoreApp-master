package com.example.storeapp.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.storeapp.Activity.OrderActivity;
import com.example.storeapp.R;
import com.example.storeapp.model.DonHang;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private List<DonHang> orderList;
    private Context context;
    public OrderListAdapter(List<DonHang> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public OrderListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DonHang dh = orderList.get(position);
        holder.orderId.setText("Mã đơn hàng: "+dh.getMaDonHang());
        final DecimalFormat format = new DecimalFormat("###,###,###,###");
        holder.orderTotal.setText("Tổng tiền: "+format.format(dh.getTongTien())+" VND");
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        holder.orderDate.setText("Ngày đặt hàng:"+sdf.format(dh.getNgayTao()));
        holder.orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("maDonHang",dh.getMaDonHang());
                intent.putExtra("ngayDat","Ngày đặt hàng: "+sdf.format(dh.getNgayTao()));
                intent.putExtra("tongTien","Tổng tiền: "+format.format(dh.getTongTien())+" VND");
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView orderDate;
        TextView orderTotal;
        LinearLayout orderLayout;
        public MyViewHolder(View v) {
            super(v);
            orderId =  itemView.findViewById(R.id.list_order_id);
            orderDate = itemView.findViewById(R.id.list_order_time);
            orderTotal = itemView.findViewById(R.id.list_order_price);
            orderLayout = itemView.findViewById(R.id.list_order_item);
        }
    }
}