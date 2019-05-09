
package com.example.storeapp.model.response;

import com.example.storeapp.model.DonHang;
import com.example.storeapp.model.request.GioHangDetail;

import java.io.Serializable;

public class DonHangResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    //có đủ đơn hàng này
    private DonHang donHang;
    //giỏ hàng này. trong này có 1 cái là maDOnHang nhưng kệ nó đi. cái đấy ko dùng đén. ông get cái matHangSoLuong là đc thôi
    private GioHangDetail gioHangDetail;
    private String message;

    public DonHangResponse(DonHang donHang, GioHangDetail gioHangDetail,
            String message) {
        super();
        this.donHang = donHang;
        this.gioHangDetail = gioHangDetail;
        this.message = message;
    }

    public DonHangResponse() {
        super();
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public GioHangDetail getGioHangDetail() {
        return gioHangDetail;
    }

    public void setGioHangDetail(GioHangDetail gioHangDetail) {
        this.gioHangDetail = gioHangDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
