package com.example.storeapp.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storeapp.R;

public class ChangeProfile extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;
    EditText _edtemail, _edtname, _edtaddress, _edtphone;
    Button btnChange, btnCancel, _changePass;
    String id, email, password, name, phone, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnChange = (Button) findViewById(R.id.btnChange);
        _edtemail = (EditText) findViewById(R.id.edtemail);
        _edtname = (EditText) findViewById(R.id.edtname);
        _edtaddress = (EditText) findViewById(R.id.edtaddress);
        _edtphone = (EditText) findViewById(R.id.edtphone);
        _changePass = findViewById(R.id.changePassword);
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        findData(id);
        _edtemail.setText(email);
        _edtname.setText(name);
        _edtaddress.setText(address);
        _edtphone.setText(phone);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String txtemail = _edtemail.getText().toString();
                String txtname = _edtname.getText().toString();
                String txtphone = _edtphone.getText().toString();
                String txtaddress = _edtaddress.getText().toString();

                if (txtemail.length() != 0 && txtname.length() != 0 && txtaddress.length() != 0 && txtphone.length() != 0){
                    updateData(txtname, txtemail, txtaddress, txtphone);
                    Toast.makeText(getApplicationContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CategoryActivity.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void updateData(String name, String email, String address, String phone){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NAME, name);
        contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
        contentValues.put(DatabaseHelper.COLUMN_ADDRESS, address);
        contentValues.put(DatabaseHelper.COLUMN_PHONE, phone);
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        db.update(DatabaseHelper.TABLE_NAME, contentValues, "ID="+id,null);
    }
    public void findData(String id){
        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_ID + "=?", new String[]{id});
        if (cursor!=null) {
            if(cursor.moveToFirst()) {
                email = cursor.getString(2);
                name = cursor.getString(1);
                address = cursor.getString(4);
                phone = cursor.getString(5);
            }
        }
    }

}
