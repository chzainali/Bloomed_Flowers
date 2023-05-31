package com.example.bloomedflowers.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.ActivityUserBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding userBinding;
    NavHostFragment navHostFragment;
    NavController navController;
    public static Boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBinding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(userBinding.getRoot());

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        setBottomNavigation();

    }

    private void setBottomNavigation() {

        userBinding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        userBinding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_favorite_24));
        userBinding.bottomNav.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_person_24));

        navController.navigate(R.id.homeFragment);
        userBinding.bottomNav.bottomNavigation.show(1, true);

        userBinding.bottomNav.bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()) {
                    case 1:
                        navController.navigate(R.id.homeFragment);
                        break;

                    case 2:
                        navController.navigate(R.id.favoritesFragment);
                        break;

                    case 3:
                        navController.navigate(R.id.profileFragment);
                        break;
                }

                return null;
            }
        });

        userBinding.bottomNav.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.homeFragment);
                userBinding.bottomNav.bottomNavigation.show(1, true);
            }
        });
        userBinding.bottomNav.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userBinding.bottomNav.bottomNavigation.show(2, true);
                navController.navigate(R.id.favoritesFragment);
            }
        });
        userBinding.bottomNav.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.profileFragment);
                userBinding.bottomNav.bottomNavigation.show(3, true);
            }
        });
    }

}