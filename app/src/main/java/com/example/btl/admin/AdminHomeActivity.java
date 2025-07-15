package com.example.btl.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.LoginActivity;
import com.example.btl.R;

public class AdminHomeActivity extends AppCompatActivity {

    Button btnManageMovies, btnManageUsers, btnManageTheaters, btnViewRevenue, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Khởi tạo các nút
        btnManageMovies = findViewById(R.id.btnManageMovies);
        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnManageTheaters = findViewById(R.id.btnManageTheaters);
        btnViewRevenue = findViewById(R.id.btnViewRevenue);
        btnLogout = findViewById(R.id.btnLogout);

        // Sự kiện click cho nút Quản lý phim
        btnManageMovies.setOnClickListener(v ->
                startActivity(new Intent(AdminHomeActivity.this, ManageMoviesActivity.class))
        );

        // Sự kiện click cho nút Quản lý người dùng
        btnManageUsers.setOnClickListener(v ->
                startActivity(new Intent(AdminHomeActivity.this, ManageUsersActivity.class))
        );

        // Sự kiện click cho nút Quản lý rạp
        btnManageTheaters.setOnClickListener(v ->
                startActivity(new Intent(AdminHomeActivity.this, ManageEventActivity.class))
        );

        // Sự kiện click cho nút Doanh thu
        btnViewRevenue.setOnClickListener(v ->
                startActivity(new Intent(AdminHomeActivity.this, RevenueActivity.class))
        );

        // Sự kiện click cho nút Đăng xuất
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
