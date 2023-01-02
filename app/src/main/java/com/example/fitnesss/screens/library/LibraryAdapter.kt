package com.example.fitnesss.screens.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnesss.R
import com.example.fitnesss.databinding.ItemLibraryBinding
import com.example.fitnesss.models.library.Library

class LibraryAdapter(
    private val onItemClick: (Library) -> Unit
): RecyclerView.Adapter<LibraryAdapter.MyViewHolder>() {


    var listLibrary = listOf<Library>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //может иметь доступ к членам внешнего класса
    inner class MyViewHolder(private val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populate(library: Library) {
            binding.itemLibraryText.text = library.textLibrary

            Glide.with(binding.root.context)
                .load(library.imageLibrary)
                .error(R.drawable.good_shape)
                .placeholder(R.drawable.placeholder)
                .into(binding.itemLibraryImage)

            binding.root.setOnClickListener{
                onItemClick.invoke(library)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = listLibrary[position]
        holder.populate(content)
    }

    override fun getItemCount(): Int {
        return listLibrary.size
    }

}
