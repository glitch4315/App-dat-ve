package com.example.btl;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.*;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imgPoster;
    private TextView txtTitle, txtReleaseDate, txtDuration, txtLikes,
            txtDescription, txtCensorship, txtGenre, txtDirector, txtActors, txtLanguage;
    private Button btnBookTicket;
    private FirebaseFirestore firestore;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Ánh xạ view
        imgPoster = findViewById(R.id.imgPoster);
        txtTitle = findViewById(R.id.txtTitle);
        txtReleaseDate = findViewById(R.id.txtReleaseDate);
        txtDuration = findViewById(R.id.txtDuration);
        txtLikes = findViewById(R.id.txtLikes);
        txtDescription = findViewById(R.id.txtDescription);
        txtCensorship = findViewById(R.id.txtCensorship);
        txtGenre = findViewById(R.id.txtGenre);
        txtDirector = findViewById(R.id.txtDirector);
        txtActors = findViewById(R.id.txtActors);
        txtLanguage = findViewById(R.id.txtLanguage);
        btnBookTicket = findViewById(R.id.btnBookTicket);

        firestore = FirebaseFirestore.getInstance();
        movieId = getIntent().getStringExtra("movieId");

        loadMovieDetail();
    }

    private void loadMovieDetail() {
        firestore.collection("movies").document(movieId)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        txtTitle.setText(document.getString("title"));
                        txtGenre.setText("Thể loại: " + document.getString("genre"));
                        txtDirector.setText("Đạo diễn: " + document.getString("director"));
                        txtActors.setText("Diễn viên: " + document.getString("actors"));
                        txtDescription.setText(document.getString("description"));
                        txtLanguage.setText("Ngôn ngữ: Tiếng Việt - Phụ đề Tiếng Anh");
                        txtCensorship.setText("Kiểm duyệt: T18 - PHIM ĐƯỢC PHỔ BIẾN ĐẾN NGƯỜI XEM TỪ ĐỦ 18 TUỔI TRỞ LÊN (18+)");
                        txtLikes.setText("364"); // hoặc bạn có thể lưu số like vào Firestore

                        List<String> showTimes = (List<String>) document.get("showTimes");
                        if (showTimes != null && !showTimes.isEmpty()) {
                            txtReleaseDate.setText(showTimes.get(0));
                        }

                        // Giả định phim nào cũng 1h51p
                        txtDuration.setText("1 giờ 51 phút");

                        // Load poster
                        String posterUrl = document.getString("posterUrl");
                        if (posterUrl != null && !posterUrl.isEmpty()) {
                            Glide.with(this).load(posterUrl).into(imgPoster);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
