package com.example.btl.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.btl.R;

import java.util.ArrayList;

public class ManageEventActivity extends AppCompatActivity {

    private ListView lvEvents;
    private Button btnAddEvent;
    private ArrayAdapter<String> adapter;
    private Uri selectedImageUri;
    private ImageView imgPreview;
    private final int PICK_IMAGE_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_event);

        lvEvents = findViewById(R.id.lvEvents);
        btnAddEvent = findViewById(R.id.btnAddEvent);

        btnAddEvent.setOnClickListener(v -> showAddEventDialog());


    }


    private void showAddEventDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null);
        EditText edtName = view.findViewById(R.id.edtEventName);
        imgPreview = view.findViewById(R.id.imgEventPreview);
        Button btnChooseImg = view.findViewById(R.id.btnChooseImage);

        btnChooseImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Thêm sự kiện")
                .setView(view)
                .setPositiveButton("Thêm", (d, w) -> {
                    String name = edtName.getText().toString().trim();
                    if (name.isEmpty() || selectedImageUri == null) {
                        Toast.makeText(this, "Hãy nhập tên và chọn ảnh", Toast.LENGTH_SHORT).show();
                        return;
                    }

                })
                .setNegativeButton("Hủy", null)
                .create();

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            if (imgPreview != null) {
                Glide.with(this).load(selectedImageUri).into(imgPreview);
            }
        }
    }

}
