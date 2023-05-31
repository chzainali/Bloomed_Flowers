package com.example.bloomedflowers.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.bloomedflowers.MainActivity;
import com.example.bloomedflowers.admin.AdminActivity;
import com.example.bloomedflowers.databinding.ActivityLoginBinding;
import com.example.bloomedflowers.model.HelperClass;
import com.example.bloomedflowers.model.Users;
import com.example.bloomedflowers.sql_db.DatabaseHelper;
import com.example.bloomedflowers.user.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    String email, password;
    DatabaseHelper databaseHelper;
    List<Users> list = new ArrayList<>();
    Boolean checkDetails = false;
    String managerEmail = "manager@gmail.com";
    String managerPassword = "manager@123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        loginBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginBinding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        loginBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    if (email.equals(managerEmail) && password.equals(managerPassword)){
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        finish();
                    }else{
                        list = databaseHelper.getAllUsers();
                        for (Users users:list){
                            if(email.equals(users.getEmail()) && password.equals(users.getPassword())){
                                checkDetails = true;
                                showMessage("Successfully Login");
                                HelperClass.users =users;
                                startActivity(new Intent(LoginActivity.this, UserActivity.class));
                                finish();
                                break;
                            }
                        }
                        if (!checkDetails){
                            showMessage("Wrong Credentials...\nPlease check email or password");
                        }
                    }
                }
            }
        });

    }

    private Boolean isValidated(){
        email = loginBinding.email.getText().toString().trim();
        password = loginBinding.password.getText().toString().trim();

        if (email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
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