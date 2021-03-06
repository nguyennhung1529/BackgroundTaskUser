package com.example.neu_simplebackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neu_simplebackgroundtask.model.User;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();
        TextView mID = findViewById(R.id.id);
        TextView mName = findViewById(R.id.name);
        TextView mEmail = findViewById(R.id.email);
        TextView mGender = findViewById(R.id.gender);
        TextView mStatus = findViewById(R.id.status);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        User user = (User) bundle.get("object_user");
        mID.setText(String.valueOf(user.getId()));
        mName.setText(user.getName());
        mEmail.setText(user.getEmail());
        mGender.setText(user.getGender());
        mStatus.setText(user.getStatus());
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}