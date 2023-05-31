package com.example.bloomedflowers.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.ActivityUpdatePasswordBinding;
import com.example.bloomedflowers.model.Users;
import com.example.bloomedflowers.sql_db.DatabaseHelper;
import com.example.bloomedflowers.user.UserActivity;

public class UpdatePasswordActivity extends AppCompatActivity {

    ActivityUpdatePasswordBinding updatePasswordBinding;
    DatabaseHelper databaseHelper;
    String getEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updatePasswordBinding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        setContentView(updatePasswordBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        if (getIntent().getExtras() != null){
            getEmail = getIntent().getStringExtra("email");
        }

        updatePasswordBinding.cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        updatePasswordBinding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = updatePasswordBinding.password.getText().toString().trim();
                String conPassword = updatePasswordBinding.conPassword.getText().toString().trim();
                if (password.isEmpty()){
                    showMessage("Please enter password");
                }else if (conPassword.isEmpty()){
                    showMessage("Please enter confirm password");
                }else if (!password.contentEquals(conPassword)){
                    showMessage("Password must be matched");
                }else{
                    Users users = new Users(getEmail, password);
                    databaseHelper.updatePassword(users);
                    showMessage("Password Updated Successfully");
                    startActivity(new Intent(UpdatePasswordActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}