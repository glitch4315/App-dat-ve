package com.example.btl.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.adapter.PromotionAdapter;
import com.example.btl.model.EventPromotion;
import com.example.btl.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ManageEventActivity extends AppCompatActivity {

    private RecyclerView rvPromotions;
    private PromotionAdapter adapter;
    private List<EventPromotion> promotionList;
    private DBHelper db;

    // Dialog các control
    private AlertDialog addEditDialog;
    private EditText edtTitle, edtDescription;
    private ImageView imgPreview;
    private Uri selectedImageUri;
    private EventPromotion editingPromotion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_event);

        db = new DBHelper(this);

        rvPromotions = findViewById(R.id.rvPromotions);
        promotionList = new ArrayList<>();
        adapter = new PromotionAdapter(this, promotionList, new PromotionAdapter.OnItemClick() {
            @Override
            public void onEdit(EventPromotion p) {
                showAddEditDialog(p);
            }

            @Override
            public void onDelete(EventPromotion p) {
                confirmDelete(p);
            }
        });
        rvPromotions.setLayoutManager(new LinearLayoutManager(this));
        rvPromotions.setAdapter(adapter);

        findViewById(R.id.btnAddPromotion).setOnClickListener(v -> showAddEditDialog(null));

        loadPromotions();
    }

    private void loadPromotions() {
        promotionList.clear();
        promotionList.addAll(db.getAllPromotions());
        adapter.notifyDataSetChanged();
    }

    private void confirmDelete(EventPromotion p) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa khuyến mãi")
                .setMessage("Bạn có chắc muốn xóa khuyến mãi này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int deleted = db.deletePromotion(p.getId());
                    if (deleted > 0) {
                        Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                        loadPromotions();
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showAddEditDialog(@Nullable EventPromotion promotion) {
        editingPromotion = promotion;
        selectedImageUri = null;

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null);
        edtTitle = dialogView.findViewById(R.id.edtTitle);
        edtDescription = dialogView.findViewById(R.id.edtDescription);
        imgPreview = dialogView.findViewById(R.id.imgPreview);
        Button btnChooseImage = dialogView.findViewById(R.id.btnChooseImage);

        if (promotion != null) {
            edtTitle.setText(promotion.getTitle());
            edtDescription.setText(promotion.getDescription());
            if (promotion.getImageUrl() != null && !promotion.getImageUrl().isEmpty()) {
                Glide.with(this).load(promotion.getImageUrl()).into(imgPreview);
            }
        } else {
            imgPreview.setImageResource(android.R.color.darker_gray);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(promotion == null ? "Thêm khuyến mãi" : "Sửa khuyến mãi")
                .setView(dialogView)
                .setPositiveButton("Lưu", null)  // override sau
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        addEditDialog = builder.create();

        addEditDialog.setOnShowListener(dialog -> {
            Button btnSave = addEditDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btnSave.setOnClickListener(v -> {
                String title = edtTitle.getText().toString().trim();
                String desc = edtDescription.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (promotion == null) {
                    EventPromotion newPromotion = new EventPromotion(0, title, desc);
                    long id = db.addPromotion(newPromotion);
                    if (id > 0) {
                        Toast.makeText(this, "Đã thêm khuyến mãi", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    promotion.setTitle(title);
                    promotion.setDescription(desc);

                    int updated = db.updatePromotion(promotion);
                    if (updated > 0) {
                        Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                addEditDialog.dismiss();
                loadPromotions();
            });
        });

        addEditDialog.show();
    }
}
