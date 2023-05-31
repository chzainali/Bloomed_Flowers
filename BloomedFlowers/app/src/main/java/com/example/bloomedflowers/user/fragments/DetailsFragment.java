package com.example.bloomedflowers.user.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.FragmentDetailsBinding;
import com.example.bloomedflowers.model.Favorites;
import com.example.bloomedflowers.model.Flowers;

public class DetailsFragment extends Fragment {

    FragmentDetailsBinding detailsBinding;
    Flowers flowers;
    Favorites favorites;
    String checkPrevious;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkPrevious = getArguments().getString("check");
            if (checkPrevious.equals("Home")){
                flowers = (Flowers) getArguments().getSerializable("data");
            }else{
                favorites = (Favorites) getArguments().getSerializable("data");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        detailsBinding =  FragmentDetailsBinding.inflate(getLayoutInflater());

        return detailsBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsBinding.cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        if (checkPrevious.equals("Home")){
            detailsBinding.name.setText(flowers.getName());
            detailsBinding.type.setText(flowers.getType());
            detailsBinding.season.setText(flowers.getSeason());
            detailsBinding.growthRate.setText(flowers.getGrowthRate()+"%");
            detailsBinding.methodOfCare.setText(flowers.getMethodOfCare());
            Glide.with(this).load(flowers.getImage()).into(detailsBinding.imageView);
        }else{
            detailsBinding.name.setText(favorites.getName());
            detailsBinding.type.setText(favorites.getType());
            detailsBinding.season.setText(favorites.getSeason());
            detailsBinding.growthRate.setText(favorites.getGrowthRate()+"%");
            detailsBinding.methodOfCare.setText(favorites.getMethodOfCare());
            Glide.with(this).load(favorites.getImage()).into(detailsBinding.imageView);
        }

    }
}