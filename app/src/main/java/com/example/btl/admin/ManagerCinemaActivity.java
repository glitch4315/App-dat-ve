package com.example.btl.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;

public class ManagerCinemaActivity extends AppCompatActivity {

    private Button btnRoom, btnStaff, btnFood, btnEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_cinema);

        btnRoom = findViewById(R.id.btnRoom);
        btnStaff = findViewById(R.id.btnStaff);
        btnFood = findViewById(R.id.btnFood);
        btnEvent = findViewById(R.id.btnEvent);


        btnRoom.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageRoomsActivity.class)); // Bạn tạo activity này
        });

        btnStaff.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageUsersActivity.class)); // Quản lý nhân viên
        });

        btnFood.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageFoodActivity.class)); // Quản lý đồ ăn/nước
        });

        btnEvent.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageEventActivity.class)); // Trang quản lý sự kiện
        });

    }
}
