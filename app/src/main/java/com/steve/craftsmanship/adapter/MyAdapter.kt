package com.steve.craftsmanship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.steve.craftsmanship.databinding.ListItemBinding

/**
 * This recyclerview adapter is quite basic, except it has ItemClickListener() when user click the
 * driver's name (list item).
 */
class MyAdapter(private val nameList: MutableList<String>, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    inner class MyViewHolder(private val itemBinding: ListItemBinding): RecyclerView.ViewHolder (itemBinding.root) {
        fun bind(name: String, position: Int, clickListener: OnItemClickListener) {
            itemBinding.name.text = name
            itemBinding.name.setOnClickListener{
                clickListener.onItemClicked(itemBinding.name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(nameList.get(position), position, itemClickListener)
    }

    override fun getItemCount(): Int {
        return nameList.size
    }
}

interface OnItemClickListener{
    fun onItemClicked(view: TextView, index: Int)
}