package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.DetailActivity;
import com.example.btl.R;
import com.example.btl.model.Movie;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MovieViewHolder> {

    private final Context context;
    private final List<Movie> movieList;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onClick(Movie movie);
    }

    public HomeAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle, txtGenre, txtDuration;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtMovieTitle);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtDuration = itemView.findViewById(R.id.txtDuration);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtGenre.setText("Thể loại: " + movie.getGenre());
        holder.txtDuration.setText("Thời lượng: " + movie.getDuration() + " phút");

        String posterName = movie.getPoster();
        if (posterName != null && !posterName.trim().isEmpty()) {
            int resId = context.getResources().getIdentifier(posterName.trim(), "drawable", context.getPackageName());
            holder.imgPoster.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_background);
        } else {
            holder.imgPoster.setImageResource(R.drawable.ic_launcher_background);
        }

        // Gọi listener (một nơi duy nhất để xử lý sự kiện click)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(movie);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
