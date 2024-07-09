package com.thekrimo.gymkho.questions

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ItemAgeBinding
import com.thekrimo.gymkho.databinding.ItemHeightBinding

class HeightAdapter (private val heights: List<Int>, private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<HeightAdapter.HeightViewHolder>() {

    var selectedPosition = heights.size / 2

    inner class HeightViewHolder(private val binding: ItemHeightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(height: Int) {
            binding.tvAge.text = height.toString()

            if (selectedPosition == adapterPosition) {
                binding.tvAge.setBackgroundResource(R.drawable.height_lines)
                val purpleColor = ContextCompat.getColor(binding.root.context, R.color.text_purple)
                binding.tvAge.setTextColor(purpleColor)
                binding.tvAge.textSize = 40f
                binding.tvAge.text =binding.tvAge.text.toString()+"cm"
            } else if (selectedPosition == adapterPosition + 1 || selectedPosition == adapterPosition - 1 ){
                binding.tvAge.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAge.setTextColor(Color.WHITE)
                binding.tvAge.textSize = 28f
                binding.tvAge.text=binding.tvAge.text.toString().replace("cm", "")
            }
            else {
                binding.tvAge.setBackgroundColor(Color.TRANSPARENT)
                binding.tvAge.setTextColor(Color.WHITE)
                binding.tvAge.textSize = 22f
                binding.tvAge.text=binding.tvAge.text.toString().replace("cm", "")
            }

            binding.tvAge.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL

            binding.tvAge.setOnClickListener {
                onItemClick(height)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeightViewHolder {
        val binding = ItemHeightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeightViewHolder, position: Int) {
        holder.bind(heights[position])
    }

    override fun getItemCount() = heights.size
}