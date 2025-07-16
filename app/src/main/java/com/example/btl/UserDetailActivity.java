package com.example.btl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.employeedao.forusers;
import com.example.btl.model.User;

public class UserDetailActivity extends AppCompatActivity {

    EditText edtName, edtPhone;
    TextView txtEmail;
    Button btnSave;
    ProgressBar progressBar;

    forusers userDao;
    int userId;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);

        userDao = new forusers(this);

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserInfo();

        btnSave.setOnClickListener(v -> updateUserInfo());
    }

    private void loadUserInfo() {
        progressBar.setVisibility(View.VISIBLE);
        currentUser = userDao.getUserById(userId);
        progressBar.setVisibility(View.GONE);

        if (currentUser != null) {
            edtName.setText(currentUser.getName());
            edtPhone.setText(currentUser.getPhone());
            txtEmail.setText(currentUser.getEmail());
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateUserInfo() {
        String newName = edtName.getText().toString().trim();
        String newPhone = edtPhone.getText().toString().trim();

        if (newName.isEmpty()) {
            edtName.setError("Vui lòng nhập họ tên");
            edtName.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        boolean updated = userDao.updateUser(
                userId,
                newName,
                currentUser.getEmail(),
                newPhone,
                currentUser.getDob(),
                currentUser.getPassword()
        );

        progressBar.setVisibility(View.GONE);

        if (updated) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
