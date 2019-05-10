package com.example.storeapp.Activity;

import android.content.ContentValues;
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
import com.example.storeapp.controller.DatabaseHelper;

public class ChangePass extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;
    EditText _edtpassword, _edtrepassword;
    Button btnChange, btnCancel;
    String id, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnChange = (Button) findViewById(R.id.btnChange);
        _edtpassword = (EditText) findViewById(R.id.edtpassword);
        _edtrepassword = (EditText) findViewById(R.id.edtrepassword);
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String txtpassword = _edtpassword.getText().toString();
                String txtrepassword = _edtrepassword.getText().toString();
                if (txtpassword.length() != 0 && txtrepassword.length() != 0){
                    if (txtpassword.equals(txtrepassword)){
                        updateData(txtpassword);
                        Toast.makeText(getApplicationContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Nhập lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void updateData(String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_PASSWORD, password);
        SharedPreferences sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        db.update(DatabaseHelper.TABLE_NAME, contentValues, "ID="+id,null);
    }
}
