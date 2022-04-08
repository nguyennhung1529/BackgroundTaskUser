package com.example.neu_simplebackgroundtask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neu_simplebackgroundtask.R;
import com.example.neu_simplebackgroundtask.model.User;
import com.example.neu_simplebackgroundtask.my_interface.IClickItemUserListener;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHoler> {

    private ArrayList<User> mListUser; // Khai bao list cac du lieu
    private IClickItemUserListener iClickItemUserListener; // Khai bao interface

    // Ham khoi tao constructor cua UserAdapter
    public UserAdapter(ArrayList<User> mListUser, IClickItemUserListener listener) {
        this.mListUser = mListUser;
        this.iClickItemUserListener = listener;
    }

    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tao view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
        // Blind DL len List
        User user = mListUser.get(position);
        if (user == null) {
            return;
        }

        // Set DL len view holder
        holder.tvId.setText(String.valueOf(user.getId()));
        holder.tvName.setText(user.getName());

        // Bat su kien khi click vao 1 item => chuyen qua activity DetailUser
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thuc hien xu ly logic gian tiep bang cach call back
                // ra ben ngoai MainActivity
                iClickItemUserListener.getDetailUser(user);
            }
        });
        // Bat su kien khi click vao btn Xoa
        holder.ivBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemUserListener.deleteUser(user.getId());
            }
        });
        // Bat su kien khi click vao btn edit
        holder.ivBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemUserListener.updateUser(user.getId(), user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHoler extends RecyclerView.ViewHolder {
        // Khai bao nhung thanh phan co trong Layout Item
        private TextView tvId, tvName;
        private LinearLayout layoutItem;
        private ImageView ivBtnEdit, ivBtnDelete;

        public UserViewHoler(@NonNull View itemView) {
            super(itemView);
            // Anh xa cac view
            layoutItem = itemView.findViewById(R.id.layout_item_user);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            ivBtnEdit = itemView.findViewById(R.id.iv_edit);
            ivBtnDelete = itemView.findViewById(R.id.iv_delete);
        }

    }
}
