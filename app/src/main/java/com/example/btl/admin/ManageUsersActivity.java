package com.example.btl.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.btl.adapter.UserAdapter;
import com.example.btl.employeedao.forusers;
import com.example.btl.model.User;

import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private Button btnAddUser;
    private forusers userDao;
    private List<User> userList;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_users);

        listViewUsers = findViewById(R.id.listViewUsers);
        userDao = new forusers(this);

        loadUserList();
    }

    private void loadUserList() {
        userList = userDao.getAllUserObjects();
        adapter = new UserAdapter(this, userList, userDao);
        listViewUsers.setAdapter(adapter);
    }

    private void reloadUserList() {
        userList.clear();
        userList.addAll(userDao.getAllUserObjects());
        adapter.notifyDataSetChanged();
    }

    private void showAddUserDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null);

        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtEmail = view.findViewById(R.id.edtEmail);
        EditText edtPassword = view.findViewById(R.id.edtPassword);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        EditText edtDOB = view.findViewById(R.id.edtDOB);

        new AlertDialog.Builder(this)
                .setTitle("Thêm người dùng")
                .setView(view)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = edtName.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();
                    String phone = edtPhone.getText().toString().trim();
                    String dob = edtDOB.getText().toString().trim();

                    if (!validateInput(name, email, password, phone, dob)) {
                        Toast.makeText(this, "Vui lòng nhập đúng và đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (userDao.isEmailExists(email)) {
                        Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    userDao.insertUser(name, email, phone, dob, password);
                    reloadUserList();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    public void showEditUserDialog(int userId) {
        User user = userDao.getUserById(userId);
        if (user == null) return;

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtEmail = view.findViewById(R.id.edtEmail);
        EditText edtPassword = view.findViewById(R.id.edtPassword);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        EditText edtDOB = view.findViewById(R.id.edtDOB);

        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtDOB.setText(user.getDob());
        edtPassword.setText(user.getPassword()); // Nếu cần

        new AlertDialog.Builder(this)
                .setTitle("Sửa người dùng")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String newName = edtName.getText().toString().trim();
                    String newEmail = edtEmail.getText().toString().trim();
                    String newPassword = edtPassword.getText().toString().trim();
                    String newPhone = edtPhone.getText().toString().trim();
                    String newDOB = edtDOB.getText().toString().trim();

                    if (!validateInput(newName, newEmail, newPassword, newPhone, newDOB)) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ và hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    userDao.updateUser(userId, newName, newEmail, newPhone, newDOB, newPassword);
                    reloadUserList();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


    private boolean validateInput(String name, String email, String password, String phone, String dob) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || dob.isEmpty()) {
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        if (password.length() < 6) {
            return false;
        }
        if (!phone.matches("\\d{10}")) {
            return false;
        }
        if (!dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        return true;
    }
}
