package com.thekrimo.gymkho.questions

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.databinding.ActivityIntensityBinding

class Intensity : AppCompatActivity() {
    private lateinit var binding: ActivityIntensityBinding
    private var selectedLayout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntensityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.beginnerLayout.isSelected = true
        selectedLayout = binding.beginnerLayout

        setupAccordion(binding.beginnerLayout, binding.beginnerDescription, binding.beginnerCheckmark)
        setupAccordion(binding.intermediateLayout, binding.intermediateDescription, binding.intermediateCheckmark)
        setupAccordion(binding.extremeLayout, binding.extremeDescription, binding.extremeCheckmark)

        binding.next.setOnClickListener {
            var intensity : String = ""
            if (binding.beginnerLayout.isSelected){
                intensity = "beginner"
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("intensity","beginner").apply()
            }else  if (binding.intermediateLayout.isSelected){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("intensity","intermediate").apply()
                intensity="intermediate"
            }
            else  if (binding.extremeLayout.isSelected){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("intensity","extreme").apply()
                intensity="extreme"
            }

            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("intensity")
                db.setValue(intensity)
            }

            startActivity(Intent(this,Rate::class.java))
        }

    }

    private fun setupAccordion(layout: View, description: View, checkmark: View) {
        layout.setOnClickListener {
            val transition = AutoTransition()
            transition.duration = 100
            TransitionManager.beginDelayedTransition(binding.root, transition)

           /** if (selectedLayout == layout) {
                description.visibility = View.GONE
                checkmark.visibility = View.GONE
                layout.isSelected = false
                selectedLayout = null
            } else { **/
                selectedLayout?.let { prevLayout ->
                    val prevDescription = when (prevLayout) {
                        binding.beginnerLayout -> binding.beginnerDescription
                        binding.intermediateLayout -> binding.intermediateDescription
                        binding.extremeLayout -> binding.extremeDescription
                        else -> null
                    }
                    val prevCheckmark = when (prevLayout) {
                        binding.beginnerLayout -> binding.beginnerCheckmark
                        binding.intermediateLayout -> binding.intermediateCheckmark
                        binding.extremeLayout -> binding.extremeCheckmark
                        else -> null
                    }
                    prevDescription?.visibility = View.GONE
                    prevCheckmark?.visibility = View.GONE
                    prevLayout.isSelected = false
                }

                description.visibility = View.VISIBLE
                checkmark.visibility = View.VISIBLE
                layout.isSelected = true

                selectedLayout = layout
            //}
        }
    }
}
