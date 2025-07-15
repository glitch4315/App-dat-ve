package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.btl.model.giohangticket;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnConfirmPayment;
    private TextView txtTotalPayment;
    private EditText edtHoTen, edtPhone, edtEmail;
    private RadioGroup radioGroupMethod;

    private int totalPrice = 0;
    private ArrayList<giohangticket> selectedTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);

        edtHoTen = findViewById(R.id.edtHoTen);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        txtTotalPayment = findViewById(R.id.txtTotalPayment);
        radioGroupMethod = findViewById(R.id.paymentMethodGroup);
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);


        totalPrice = getIntent().getIntExtra("total", 0);
        selectedTickets = (ArrayList<giohangticket>) getIntent().getSerializableExtra("selectedTickets");

        txtTotalPayment.setText("Tổng tiền: " + totalPrice + "đ");


        btnConfirmPayment.setOnClickListener(view -> {
            String hoTen = edtHoTen.getText().toString().trim();
            String soDienThoai = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            int selectedMethodId = -1;
            for (int i = 0; i < radioGroupMethod.getChildCount(); i++) {
                View child = radioGroupMethod.getChildAt(i);
                if (child instanceof LinearLayout) {
                    LinearLayout optionLayout = (LinearLayout) child;
                    for (int j = 0; j < optionLayout.getChildCount(); j++) {
                        View subChild = optionLayout.getChildAt(j);
                        if (subChild instanceof RadioButton) {
                            RadioButton rb = (RadioButton) subChild;
                            if (rb.isChecked()) {
                                selectedMethodId = rb.getId();
                                break;
                            }
                        }
                    }
                }
            }


            if (hoTen.isEmpty() || soDienThoai.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.contains("@") ||
                    !hoTen.matches("[a-zA-Z\\p{L} ]+") ||
                    soDienThoai.length() != 10 ||
                    !soDienThoai.matches("\\d+")) {
                Toast.makeText(this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedMethodId == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutThanhToan), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
