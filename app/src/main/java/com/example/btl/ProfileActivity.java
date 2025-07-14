package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private DrawerLayout drawerLayout;
    private TextView txtFullName;
    private Button btnShowInfo, btnLogout, btnChangePassword;
    private ImageButton btnMenu, btnBack, btnTicket;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private ListenerRegistration userListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        imgAvatar = findViewById(R.id.imgAvatar);
        txtFullName = findViewById(R.id.txtFullName);
        btnShowInfo = findViewById(R.id.btnShowInfo);
        btnLogout = findViewById(R.id.btnLogout);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnBack = findViewById(R.id.btn_back);
        btnMenu = findViewById(R.id.btn_menu);
        btnTicket = findViewById(R.id.btn_ticket);

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
                // Trang cá nhân
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (id == R.id.nav_item_2) {
                // Lịch sử đặt vé (có thể tạo thêm activity HistoryActivity nếu cần)
                Toast.makeText(this, "Chưa có chức năng lịch sử", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_item_3) {
                // Đăng xuất
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear ngăn quay lại
                startActivity(intent);
            }

            drawerLayout.closeDrawer(GravityCompat.END); // Đóng menu sau khi chọn
            return true;
        });

        ImageButton btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTicket.setOnClickListener(view ->
                Toast.makeText(this, "Ticket clicked", Toast.LENGTH_SHORT).show()
        );

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DocumentReference userRef = firestore.collection("users").document(uid);

            userListener = userRef.addSnapshotListener((snapshot, e) -> {
                if (snapshot != null && snapshot.exists()) {
                    Map<String, Object> data = snapshot.getData();
                    String name = data.get("name") != null ? data.get("name").toString() : "";
                    txtFullName.setText(name);
                }
            });
        }

        btnShowInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, UserDetailActivity.class));
        });

        btnChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected void onDestroy() {
        if (userListener != null) {
            userListener.remove();
        }
        super.onDestroy();
    }
}
