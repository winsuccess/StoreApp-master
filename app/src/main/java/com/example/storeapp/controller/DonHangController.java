package com.example.storeapp.controller;

import android.os.AsyncTask;

import com.example.storeapp.model.DonHang;
import com.example.storeapp.model.request.DonHangRequest;
import com.example.storeapp.model.response.DonHangResponse;
import com.example.storeapp.model.response.ListDonHang;
import com.example.storeapp.model.response.ResponseMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DonHangController {
//cái này là sẽ lấy chi tiết đơn hàng từ server bằng maDonHang
    public DonHangResponse getDonHangFormServer(String maDonHang)
            throws ExecutionException, InterruptedException {
        return new GetDonHang().execute(maDonHang).get();
    }
//cái này để lấy hết đơn hàng trong cơ sở dữ liệu có mã khách hàng vừa nhập vào đó
    public List<DonHang> GetAllDonHangByMaKhachHang(String maKhachHang)
            throws ExecutionException, InterruptedException {
        return new GetAllDonHangByMaKhachHang().execute(maKhachHang).get();
    }

    public String deleteDonHang̣̣(String maDonHang) throws ExecutionException, InterruptedException {
        return new DeleteDonHang().execute(maDonHang).get().getMessage();
    }

    public DonHangResponse createDonHang(DonHangRequest donHangRequest) throws ExecutionException, InterruptedException {
        return new CreateDonHang().execute(donHangRequest).get();
    }


    public class GetDonHang extends AsyncTask<String, Void, DonHangResponse> {

        @Override
        protected DonHangResponse doInBackground(String... String) {
            RestTemplate rest = new RestTemplate();
            try {
                rest.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                rest.getMessageConverters()
                        .add(new StringHttpMessageConverter());
                String maDonHang = String[0];
                String c = MatHangController.HOSTNAME + "/api/don-hang/get/chi-tiet/" + maDonHang;
                DonHangResponse a = rest.getForObject(c, DonHangResponse.class);
                return a;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }

    public class DeleteDonHang extends AsyncTask<String, Void, ResponseMessage> {

        @Override
        protected ResponseMessage doInBackground(String... String) {
            RestTemplate rest = new RestTemplate();
            try {
                rest.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                rest.getMessageConverters()
                        .add(new StringHttpMessageConverter());
                String maDonHang = String[0];
                String c = MatHangController.HOSTNAME + "/api/don-hang/delete/" + maDonHang;
                ResponseMessage a = rest.getForObject(c, ResponseMessage.class);
                return a;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }

    public class CreateDonHang extends AsyncTask<DonHangRequest, Void, DonHangResponse> {

        @Override
        protected DonHangResponse doInBackground(DonHangRequest... DonHangRequest) {
            RestTemplate rest = new RestTemplate();
            try {
                rest.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                rest.getMessageConverters()
                        .add(new StringHttpMessageConverter());
                DonHangRequest donHangRequest = DonHangRequest[0];
                String c = MatHangController.HOSTNAME + "/api/don-hang/create/";
                ResponseEntity<DonHangResponse> donHangResponseResponseEntity = rest.postForEntity(c, donHangRequest , DonHangResponse.class);
                return donHangResponseResponseEntity.getBody();
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }

    public class GetAllDonHangByMaKhachHang extends AsyncTask<String, Void, List<DonHang>> {

        @Override
        protected List<DonHang> doInBackground(String... String) {
            RestTemplate rest = new RestTemplate();
            try {
                rest.getMessageConverters()
                        .add(new MappingJackson2HttpMessageConverter());
                rest.getMessageConverters()
                        .add(new StringHttpMessageConverter());
                String maKhachHang = String[0];
                String c = MatHangController.HOSTNAME + "/api/don-hang/get/all/" + maKhachHang;
                ListDonHang donHangList = rest.getForObject(c, ListDonHang.class);
                return donHangList.getDanhSachDonHang();
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }
}
