package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.model.giohangticket;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {
    private Context context;
    private List<giohangticket> ticketList;

    public RevenueAdapter(Context context, List<giohangticket> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder {
        TextView txtMovieName, txtSeat, txtBookingDate, txtPrice, txtRoom;

        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtSeat = itemView.findViewById(R.id.txtSeat);
            txtBookingDate = itemView.findViewById(R.id.txtBookingDate);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtRoom = itemView.findViewById(R.id.txtRoom);
        }
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_revenue, parent, false);
        return new RevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        giohangticket ticket = ticketList.get(position);

        holder.txtMovieName.setText("Phim: " + ticket.getMovieName());
        holder.txtSeat.setText("Ghế: " + ticket.getSeat());
        holder.txtBookingDate.setText("Ngày đặt: " + ticket.getBookingDate());
        holder.txtPrice.setText("Giá: " + ticket.getPrice() + "đ");
        holder.txtRoom.setText("Phòng: " + ticket.getRoom());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }
}
