package com.thekrimo.gymkho.questions

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ItemRateBinding

class RateAdapter (private val rates: List<String>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    var selectedPosition = rates.size / 2

    inner class RateViewHolder(private val binding: ItemRateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(Rate: String) {
            binding.tvAge.text = Rate.toString()

            if (selectedPosition == adapterPosition) {
                binding.tvAge.setBackgroundResource(R.drawable.height_lines)
                val purpleColor = ContextCompat.getColor(binding.root.context, R.color.text_purple)
                binding.tvAge.setTextColor(purpleColor)
                binding.tvAge.textSize = 40f

            } else if (selectedPosition == adapterPosition + 1 || selectedPosition == adapterPosition - 1 ){
                binding.tvAge.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAge.setTextColor(Color.WHITE)
                binding.tvAge.textSize = 28f
            }
            else {
                binding.tvAge.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAge.setTextColor(Color.WHITE)
                binding.tvAge.textSize = 22f

            }

            binding.tvAge.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL

            binding.tvAge.setOnClickListener {
                onItemClick(Rate)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding = ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(rates[position])
    }



    override fun getItemCount() = rates.size
}