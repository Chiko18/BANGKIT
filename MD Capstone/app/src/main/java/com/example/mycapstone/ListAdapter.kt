package com.example.mycapstone

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAdapter(private val listriwayat: ArrayList<String>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.tv_item_name)
        var tvDesc:  TextView = view.findViewById(R.id.tv_item_detail)
        var ivPhoto: ImageView = view.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val riwayat = listriwayat[position]

        Glide.with(holder.itemView.context)
            .load(riwayat)
    }
}