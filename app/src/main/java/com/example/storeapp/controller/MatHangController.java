package com.example.storeapp.controller;

import android.os.AsyncTask;

import com.example.storeapp.model.MatHang;
import com.example.storeapp.model.request.MatHangRequest;
import com.example.storeapp.model.response.DonHangResponse;
import com.example.storeapp.model.response.MatHangResponse;
import com.example.storeapp.model.response.ResponseMessage;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class MatHangController {
    public MatHangResponse getBanChay(MatHangRequest matHangRequest)
            throws ExecutionException, InterruptedException {
        return new GetListMatHang().execute(matHangRequest).get();
    }

    public MatHang getMatHang(String maMatHang)
            throws ExecutionException, InterruptedException {
        return new GetMatHang().execute(maMatHang).get();
    }

    public static final String HOSTNAME = "http://192.168.1.100:8080";


    public class GetMatHang extends AsyncTask<String, Void, MatHang> {

        @Override
        protected MatHang doInBackground(String... String) {
            RestTemplate rest = new RestTemplate();
            try {
                rest.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                rest.getMessageConverters()
                        .add(new StringHttpMessageConverter());
                String maMatHang = String[0];
                String c = HOSTNAME + "/api/san-pham/get/chi-tiet/" + maMatHang;
                MatHang matHang = rest.getForObject(c, MatHang.class);
                return matHang;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }


    public class GetListMatHang extends AsyncTask<MatHangRequest, Void, MatHangResponse> {

        @Override
        protected MatHangResponse doInBackground(MatHangRequest... MatHangRequest) {
            RestTemplate rest = new RestTemplate();
            try {
                rest.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                rest.getMessageConverters()
                        .add(new StringHttpMessageConverter());
                MatHangRequest matHang = MatHangRequest[0];
                String c = HOSTNAME + "/api/san-pham/get/danh-sach";
                MatHangResponse matHangResponse = rest.postForObject(c,matHang, MatHangResponse.class);
                return matHangResponse;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }

    public static void main(String[] args) {
        MatHangController matHangController = new MatHangController();

        MatHangRequest mhRequest = new MatHangRequest("",0,"0");
        MatHangResponse mhResponse= new MatHangResponse();
        try {
            mhResponse = matHangController.getBanChay(mhRequest);
        } catch (Exception e) {
        }
        if(mhResponse!=null) {
            int i = 5;
            while(i>0)
            {

                System.out.println("WRONG");
                i--;
            }
        }
    }



}
