package com.example.btl.adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.model.EventPromotion;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.VH> {

    public interface OnItemClick {
        void onEdit(EventPromotion p);
        void onDelete(EventPromotion p);
    }

    private final List<EventPromotion> data;
    private final Context ctx;
    private final OnItemClick listener;

    public PromotionAdapter(Context ctx, List<EventPromotion> data, OnItemClick listener) {
        this.ctx = ctx;
        this.data = data;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_promotion, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        EventPromotion e = data.get(position);
        holder.title.setText(e.getTitle());
        holder.desc.setText(e.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onEdit(e));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDelete(e);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, desc;
        ImageView img;

        VH(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.tvPromoTitle);
            desc = v.findViewById(R.id.tvPromoDesc);
        }
    }
}
