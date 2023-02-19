package com.study.trajectory_semifinal.adapter

import android.content.Context
import com.study.trajectory_semifinal.model.ServiceViewInfo
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.study.trajectory_semifinal.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ServiceAdapter(private val context: Context, private val services: List<ServiceViewInfo>) :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {
    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view){
        val serviceView: ImageView = view.findViewById(R.id.imageView)
        val nameView: TextView = view.findViewById(R.id.nameTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        Glide.with(context)
            .load(service.icon)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.serviceView)
        holder.nameView.text = service.name
    }

    override fun getItemCount(): Int {
        return services.size
    }
}