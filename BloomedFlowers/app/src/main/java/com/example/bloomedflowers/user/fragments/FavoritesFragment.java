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
import com.example.bloomedflowers.adapter.FavoritesAdapter;
import com.example.bloomedflowers.adapter.FlowersAdapter;
import com.example.bloomedflowers.databinding.FragmentFavoritesBinding;
import com.example.bloomedflowers.model.Favorites;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    FragmentFavoritesBinding favoritesBinding;
    FavoritesAdapter adapter;
    DatabaseHelper databaseHelper;
    List<Favorites> list = new ArrayList<>();

    public FavoritesFragment() {
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
        favoritesBinding = FragmentFavoritesBinding.inflate(getLayoutInflater());

        databaseHelper = new DatabaseHelper(requireContext());

        return favoritesBinding.getRoot();
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
        list.addAll(databaseHelper.getAllFavorites());
        if (list.size()>0){
            favoritesBinding.noDataFound.setVisibility(View.GONE);
            favoritesBinding.recyclerView.setVisibility(View.VISIBLE);
            favoritesBinding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            adapter = new FavoritesAdapter(list, requireContext());
            favoritesBinding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            favoritesBinding.noDataFound.setVisibility(View.VISIBLE);
            favoritesBinding.recyclerView.setVisibility(View.GONE);
        }

    }

}