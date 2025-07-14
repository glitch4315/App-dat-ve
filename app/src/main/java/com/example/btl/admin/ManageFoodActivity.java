package com.example.btl.admin;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl.R;

import java.util.ArrayList;

public class ManageFoodActivity extends AppCompatActivity {

    private ListView lvFood;
    private TextView txtTotalQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food);

        lvFood = findViewById(R.id.lvFood);
        txtTotalQuantity = findViewById(R.id.txtTotalQuantity);

    }
}
