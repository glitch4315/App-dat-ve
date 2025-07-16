package com.example.btl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.btl.employeedao.forusers;
import com.example.btl.model.User;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private DrawerLayout drawerLayout;
    private TextView txtFullName;
    private Button btnShowInfo, btnLogout, btnChangePassword;
    private ImageButton btnMenu, btnBack, btnTicket;

    private forusers userDao;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        imgAvatar = findViewById(R.id.imgAvatar);
        txtFullName = findViewById(R.id.txtFullName);
        btnShowInfo = findViewById(R.id.btnShowInfo);
        btnLogout = findViewById(R.id.btnLogout);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnBack = findViewById(R.id.btn_back);
        btnMenu = findViewById(R.id.btn_menu);
        btnTicket = findViewById(R.id.btn_ticket);

        userDao = new forusers(this);
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);

        if (userId != -1) {
            User user = userDao.getUserById(userId);
            if (user != null) {
                txtFullName.setText(user.getName());
            } else {
                txtFullName.setText("Khách");
            }
        } else {
            txtFullName.setText("Khách");
        }

        btnShowInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, UserDetailActivity.class));
        });

        btnChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnBack.setOnClickListener(v -> finish());

        btnTicket.setOnClickListener(view -> {
            Toast.makeText(this, "Ticket clicked", Toast.LENGTH_SHORT).show();
        });

        btnMenu.setOnClickListener(view -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_item_1) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (id == R.id.nav_item_2) {
                Toast.makeText(this, "Chưa có chức năng lịch sử", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_item_3) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }
}
