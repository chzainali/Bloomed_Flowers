package com.example.bloomedflowers.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.ActivityFlowerDetailsBinding;
import com.example.bloomedflowers.databinding.ActivityFlowersBinding;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

public class FlowerDetailsActivity extends AppCompatActivity {

    ActivityFlowerDetailsBinding detailsBinding;
    Flowers flowers;
    DatabaseHelper databaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = ActivityFlowerDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        detailsBinding.cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


            flowers = (Flowers) getIntent().getSerializableExtra("data");
            detailsBinding.name.setText(flowers.getName());
            detailsBinding.type.setText(flowers.getType());
            detailsBinding.season.setText(flowers.getSeason());
            detailsBinding.growthRate.setText(flowers.getGrowthRate()+"%");
            detailsBinding.methodOfCare.setText(flowers.getMethodOfCare());
            Glide.with(this).load(flowers.getImage()).into(detailsBinding.imageView);

            detailsBinding.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FlowerDetailsActivity.this, AddActivity.class);
                    intent.putExtra("data", flowers);
                    startActivity(intent);
                }
            });

            detailsBinding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHelper.deleteFlower(flowers.getId());
                    Toast.makeText(FlowerDetailsActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }

    }