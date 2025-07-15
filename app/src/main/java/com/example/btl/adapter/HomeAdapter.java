package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.model.Movie;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movieList;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public HomeAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtGenre.setText("Thể loại: " + movie.getGenre());
        holder.txtRelease.setText("Khởi chiếu: " + movie.getReleaseDate());

        String posterName = movie.getPoster();
        if (posterName != null && !posterName.isEmpty()) {
            int resId = context.getResources().getIdentifier(posterName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgPoster.setImageResource(resId);
            } else {
                holder.imgPoster.setImageResource(R.drawable.sample_movie); // ảnh mặc định
            }
        } else {
            holder.imgPoster.setImageResource(R.drawable.sample_movie); // ảnh mặc định nếu null hoặc rỗng
        }



        // Bắt sự kiện click vào item
        holder.itemView.setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle, txtGenre, txtRelease;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtRelease = itemView.findViewById(R.id.txtRelease);
        }
    }
}
