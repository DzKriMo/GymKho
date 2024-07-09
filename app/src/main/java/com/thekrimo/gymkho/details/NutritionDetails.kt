package com.thekrimo.gymkho.details

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.adapters.CustomSpinnerAdapter
import com.thekrimo.gymkho.adapters.MealAdapter
import com.thekrimo.gymkho.dataclasses.Meal
import com.thekrimo.gymkho.databinding.ActivityNutritionDetailsBinding

class NutritionDetails : AppCompatActivity() {
    private lateinit var binding: ActivityNutritionDetailsBinding
    private lateinit var mealAdapter: MealAdapter
    private val mealList = mutableListOf<Meal>()
    private val breakfastList = mutableListOf<Meal>()
    private val lunchList = mutableListOf<Meal>()
    private val dinnerList = mutableListOf<Meal>()
    private val snacksList = mutableListOf<Meal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeMealLists()

        val mealSpinner: Spinner = binding.mealSpinner
        val mealArray = resources.getStringArray(R.array.meals_array)
        val adapter = CustomSpinnerAdapter(
            this,
            R.layout.meal_spinner_item,
            android.R.id.text1,
            mealArray
        )

        mealSpinner.adapter = adapter

        mealAdapter = MealAdapter(this, breakfastList)
        binding.mealRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mealRecyclerView.adapter = mealAdapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                mealAdapter.removeAt(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                val itemView = viewHolder.itemView
                val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)

                if (dX < 0) {
                    deleteButton.visibility = View.VISIBLE
                } else {
                    deleteButton.visibility = View.GONE
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.mealRecyclerView)

        mealSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateMealList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initializeMealLists() {
        breakfastList.add(Meal("Eggs (boiled)", "1 Whole (49g)", "76 Cal"))
        breakfastList.add(Meal("Cereal", "100g", "320 Cal"))
        dinnerList.add(Meal("Pizza", "4 slices", "800 Cal"))
        snacksList.add(Meal("Birra Barda", "7 kisan bardin", "bzaf Cal"))
    }

    private fun updateMealList(position: Int) {
        when (position) {
            0 -> {
                if (breakfastList.isEmpty()) {
                    binding.noMeals.visibility = View.VISIBLE
                } else {
                    binding.noMeals.visibility = View.GONE
                }
                mealAdapter.updateList(breakfastList)
            }
            1 -> {
                if (lunchList.isEmpty()) {
                    binding.noMeals.visibility = View.VISIBLE
                } else {
                    binding.noMeals.visibility = View.GONE
                }
                mealAdapter.updateList(lunchList)
            }
            2 -> {
                if (dinnerList.isEmpty()) {
                    binding.noMeals.visibility = View.VISIBLE
                } else {
                    binding.noMeals.visibility = View.GONE
                }
                mealAdapter.updateList(dinnerList)
            }
            3 -> {
                if (snacksList.isEmpty()) {
                    binding.noMeals.visibility = View.VISIBLE
                } else {
                    binding.noMeals.visibility = View.GONE
                }
                mealAdapter.updateList(snacksList)
            }
            else -> {
                mealAdapter.updateList(emptyList())
            }
        }
    }
}
