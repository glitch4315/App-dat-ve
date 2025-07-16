package com.example.btl.adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;
import android.app.AlertDialog;


import com.example.btl.R;
import com.example.btl.employeedao.forusers;
import com.example.btl.model.User;
import com.example.btl.admin.ManageUsersActivity;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private forusers userDao;

    public UserAdapter(Context context, List<User> userList, forusers userDao) {
        this.context = context;
        this.userList = userList;
        this.userDao = userDao;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return userList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_users, parent, false);

        TextView txtName = view.findViewById(R.id.txtUserName);
        TextView txtEmail = view.findViewById(R.id.txtUserEmail);
        TextView txtPhone = view.findViewById(R.id.txtUserPhone);
        Button btnEdit = view.findViewById(R.id.btnEditUser);
        Button btnDelete = view.findViewById(R.id.btnDeleteUser);

        User user = userList.get(i);

        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone());

        btnEdit.setOnClickListener(v -> {
            if (context instanceof ManageUsersActivity) {
                ((ManageUsersActivity) context).showEditUserDialog(user.getId());
            }
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc muốn xoá người dùng này?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        userDao.deleteUser(user.getId());
                        userList.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xoá người dùng", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        return view;
    }
}
