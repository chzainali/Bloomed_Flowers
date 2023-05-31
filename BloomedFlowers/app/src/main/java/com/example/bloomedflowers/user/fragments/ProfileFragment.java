package com.example.bloomedflowers.user.fragments;

import static com.example.bloomedflowers.user.UserActivity.isPlaying;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.FragmentProfileBinding;
import com.example.bloomedflowers.model.HelperClass;
import com.example.bloomedflowers.service.BackgroundMusicService;
import com.example.bloomedflowers.start.LoginActivity;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding profileBinding;

    public ProfileFragment() {
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
        profileBinding = FragmentProfileBinding.inflate(getLayoutInflater());

        return profileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileBinding.name.setText(HelperClass.users.getUserName());
        profileBinding.email.setText(HelperClass.users.getEmail());
        profileBinding.phone.setText(HelperClass.users.getPhone());
        profileBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(), LoginActivity.class));
                requireActivity().finish();
            }
        });

        profileBinding.playMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlaying){
                    isPlaying = true;
                    requireActivity().startService(new Intent(requireContext(), BackgroundMusicService.class));
                }
            }
        });

        profileBinding.stopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    isPlaying = false;
                    requireActivity().stopService(new Intent(requireContext(), BackgroundMusicService.class));
                }
            }
        });

    }
}