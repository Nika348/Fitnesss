package com.example.fitnesss.screens.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnesss.R
import com.example.fitnesss.databinding.ItemWorkoutsBinding
import com.example.fitnesss.models.workouts.Workouts

class FavoriteAdapter(
    private val onItemClick: (Workouts) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {


    var listFavorite = emptyList<Workouts>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    //может иметь доступ к членам внешнего класса
    inner class MyViewHolder(private val binding: ItemWorkoutsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun populate(workouts: Workouts) {
            binding.textExercise.text = workouts.nameExercise
            binding.calories.text = workouts.calories

            Glide.with(binding.root.context)
                .load(workouts.imageExercise)
                .centerCrop()
                .error(R.drawable.good_shape)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageExercise)

            binding.root.setOnClickListener {
                onItemClick.invoke(workouts)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemWorkoutsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = listFavorite[position]
        holder.populate(content)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

}
