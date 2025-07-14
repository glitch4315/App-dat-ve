package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.model.giohangticket;

import java.util.List;

public class GioHangTicketAdapter extends RecyclerView.Adapter<GioHangTicketAdapter.ViewHolder> {

    private Context context;
    private List<giohangticket> list;
    private OnSelectionChangeListener listener;

    public interface OnSelectionChangeListener {
        void onSelectionChanged();
    }

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        this.listener = listener;
    }

    public GioHangTicketAdapter(Context context, List<giohangticket> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView txtMovieName, txtSeat, txtPrice, txtRoom;
        ImageView imgMoviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkboxSelect);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtSeat = itemView.findViewById(R.id.txtSeat);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgMoviePoster = itemView.findViewById(R.id.imgMoviePoster);
            txtRoom = itemView.findViewById(R.id.txtRoom);
        }
    }

    @NonNull
    @Override
    public GioHangTicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangTicketAdapter.ViewHolder holder, int position) {
        giohangticket ticked = list.get(position);

        holder.txtMovieName.setText(ticked.getMovieName());
        holder.txtSeat.setText("Chỗ: " + ticked.getSeat());
        holder.txtPrice.setText("Giá: " + ticked.getPrice());
        holder.checkbox.setChecked(ticked.isSelected());
        holder.txtRoom.setText("Phòng: " + ticked.getRoom());
        String posterName = ticked.getPoster();
        if (posterName != null && !posterName.isEmpty()) {
            int resId = context.getResources().getIdentifier(
                    posterName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imgMoviePoster.setImageResource(resId);
            } else {
                holder.imgMoviePoster.setImageResource(R.drawable.sample_movie); // fallback ảnh mặc định
            }
        } else {
            holder.imgMoviePoster.setImageResource(R.drawable.sample_movie); // fallback nếu null
        }



        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ticked.setSelected(isChecked);
            if (listener != null) listener.onSelectionChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

