package com.example.btl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.employeedao.formovies;
import com.example.btl.employeedao.forticket;
import com.example.btl.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private TextView txtTitle, txtGenre, txtDuration, txtRelease, txtAuthor, txtTicketPrice;
    private ImageView imgPoster;
    private Button btnDatVe;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtTitle = findViewById(R.id.txtDetailTitle);
        txtGenre = findViewById(R.id.txtDetailGenre);
        txtDuration = findViewById(R.id.txtDetailDuration);
        txtRelease = findViewById(R.id.txtDetailReleaseDate);
        txtAuthor = findViewById(R.id.txtDetailAuthor);
        imgPoster = findViewById(R.id.imgDetailPoster);
        btnDatVe = findViewById(R.id.btnDatVe);
        txtTicketPrice = findViewById(R.id.txtTicketPrice);

        int movieId = getIntent().getIntExtra("movie_id", -1);

        if (movieId != -1) {
            formovies dao = new formovies(this);
            movie = dao.getMovieById(movieId);

            if (movie != null) {
                showMovieInfo(movie);
                txtTicketPrice.setText("Giá vé: " + movie.getPrice() + "đ"); // ✅ từ movie
            } else {
                Toast.makeText(this, "Không tìm thấy phim", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Lỗi truyền dữ liệu phim", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnDatVe.setOnClickListener(v -> {
            forticket ticketDao = new forticket(DetailActivity.this);
            String ticketPrice = movie.getPrice(); // ✅ từ movie

            int userId = 1; // user mẫu
            int eventId = 0;
            String room = "A1";
            String seat = "C1, C2";

            ticketDao.insertTicket(
                    userId,
                    eventId,
                    movie.getTitle(),
                    ticketPrice,
                    room,
                    seat,
                    movie.getPoster()
            );

            Toast.makeText(DetailActivity.this, "Đã thêm vé vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });
    }

    private void showMovieInfo(Movie movie) {
        txtTitle.setText(movie.getTitle());
        txtGenre.setText("Thể loại: " + movie.getGenre());
        txtDuration.setText("Thời lượng: " + movie.getDuration() + " phút");
        txtRelease.setText("Khởi chiếu: " + movie.getReleaseDate());
        txtAuthor.setText("Đạo diễn: " + movie.getAuthor());
        txtTicketPrice.setText("Giá vé: " + movie.getPrice() + "đ");

        String posterName = movie.getPoster();
        if (posterName != null && !posterName.trim().isEmpty()) {
            int resId = getResources().getIdentifier(posterName.trim(), "drawable", getPackageName());
            imgPoster.setImageResource(resId != 0 ? resId : R.drawable.sample_movie);
        } else {
            imgPoster.setImageResource(R.drawable.sample_movie);
        }
    }
}
