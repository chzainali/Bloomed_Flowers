package com.example.bloomedflowers.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.ActivityForgotPasswordBinding;
import com.example.bloomedflowers.model.HelperClass;
import com.example.bloomedflowers.model.Users;
import com.example.bloomedflowers.sql_db.DatabaseHelper;
import com.example.bloomedflowers.user.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding forgotPasswordBinding;
    DatabaseHelper databaseHelper;
    List<Users> list = new ArrayList<>();
    Boolean checkEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(forgotPasswordBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        forgotPasswordBinding.cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        forgotPasswordBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgotPasswordBinding.email.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }else if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email in correct format", Toast.LENGTH_SHORT).show();
                }else{
                    list = databaseHelper.getAllUsers();
                    for (Users users:list){
                        if(email.equals(users.getEmail())){
                            checkEmail = true;
                            Intent intent = new Intent(ForgotPasswordActivity.this, UpdatePasswordActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            break;
                        }
                    }
                    if (!checkEmail){
                        Toast.makeText(ForgotPasswordActivity.this, "Email doesn't Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}