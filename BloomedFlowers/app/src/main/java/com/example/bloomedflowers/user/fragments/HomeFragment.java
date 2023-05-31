package com.example.bloomedflowers.user.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloomedflowers.R;
import com.example.bloomedflowers.adapter.FlowersAdapter;
import com.example.bloomedflowers.databinding.FragmentHomeBinding;
import com.example.bloomedflowers.model.Favorites;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding homeBinding;
    FlowersAdapter adapter;
    DatabaseHelper databaseHelper;
    List<Flowers> list = new ArrayList<>();
    public static List<Favorites> favoriteList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(getLayoutInflater());

        databaseHelper = new DatabaseHelper(requireContext());

        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();

        list.clear();
        favoriteList.clear();
        list.addAll(databaseHelper.getAllFlowers());
        favoriteList.addAll(databaseHelper.getAllFavorites());
        if (list.size()>0){
            homeBinding.noDataFound.setVisibility(View.GONE);
            homeBinding.recyclerView.setVisibility(View.VISIBLE);
            homeBinding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            adapter = new FlowersAdapter(list, requireContext(), "user");
            homeBinding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            homeBinding.noDataFound.setVisibility(View.VISIBLE);
            homeBinding.recyclerView.setVisibility(View.GONE);
        }

    }

}