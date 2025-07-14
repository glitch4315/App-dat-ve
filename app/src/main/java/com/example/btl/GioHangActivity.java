package com.example.btl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.adapter.GioHangTicketAdapter;
import com.example.btl.employeedao.forticket;
import com.example.btl.model.Movie;
import com.example.btl.model.giohangticket;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GioHangActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtTotalPrice;
    private Button btnDelete, btnThanhToan;
    private ImageButton imagebtnBack;
    private List<giohangticket> ticketList;
    private GioHangTicketAdapter adapter;
    private forticket dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        recyclerView = findViewById(R.id.recyclerViewCart);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnDelete = findViewById(R.id.btnDeleteSelected);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        imagebtnBack = findViewById(R.id.btnBack);
        imagebtnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        dao = new forticket(this);
        ticketList = dao.getAllTickets();

        adapter = new GioHangTicketAdapter(this, ticketList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnSelectionChangeListener(this::updateTotalPrice);

        btnThanhToan.setOnClickListener(view -> {
            int total = 0;
            ArrayList<giohangticket> selectedTickets = new ArrayList<>();
            for (giohangticket ticket : ticketList) {
                if (ticket.isSelected()) {
                    selectedTickets.add(ticket);
                    total += Integer.parseInt(ticket.getPrice());
                }
            }

            if (selectedTickets.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 vé để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, ThanhToanActivity.class);
            intent.putExtra("total", total);
            intent.putExtra("selectedTickets", selectedTickets);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            List<giohangticket> toDelete = new ArrayList<>();
            for (giohangticket ticket : ticketList) {
                if (ticket.isSelected()) {
                    dao.deleteTicket(ticket.getId());
                    toDelete.add(ticket);
                }
            }

            if (toDelete.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 vé để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            ticketList.removeAll(toDelete);
            adapter.notifyDataSetChanged();
            updateTotalPrice();

            Toast.makeText(this, "Đã xóa vé đã chọn", Toast.LENGTH_SHORT).show();
        });
    }
    public static void addToCart(Context context, Movie movie) {
        SharedPreferences prefs = context.getSharedPreferences("gio_hang", MODE_PRIVATE);
        Set<String> ids = new HashSet<>(prefs.getStringSet("movie_ids", new HashSet<>()));
        ids.add(String.valueOf(movie.getId()));
        prefs.edit().putStringSet("movie_ids", ids).apply();
    }

    private void updateTotalPrice() {
        int total = 0;
        for (giohangticket ticket : ticketList) {
            if (ticket.isSelected()) {
                total += Integer.parseInt(ticket.getPrice());
            }
        }
        txtTotalPrice.setText("Tổng tiền: " + total + "đ");
    }
}

