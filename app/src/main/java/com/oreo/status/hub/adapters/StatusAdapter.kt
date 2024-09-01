package com.oreo.status.hub.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oreo.status.hub.R // Replace "com.example.yourpackage" with your actual package name

class StatusAdapter(private var uriList: List<Uri>) : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uri = uriList[position]
        Glide.with(holder.itemView.context)
            .load(uri)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return uriList.size
    }
    fun updateData(newList: List<Uri>) {
        // Check for empty list to avoid errors
        if (newList.isEmpty()) {
            return
        }
        // Clear the current list
        uriList = emptyList()
        // Update internal data
        uriList = newList
        // Notify the adapter about the change
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
