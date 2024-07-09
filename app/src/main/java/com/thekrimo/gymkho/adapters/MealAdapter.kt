package com.thekrimo.gymkho.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thekrimo.gymkho.dataclasses.Meal
import com.thekrimo.gymkho.databinding.ListItemMealBinding

class MealAdapter(private val context: Context, private var mealList: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ListItemMealBinding.inflate(LayoutInflater.from(context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun updateList(newList: List<Meal>) {
        mealList = newList
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        mealList = mealList.toMutableList().apply {
            removeAt(position)
        }
        notifyItemRemoved(position)
    }

    inner class MealViewHolder(private val binding: ListItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.mealName.text = meal.name
            binding.mealQuantity.text = meal.quantity
            binding.mealCalories.text = meal.calories
        }
    }
}
