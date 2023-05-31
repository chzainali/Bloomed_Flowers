package com.example.bloomedflowers.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.bloomedflowers.R;
import com.example.bloomedflowers.adapter.FlowersAdapter;
import com.example.bloomedflowers.databinding.ActivityAddBinding;
import com.example.bloomedflowers.databinding.ActivityFlowersBinding;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FlowersActivity extends AppCompatActivity {

    ActivityFlowersBinding flowersBinding;
    FlowersAdapter adapter;
    DatabaseHelper databaseHelper;
    List<Flowers> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flowersBinding = ActivityFlowersBinding.inflate(getLayoutInflater());
        setContentView(flowersBinding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        flowersBinding.cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        list.clear();
        list.addAll(databaseHelper.getAllFlowers());
        if (list.size()>0){
            flowersBinding.noDataFound.setVisibility(View.GONE);
            flowersBinding.recyclerView.setVisibility(View.VISIBLE);
            flowersBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new FlowersAdapter(list, this, "admin");
            flowersBinding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            flowersBinding.noDataFound.setVisibility(View.VISIBLE);
            flowersBinding.recyclerView.setVisibility(View.GONE);
        }

    }
}