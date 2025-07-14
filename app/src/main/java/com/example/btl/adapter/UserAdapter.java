package com.example.btl.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.btl.R;
import com.example.btl.admin.ManageUsersActivity;
import com.example.btl.employeedao.forusers;
import com.example.btl.model.User;


import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> userList;
    private forusers userDao;

    public UserAdapter(Context context, List<User> users, forusers dao) {
        super(context, 0, users);
        this.context = context;
        this.userList = users;
        this.userDao = dao;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = userList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_users, parent, false);
        }

        TextView txtEmail = convertView.findViewById(R.id.txtEmail);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        txtEmail.setText(user.getEmail());

        btnEdit.setOnClickListener(v -> {
            if (context instanceof ManageUsersActivity) {
                ((ManageUsersActivity) context).showEditUserDialog(user.getId());
            }
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xoá người dùng")
                    .setMessage("Bạn có chắc muốn xoá người dùng này không?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        userDao.deleteUser(user.getId());
                        userList.clear();
                        userList.addAll(userDao.getAllUserObjects());
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });

        return convertView;
    }
}

