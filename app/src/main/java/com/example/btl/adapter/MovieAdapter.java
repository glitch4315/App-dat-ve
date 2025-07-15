package com.example.btl.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movieList;

        public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView txtTitle, txtGenre, txtAuthor, txtDuration;
        CheckBox checkboxSelect;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtMovieTitle);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            checkboxSelect = itemView.findViewById(R.id.checkboxSelect);
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
        holder.txtAuthor.setText("Tác giả: " + movie.getAuthor());
        holder.txtDuration.setText("Thời lượng: " + movie.getDuration() + " phút");

        // Load poster từ URI
        String posterPath = movie.getPoster();
        String posterName = movie.getPoster();
        if (posterName != null && !posterName.isEmpty()) {
            int resId = context.getResources().getIdentifier(posterName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgPoster.setImageResource(resId);
            } else {
                holder.imgPoster.setImageResource(R.drawable.sample_movie);
            }
        } else {
            holder.imgPoster.setImageResource(R.drawable.sample_movie);
        }


        // Checkbox chọn phim
        holder.checkboxSelect.setChecked(movie.isSelected());
        holder.checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            movie.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
