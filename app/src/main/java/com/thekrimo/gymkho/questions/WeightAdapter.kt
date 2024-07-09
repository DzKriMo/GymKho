package com.thekrimo.gymkho.questions

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ItemAgeBinding
import com.thekrimo.gymkho.databinding.ItemWeightBinding

class WeightAdapter(
    private val weights: List<Int>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {

    var selectedPosition = weights.size / 2

    inner class WeightViewHolder(private val binding: ItemWeightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weight: Int) {
            binding.tvAge.text = weight.toString()
            if (selectedPosition == adapterPosition) {

                val purpleColor = ContextCompat.getColor(binding.root.context, R.color.text_purple)
                binding.tvAge.setTextColor(purpleColor)
                binding.tvAge.textSize = 44f
            } else {
                binding.tvAge.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAge.setTextColor(Color.WHITE)
                binding.tvAge.textSize = 24f
            }

            binding.tvAge.setOnClickListener {
                onItemClick(weight)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val binding = ItemWeightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.bind(weights[position])
    }

    override fun getItemCount() = weights.size
}