package com.thekrimo.gymkho.questions

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ActivityGoalBinding

class Goal : AppCompatActivity() {
    private lateinit var binding: ActivityGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.next.setOnClickListener {
            val goal : String
            if (binding.buildMuscles.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("flag","muscle").apply()
                goal = "build"
            }
            else if (binding.looseWeight.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("flag","fat").apply()
                goal = "loose"
            }
            else {
                goal = "stay"
            }
            getSharedPreferences("questions", MODE_PRIVATE).edit().putString("goal",goal).apply()
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("goal")
                db.setValue(goal)
            }
            startActivity(Intent(this,Equipments::class.java))
        }

        val build = binding.buildMuscles
        val loose = binding.looseWeight
        val stay  = binding.stayFit

        val onCheckedChangeListener = object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                build.setOnCheckedChangeListener(null)
                loose.setOnCheckedChangeListener(null)
                stay.setOnCheckedChangeListener(null)


                when (buttonView?.id) {
                     R.id.buildMuscles -> {
                        if (isChecked) {
                            stay.isChecked = false
                            loose.isChecked = false
                            build.isChecked = true

                        } else {
                            build.isChecked = true
                        }
                    }
                    R.id.looseWeight -> {
                        if (isChecked) {
                            build.isChecked = false
                            stay.isChecked = false
                            loose.isChecked = true

                        } else {
                            loose.isChecked = true
                        }
                    }

                    R.id.stayFit -> {
                        if (isChecked) {
                            build.isChecked = false
                            stay.isChecked = true
                            loose.isChecked = false

                        } else {
                            stay.isChecked = true
                        }
                    }
                }

                build.setOnCheckedChangeListener(this)
                loose.setOnCheckedChangeListener(this)
                stay.setOnCheckedChangeListener(this)
            }
        }

        build.setOnCheckedChangeListener(onCheckedChangeListener)
        loose.setOnCheckedChangeListener(onCheckedChangeListener)
        stay.setOnCheckedChangeListener(onCheckedChangeListener)











    }
}
