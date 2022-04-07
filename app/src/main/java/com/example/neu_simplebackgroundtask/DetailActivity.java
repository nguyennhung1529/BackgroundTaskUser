package com.example.neu_simplebackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mID = findViewById(R.id.id);
        TextView mName = findViewById(R.id.name);
        TextView mEmail = findViewById(R.id.email);
        TextView mGender = findViewById(R.id.gender);
        TextView mStatus = findViewById(R.id.status);

        Intent intent = getIntent();
        // Lay DL tu intent
        String dtaUserString = intent.getStringExtra("user");
        // Do DL vao Activity
        try {
            JSONObject user = new JSONObject(dtaUserString);
            // mUser.setText(user.getString("name"));
            mID.setText(user.getString("id"));
            mName.setText(user.getString("name"));
            mEmail.setText(user.getString("email"));
            mGender.setText(user.getString("gender"));
            mStatus.setText(user.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}