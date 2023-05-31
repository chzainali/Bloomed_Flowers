package com.example.bloomedflowers.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.ActivityAddBinding;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding addBinding;
    String name, type, season, growth, methodOfCare;
    int PICK_IMAGE_GALLERY = 124;
    String strImageURI = "";
    Uri imageUri ;
    DatabaseHelper databaseHelper;
    Flowers flowers;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(addBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        if (getIntent().getExtras() != null){
            flowers = (Flowers) getIntent().getSerializableExtra("data");
            addBinding.flowerName.setText(flowers.getName());
            addBinding.flowerType.setText(flowers.getType());
            addBinding.flowerSeason.setText(flowers.getSeason());
            addBinding.flowerGrowthRate.setText(flowers.getGrowthRate());
            addBinding.flowerMethodOfCare.setText(flowers.getMethodOfCare());
            strImageURI = flowers.getImage();
            Glide.with(this).load(flowers.getImage()).into(addBinding.imageView);
            addBinding.add.setText("Edit");
            addBinding.label.setText("Edit Flower Details");
            addBinding.rlGone.setVisibility(View.GONE);
        }

        addBinding.cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        addBinding.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICK_IMAGE_GALLERY);
            }
        });

        addBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    if (getIntent().getExtras() != null){
                        Flowers flowers2 = new Flowers(flowers.getId(), strImageURI, name, type, season, growth, methodOfCare);
                        databaseHelper.updateFlower(flowers2);
                        showMessage("Updated Successfully");
                        startActivity(new Intent(AddActivity.this, AdminActivity.class));
                        finish();
                    }else{
                        Flowers flowers = new Flowers(strImageURI, name, type, season, growth, methodOfCare);
                        databaseHelper.addFlower(flowers);
                        showMessage("Added Successfully");
                        finish();
                    }
                }
            }
        });

    }

    private Boolean isValidated(){

        name = addBinding.flowerName.getText().toString();
        type = addBinding.flowerType.getText().toString();
        season = addBinding.flowerSeason.getText().toString();
        growth = addBinding.flowerGrowthRate.getText().toString();
        methodOfCare = addBinding.flowerMethodOfCare.getText().toString();

        if (strImageURI.isEmpty()){
            showMessage("Please pick image");
            return false;
        }

        if (name.isEmpty()){
            showMessage("Please enter name");
            return false;
        }

        if (type.isEmpty()){
            showMessage("Please enter type");
            return false;
        }

        if (season.isEmpty()){
            showMessage("Please enter season");
            return false;
        }

        if (growth.isEmpty()){
            showMessage("Please enter growth rate");
            return false;
        }

        if (methodOfCare.isEmpty()){
            showMessage("Please enter method of care");
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK && data.getData() != null) {
            imageUri = data.getData();
            strImageURI = imageUri.toString();
            try {
                addBinding.rlGone.setVisibility(View.GONE);
                addBinding.imageView.setVisibility(View.VISIBLE);
                Bitmap bitmap =
                        MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                addBinding.imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}