package com.example.btl.admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.adapter.RevenueAdapter;
import com.example.btl.employeedao.forticket;
import com.example.btl.model.giohangticket;

import java.util.List;

public class RevenueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtTotalRevenue;
    private RevenueAdapter adapter;
    private forticket dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        recyclerView = findViewById(R.id.recyclerViewRevenue);
        txtTotalRevenue = findViewById(R.id.txtTotalRevenue);

        dao = new forticket(this);
        List<giohangticket> ticketList = dao.getAllTickets();

        adapter = new RevenueAdapter(this, ticketList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        int total = 0;
        for (giohangticket ticket : ticketList) {
            try {
                total += Integer.parseInt(ticket.getPrice());
            } catch (NumberFormatException ignored) {}
        }

        txtTotalRevenue.setText("Tổng doanh thu: " + total + "đ");
    }
}
