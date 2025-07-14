package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.employeedao.forusers;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPhone, edtPassword;
    private Button btnRegister, btnBackToLogin;
    private ProgressBar progressBar;
    private forusers forusers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        progressBar = findViewById(R.id.progressBar);

        forusers = new forusers(this);

        btnRegister.setOnClickListener(v -> registerUser());

        btnBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String dob = ""; // nếu chưa nhập ngày sinh, bạn có thể để trống

        if (!validateInput(name, email, phone, password)) return;

        showLoading(true);

        if (forusers.checkEmailExists(email)) {
            showLoading(false);
            Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = forusers.insertUser(name, email, phone, dob, password);
        showLoading(false);

        if (result != -1) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi đăng ký người dùng", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateInput(String name, String email, String phone, String password) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phone.matches("\\d{10}")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnRegister.setEnabled(!isLoading);
    }
}
