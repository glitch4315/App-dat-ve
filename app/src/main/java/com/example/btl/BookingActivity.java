package com.example.btl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    TextView tvMovieTitle, tvCinema, tvTime, tvSeat;
    Button btnConfirmBooking;
    ImageButton btnFavorite, btnShare;
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Ánh xạ View
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvCinema = findViewById(R.id.tvCinema);
        tvTime = findViewById(R.id.tvTime);
        tvSeat = findViewById(R.id.tvSeat);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShare = findViewById(R.id.btnShare);

        // Lấy dữ liệu từ MovieDetailActivity nếu cần
        String movieTitle = getIntent().getStringExtra("movieTitle");
        tvMovieTitle.setText(movieTitle != null ? movieTitle : "Tên phim chưa có");

        // Đặt vé
        btnConfirmBooking.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại sau khi đặt vé
        });

        // Yêu thích
        btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            btnFavorite.setImageResource(isFavorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline);
            Toast.makeText(this, isFavorite ? "Đã thêm vào yêu thích" : "Đã bỏ khỏi yêu thích", Toast.LENGTH_SHORT).show();
        });

        // Chia sẻ
        btnShare.setOnClickListener(v -> {
            String shareText = "Xem phim " + tvMovieTitle.getText().toString() + " tại CGV!";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
        });
    }
}
