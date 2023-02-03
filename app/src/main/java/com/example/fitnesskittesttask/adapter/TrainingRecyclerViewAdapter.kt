package com.example.fitnesskittesttask.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesskittesttask.databinding.ItemDateBinding
import com.example.fitnesskittesttask.databinding.ItemTrainingBinding

class TrainingRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val trainings = mutableListOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when (viewType) {
            ItemType.DATE.type -> {
                val binding = ItemDateBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                DateViewHolder(binding)
            }
            else -> {
                val binding = ItemTrainingBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                TrainingViewHolder(binding)
            }
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = trainings[position]) {
            is Item.Date -> (holder as DateViewHolder).bind(item)
            is Item.Training -> (holder as TrainingViewHolder).bind(item)
        }
    }

    override fun getItemViewType(
        position: Int
    ): Int {
        return when (trainings[position]) {
            is Item.Date -> ItemType.DATE.type
            is Item.Training -> ItemType.TRAINING.type
        }
    }

    override fun getItemCount(): Int = trainings.size

    private class DateViewHolder(
        private val binding: ItemDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Item.Date) {
            binding.textViewDate.text = date.date
        }
    }

    private class TrainingViewHolder(
        private val binding: ItemTrainingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(training: Item.Training) {
            binding.textViewFrom.text = training.from
            binding.textViewTo.text = training.to
            binding.textViewTraining.text = training.training
            binding.textViewTrainer.text = training.trainer
            binding.textViewPlace.text = training.place
            binding.textViewDuration.text = training.duration
            binding.materialCardView.setCardBackgroundColor(Color.parseColor(training.color))
        }
    }

    fun updateSchedules(newList: List<Item>) {
        trainings.clear()
        trainings.addAll(newList)
        notifyDataSetChanged()
    }
}