package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl.adapter.HomeAdapter;
import com.example.btl.adapter.MovieAdapter;
import com.example.btl.model.Movie;
import com.example.btl.sqlite.DBHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton btnMenu, btnProfile, btnTicket;
    private DBHelper db;
    private ViewPager2 bannerViewPager;
    private RecyclerView recyclerViewMovies;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Khởi tạo
        drawerLayout = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.btn_menu);
        btnProfile = findViewById(R.id.btn_profile);
        btnTicket = findViewById(R.id.btn_ticket);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        progressBar = findViewById(R.id.progressBar);



        // Menu Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_item_1) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (id == R.id.nav_item_2) {
                Toast.makeText(this, "Chưa có chức năng lịch sử", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_item_3) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });

        btnMenu.setOnClickListener(view -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        btnTicket.setOnClickListener(view -> {
            Intent intent = new Intent(this, GioHangActivity.class);
            startActivity(intent);
        });

        // Hiển thị danh sách phim bằng RecyclerView
        db = new DBHelper(this);
        List<Movie> movieList = db.getAllMovies(); // hoặc có thể lọc nếu có cột "type"
        HomeAdapter adapter = new HomeAdapter(this, movieList, movie -> {
            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        });
        recyclerViewMovies.setAdapter(adapter);

        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(adapter);
    }
}
