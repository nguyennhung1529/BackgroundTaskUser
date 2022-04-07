package com.example.neu_simplebackgroundtask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.neu_simplebackgroundtask.adapter.UserAdapter;
import com.example.neu_simplebackgroundtask.model.User;
import com.example.neu_simplebackgroundtask.my_interface.IClickItemUserListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private ArrayList<User> userArrayList;

    private EditText edtName, edtEmail;
    private RadioButton radMale, radFemale;
    private CheckBox chkStatus;

    private FloatingActionButton btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddUser = findViewById(R.id.btn_add_user);
        userArrayList = new ArrayList<>();
        // Lay DL danh sach User
        ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userArrayList.addAll(response.body());
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                displayToast("Error:" + t.getMessage());
            }
        });

        // Do set DL vao RecycleView
        rcvUser = findViewById(R.id.rcv_user);
        // Set LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);
        // Set ItemDecoration
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);
        // Set Adapter
        userAdapter = new UserAdapter(userArrayList, new IClickItemUserListener() {
            @Override
            public void getDetailUser(User user) {
                onClickGoToDetail(user);
            }

            @Override
            public void updateUser(int id, User user) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("UPDATE");
                builder.setMessage("Do you really want to update this user?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateUserExec(id, user);
                    }
                });
                builder.create();
                builder.show();
            }

            @Override
            public void deleteUser(int id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("DELETE");
                builder.setMessage("Do you really want to delete this user?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteUserExec(id);
                    }
                });
                builder.create();
                builder.show();
            }
        });
        rcvUser.setAdapter(userAdapter);

        // Su kien them moi User
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View addUserView = getLayoutInflater().inflate(R.layout.add_user_layout, null);
                edtName = addUserView.findViewById(R.id.name);
                edtEmail = addUserView.findViewById(R.id.email);
                radMale = addUserView.findViewById(R.id.rad_mail);
                radFemale = addUserView.findViewById(R.id.rad_femail);
                chkStatus = addUserView.findViewById(R.id.chk_status);

                builder.setView(addUserView);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Them moi ban ghi
                        // - Lay DL
                        String name = edtName.getText().toString().trim();
                        String email = edtEmail.getText().toString().trim();
                        String gender = (radMale.isChecked()) ? "male" : "female";
                        String status = (chkStatus.isChecked()) ? "active" : "inactive";
                        // - Push vao user
                        User user = new User(name, email, gender, status);
                        // - Goi API insert
                        ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                userArrayList.add(response.body());
                                displayToast("Insert success!");
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                displayToast("Error:" + t.getMessage());
                            }
                        });
                    }
                });
            }
        });
    }

    private void updateUserExec(int id, User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View updateUserView = getLayoutInflater().inflate(R.layout.update_user_layout, null);
        edtName = updateUserView.findViewById(R.id.name);
        edtEmail = updateUserView.findViewById(R.id.email);
        radMale = updateUserView.findViewById(R.id.rad_mail);
        radFemale = updateUserView.findViewById(R.id.rad_femail);
        chkStatus = updateUserView.findViewById(R.id.chk_status);

        // Do DL cu vao Activity
        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());

        if (user.getGender().equals("male"))
            radMale.setChecked(true);
        else
            radFemale.setChecked(true);

        if (user.getStatus().equals("active")) chkStatus.setChecked(true);

        builder.setView(updateUserView);
        builder.setNegativeButton("Cancel", null);
        // Cap nhat DL moi
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lay DL cap nhat
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String gender = (radMale.isChecked()) ? "male" : "female";
                String status = (chkStatus.isChecked()) ? "active" : "inactive";
                // Push vao user
                User user = new User(name, email, gender, status);
                // Goi API update
                ApiClient.getAPI().updateUser(id, user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        // Hien thi thong bao thanh cong
                        displayToast("Update success!");
                        // Refresh lai Activity
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        displayToast("Error:" + t.getMessage());
                    }
                });

            }
        });

    }

    private void deleteUserExec(int id) {
        ApiClient.getAPI().deleteUser(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Hien thi thong bao thanh cong
                displayToast("Delete success!");
                // Refresh lai Activity
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                displayToast("Error:" + t.getMessage());
            }
        });
    }


    private void onClickGoToDetail(User user) {
        Intent intent = new Intent(this, DetailActivity.class);
        // Put DL vao bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", user);
        // Put extra vao bundle
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}