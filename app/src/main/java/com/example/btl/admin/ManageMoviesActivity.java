package com.example.btl.admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.adapter.MovieAdapter;
import com.example.btl.employeedao.formovies;
import com.example.btl.model.Movie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class    ManageMoviesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerViewMovies;
    private formovies dao;
    private MovieAdapter adapter;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private Uri selectedPosterUri;
    private ImageView currentImgPreview;
    private Button btnDeleteSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_movies);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));

        Button btnAddMovie = findViewById(R.id.btnAddMovie);
        btnDeleteSelected = findViewById(R.id.btnDeleteSelected);

        dao = new formovies(this);
        loadMovieList();

        btnAddMovie.setOnClickListener(v -> showAddMovieDialog());

        btnDeleteSelected.setOnClickListener(v -> {
            List<Movie> toDelete = new ArrayList<>();
            for (Movie movie : movieList) {
                if (movie.isSelected()) {
                    toDelete.add(movie);
                }
            }

            if (toDelete.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 phim để xoá", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Movie movie : toDelete) {
                dao.deleteMovie(movie.getId());
                movieList.remove(movie);
            }

            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xoá phim đã chọn", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadMovieList() {
        movieList = new ArrayList<>(dao.getAllMovieObjects());
        adapter = new MovieAdapter(this, movieList);
        recyclerViewMovies.setAdapter(adapter);
    }

    private void showAddMovieDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_movie, null);

        EditText edtTitle = view.findViewById(R.id.edtTitle);
        EditText edtAuthor = view.findViewById(R.id.edtAuthor);
        EditText edtTrailer = view.findViewById(R.id.edtTrailer);
        EditText edtGenre = view.findViewById(R.id.edtGenre);
        EditText edtDuration = view.findViewById(R.id.edtDuration);
        EditText edtReleaseDate = view.findViewById(R.id.edtReleaseDate);
        EditText edtRatingHeart = view.findViewById(R.id.edtHeart);
        EditText edtRatingShare = view.findViewById(R.id.edtShare);
        EditText edtRating = view.findViewById(R.id.edtRating);


        edtReleaseDate.setFocusable(false);
        edtReleaseDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (view1, year1, month1, dayOfMonth) -> {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, month1 + 1, dayOfMonth);
                edtReleaseDate.setText(selectedDate);
            }, year, month, day).show();
        });

        ImageView imgPreview = view.findViewById(R.id.imgPosterPreview);
        Button btnChoosePoster = view.findViewById(R.id.btnChoosePoster);

        btnChoosePoster.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
            currentImgPreview = imgPreview;
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Thêm phim mới")
                .setView(view)
                .setPositiveButton("Thêm", null)
                .setNegativeButton("Hủy", null)
                .create();

        dialog.setOnShowListener(dlg -> {
            Button btnAdd = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btnAdd.setOnClickListener(v -> {
                try {
                    String title = edtTitle.getText().toString().trim();
                    String author = edtAuthor.getText().toString().trim();
                    String trailer = edtTrailer.getText().toString().trim();
                    String genre = edtGenre.getText().toString().trim();
                    int duration = Integer.parseInt(edtDuration.getText().toString().trim());
                    String releaseDate = edtReleaseDate.getText().toString().trim();
                    String posterUriStr = selectedPosterUri != null ? selectedPosterUri.toString() : null;

                    int ratingHeart = Integer.parseInt(edtRatingHeart.getText().toString().trim());
                    int ratingShare = Integer.parseInt(edtRatingShare.getText().toString().trim());
                    String rating = edtRating.getText().toString().trim();

                    dao.insertMovie(title, author, trailer, genre, duration, ratingHeart, ratingShare, rating, releaseDate, posterUriStr);
                    Toast.makeText(this, "Đã thêm phim", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    loadMovieList();
                } catch (Exception e) {
                    Toast.makeText(this, "Lỗi nhập liệu", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedPosterUri = data.getData();
            if (currentImgPreview != null) {
                currentImgPreview.setImageURI(selectedPosterUri);
            }
        }
    }
}
