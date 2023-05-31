package com.example.bloomedflowers.adapter;

import static com.example.bloomedflowers.user.fragments.HomeFragment.favoriteList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bloomedflowers.R;
import com.example.bloomedflowers.admin.FlowerDetailsActivity;
import com.example.bloomedflowers.databinding.FlowerItemsBinding;
import com.example.bloomedflowers.model.Favorites;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.model.HelperClass;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FlowersAdapter extends RecyclerView.Adapter<FlowersAdapter.ViewHolder> {
    List<Flowers> list;
    List<Favorites> favoritesFlowerList;
    Context context;
    String checkSide;
    DatabaseHelper databaseHelper;
    Boolean isAdded = false;
    int deletedPosition;

    public FlowersAdapter(List<Flowers> list, Context context, String checkSide) {
        this.list = list;
        this.context = context;
        this.checkSide = checkSide;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flower_items,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flowers flowers = list.get(position);
        favoritesFlowerList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        holder.binding.flowerName.setText(flowers.getName());
        Glide.with(context).load(flowers.getImage()).into(holder.binding.imageView);

        if (checkSide.equals("admin")){
            holder.binding.favorite.setVisibility(View.GONE);
        }else{
            holder.binding.favorite.setVisibility(View.VISIBLE);
            if (!favoriteList.isEmpty()){
                for (int i=0; i<favoriteList.size(); i++){
                    if (flowers.getId() == favoriteList.get(i).getFlowerId()){
                        holder.binding.favorites.setImageResource(R.drawable.ic_fav_two);
                    }
                }
            }
        }

        holder.binding.favorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                favoritesFlowerList = databaseHelper.getAllFavorites();
                if (!favoritesFlowerList.isEmpty()){
                    for (int i=0; i<favoritesFlowerList.size(); i++){
                        if (flowers.getId() == favoritesFlowerList.get(i).getFlowerId()){
                            isAdded = true;
                            deletedPosition = favoritesFlowerList.get(i).getId();
                        }
                    }

                    if (!isAdded){
                        Favorites favorites = new Favorites(flowers.getId(), HelperClass.users.getId(), flowers.getImage(),
                                flowers.getName(), flowers.getType(), flowers.getSeason(), flowers.getGrowthRate(), flowers.getMethodOfCare());
                        databaseHelper.addFavorite(favorites);
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                        holder.binding.favorites.setImageResource(R.drawable.ic_fav_two);
                        notifyDataSetChanged();
                    }

                }else{
                    Favorites favorites = new Favorites(flowers.getId(), HelperClass.users.getId(), flowers.getImage(),
                            flowers.getName(), flowers.getType(), flowers.getSeason(), flowers.getGrowthRate(), flowers.getMethodOfCare());
                    databaseHelper.addFavorite(favorites);
                    holder.binding.favorites.setImageResource(R.drawable.ic_fav_two);
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            }
        });

        holder.binding.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSide.equals("admin")){
                    Intent intent = new Intent(context, FlowerDetailsActivity.class);
                    intent.putExtra("data", flowers);
                    context.startActivity(intent);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("check", "Home");
                    bundle.putSerializable("data", flowers);
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailsFragment, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        FlowerItemsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FlowerItemsBinding.bind(itemView);
        }
    }

}
