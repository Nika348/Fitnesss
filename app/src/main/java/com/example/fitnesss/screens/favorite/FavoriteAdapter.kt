package com.example.fitnesss.screens.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnesss.R
import com.example.fitnesss.databinding.ItemWorkoutsBinding
import com.example.fitnesss.models.workouts.Workouts

class FavoriteAdapter(
    private val onItemClick: (Workouts) -> Unit
) : ListAdapter<Workouts, FavoriteAdapter.MyViewHolder>(FavoriteDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemWorkoutsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = getItem(position)
        holder.populate(content)
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

    class FavoriteDiffUtilCallback: DiffUtil.ItemCallback<Workouts>() {
        override fun areItemsTheSame(oldItem: Workouts, newItem: Workouts): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workouts, newItem: Workouts): Boolean {
            return oldItem == newItem
        }
    }
}
