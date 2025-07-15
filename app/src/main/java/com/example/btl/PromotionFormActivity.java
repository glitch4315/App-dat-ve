package com.example.btl;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.model.EventPromotion;
import com.example.btl.sqlite.DBHelper;

public class PromotionFormActivity extends AppCompatActivity {

    EditText etTitle, etDesc, etImg;
    DBHelper db;
    int promoId = -1;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_promotion_form);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDescription);
        db = new DBHelper(this);

        if (getIntent() != null) {
            promoId = getIntent().getIntExtra("id", -1);
            if (promoId != -1) loadToForm();
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
    }

    void loadToForm() {
        for (EventPromotion p : db.getAllPromotions()) {
            if (p.getId() == promoId) {
                etTitle.setText(p.getTitle());
                etDesc.setText(p.getDescription());
                etImg.setText(p.getImageUrl());
                break;
            }
        }
    }

    void save() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String img = etImg.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Vui lòng nhập tiêu đề");
            return;
        }

        EventPromotion p = new EventPromotion(promoId, title, desc);
        if (promoId == -1) db.addPromotion(p);
        else db.updatePromotion(p);

        finish();
    }
}
