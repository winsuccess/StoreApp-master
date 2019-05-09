package com.example.storeapp.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storeapp.R;

public class FormRegister extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    EditText _edtemail, _edtpassword, _edtname, _edtaddress, _edtphone, _edtrepassword;
    Button btnreg, btncancel;
    String email, password, name, phone, address, repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register);
        openHelper = new DatabaseHelper(this);
        _edtemail = (EditText) findViewById(R.id.edtemail);
        _edtpassword = (EditText) findViewById(R.id.edtpassword);
        _edtrepassword = (EditText) findViewById(R.id.edtrepassword);
        _edtname = (EditText) findViewById(R.id.edtname);
        _edtphone = (EditText) findViewById(R.id.edtphone);
        _edtaddress = (EditText) findViewById(R.id.edtaddress);
        btnreg = (Button) findViewById(R.id.btnregister);
        btncancel = (Button) findViewById(R.id.btncancel);


        getSupportActionBar().setTitle("Trang đăng ký");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                email = _edtemail.getText().toString();
                password = _edtpassword.getText().toString();
                repassword = _edtrepassword.getText().toString();
                name = _edtname.getText().toString();
                phone = _edtphone.getText().toString();
                address = _edtaddress.getText().toString();
                if (email.length() != 0 && name.length() != 0 && address.length() != 0 && phone.length() != 0 && password.length() != 0 && repassword.length() !=0){
                    if (password.equals(repassword)){
                        insertData(name, email, password, address, phone);
                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FormRegister.this, Login.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Nhập lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void insertData(String name, String email, String password, String address, String phone){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NAME, name);
        contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
        contentValues.put(DatabaseHelper.COLUMN_PASSWORD, password);
        contentValues.put(DatabaseHelper.COLUMN_ADDRESS, address);
        contentValues.put(DatabaseHelper.COLUMN_PHONE, phone);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }
}
