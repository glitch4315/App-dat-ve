package com.example.btl;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.sqlite.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBHelper dbHelper = new DBHelper(this);

        // Lấy đối tượng SQLiteDatabase để thực hiện các thao tác (nếu cần)
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.close();
    }

}