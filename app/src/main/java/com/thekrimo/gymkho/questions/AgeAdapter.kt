package com.thekrimo.gymkho.questions

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ItemAgeBinding

class AgeAdapter(private val ages: List<Int>, private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<AgeAdapter.AgeViewHolder>() {

    var selectedPosition = ages.size / 2

    inner class AgeViewHolder(private val binding: ItemAgeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(age: Int) {
            binding.tvAge.text = age.toString()
            if (selectedPosition == adapterPosition) {
                binding.tvAge.setBackgroundResource(R.drawable.age_circle)
                val purpleColor = ContextCompat.getColor(binding.root.context, R.color.text_purple)
                binding.tvAge.setTextColor(purpleColor)
                binding.tvAge.textSize = 40f
            } else {
                binding.tvAge.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAge.setTextColor(Color.WHITE)
                binding.tvAge.textSize = 28f
            }

            binding.tvAge.setOnClickListener {
                onItemClick(age)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeViewHolder {
        val binding = ItemAgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgeViewHolder, position: Int) {
        holder.bind(ages[position])
    }

    override fun getItemCount() = ages.size
}
