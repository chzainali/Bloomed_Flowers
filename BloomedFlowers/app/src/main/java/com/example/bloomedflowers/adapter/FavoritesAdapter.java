package com.example.bloomedflowers.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bloomedflowers.R;
import com.example.bloomedflowers.databinding.FlowerItemsBinding;
import com.example.bloomedflowers.model.Favorites;
import com.example.bloomedflowers.model.Flowers;
import com.example.bloomedflowers.model.HelperClass;
import com.example.bloomedflowers.sql_db.DatabaseHelper;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{
    List<Favorites> favoriteList;
    Context context;
    DatabaseHelper databaseHelper;

    public FavoritesAdapter(List<Favorites> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flower_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Favorites favorites = favoriteList.get(position);
        databaseHelper = new DatabaseHelper(context);
        holder.binding.flowerName.setText(favorites.getName());
        Glide.with(context).load(favorites.getImage()).into(holder.binding.imageView);
        holder.binding.favorite.setVisibility(View.VISIBLE);
        holder.binding.favorites.setImageResource(R.drawable.ic_fav_two);
        holder.binding.favorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                databaseHelper.deleteFavorites(favorites.getId());
                favoriteList.remove(position);
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });

        holder.binding.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("check", "Favorite");
                bundle.putSerializable("data", favorites);
                Navigation.findNavController(view).navigate(R.id.action_favoritesFragment_to_detailsFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        FlowerItemsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FlowerItemsBinding.bind(itemView);
        }
    }

}
