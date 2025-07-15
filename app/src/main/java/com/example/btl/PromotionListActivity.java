package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.adapter.PromotionAdapter;
import com.example.btl.model.EventPromotion;
import com.example.btl.sqlite.DBHelper;

import java.util.List;

public class PromotionListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PromotionAdapter adapter;
    private DBHelper db;
    private List<EventPromotion> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);

        recyclerView = findViewById(R.id.recyclerViewPromotions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DBHelper(this);
        list = db.getAllPromotions();

        adapter = new PromotionAdapter(this, list, new PromotionAdapter.OnItemClick() {
            @Override
            public void onEdit(EventPromotion p) {
                // Mở form sửa, truyền id promotion để load dữ liệu lên form
                Intent intent = new Intent(PromotionListActivity.this, com.example.btl.PromotionFormActivity.class);
                intent.putExtra("id", p.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(EventPromotion p) {
                // Xoá khỏi database
                int result = db.deletePromotion(p.getId());
                if (result > 0) {
                    // Xoá khỏi list hiện tại và refresh adapter
                    list.remove(p);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(PromotionListActivity.this, "Đã xoá sự kiện", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PromotionListActivity.this, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tải lại danh sách khi trở lại activity (ví dụ sau khi sửa hoặc thêm mới)
        list.clear();
        list.addAll(db.getAllPromotions());
        adapter.notifyDataSetChanged();
    }
}
