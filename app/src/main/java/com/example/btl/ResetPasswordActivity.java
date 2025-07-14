package com.example.btl;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.employeedao.forusers;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnReset;
    private Button btnBackToLogin;

    private forusers forusers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmail = findViewById(R.id.edtEmail);
        btnReset = findViewById(R.id.btnReset);
        btnBackToLogin = findViewById(R.id.btnBackToLogin); // thêm dòng này

        forusers = new forusers(this);

        btnReset.setOnClickListener(v -> {
            // logic gửi email hoặc kiểm tra DB
        });

        btnBackToLogin.setOnClickListener(v -> {
            // Quay lại màn hình login
            finish(); // nếu chỉ quay lại Activity trước đó
            // hoặc:
            // startActivity(new Intent(this, LoginActivity.class));
        });
    }
}