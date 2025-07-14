package com.example.btl;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import com.example.btl.R;
import com.example.btl.adapter.MovieAdapter;
import com.example.btl.model.Movie;
import com.example.btl.sqlite.DBHelper;
import java.util.List;

public class MovieListFragment extends Fragment {

    private static final String ARG_TYPE = "type"; // Ví dụ: "now_showing" hoặc "coming_soon"

    public static MovieListFragment newInstance(String type) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DBHelper db = new DBHelper(getContext());
        List<Movie> movieList = db.getAllMovies(); // Có thể lọc theo type nếu muốn

        MovieAdapter adapter = new MovieAdapter(requireContext(), movieList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
