package com.study.trajectory_semifinal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.study.trajectory_semifinal.R;
import com.study.trajectory_semifinal.model.ServiceViewInfo;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        final ImageView serviceView;
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            serviceView = view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
            nameView = view.findViewById(R.id.nameTextView);

        }

        @Override
        public void onClick(View view) {

        }
    }
    private final List<ServiceViewInfo> services;
    private final Context context;

    public ServiceAdapter(Context context, List<ServiceViewInfo> services) {
        this.services = services;
        this.context = context;
    }


    

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        ServiceViewInfo service = services.get(position);
        Glide.with(this.context)
                .load(service.icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.serviceView);
        holder.nameView.setText(service.name);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

}
