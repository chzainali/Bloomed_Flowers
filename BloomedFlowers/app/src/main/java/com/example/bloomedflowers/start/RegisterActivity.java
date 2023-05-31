package com.example.bloomedflowers.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.bloomedflowers.databinding.ActivityRegisterBinding;
import com.example.bloomedflowers.model.Users;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding registerBinding;
    String name, email, phone, password;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        registerBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    Users users = new Users(name, email, phone, password);
                    databaseHelper.register(users);
                    showMessage("Successfully Registered");
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

    }

    private Boolean isValidated(){
        name = registerBinding.name.getText().toString().trim();
        email = registerBinding.email.getText().toString().trim();
        phone = registerBinding.phone.getText().toString().trim();
        password = registerBinding.password.getText().toString().trim();

        if (name.isEmpty()){
            showMessage("Please enter name");
            return false;
        }
        if (email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
        }
        if (phone.isEmpty()){
            showMessage("Please enter phone");
            return false;
        }
        if (password.isEmpty()){
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}