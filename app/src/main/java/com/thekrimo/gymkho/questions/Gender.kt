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
import com.thekrimo.gymkho.databinding.ActivityGenderBinding

class Gender : AppCompatActivity() {
    private lateinit var binding: ActivityGenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val maleBox = binding.maleCheckBox
        val maleButton = binding.maleCheckButton
        val femaleBox = binding.CheckBoxFemale
        val femaleButton = binding.femaleCheckButton


        val onCheckedChangeListener = object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                maleBox.setOnCheckedChangeListener(null)
                maleButton.setOnCheckedChangeListener(null)
                femaleBox.setOnCheckedChangeListener(null)
                femaleButton.setOnCheckedChangeListener(null)

                when (buttonView?.id) {
                    R.id.maleCheckBox, R.id.maleCheckButton -> {
                        if (isChecked) {
                            femaleBox.isChecked = false
                            femaleButton.isChecked = false
                            maleBox.isChecked = true
                            maleButton.isChecked = true
                        } else {
                            maleBox.isChecked = true
                            maleButton.isChecked = true
                        }
                    }
                    R.id.CheckBoxFemale, R.id.femaleCheckButton -> {
                        if (isChecked) {
                            maleBox.isChecked = false
                            maleButton.isChecked = false
                            femaleBox.isChecked = true
                            femaleButton.isChecked = true
                        } else {
                            femaleBox.isChecked = true
                            femaleButton.isChecked = true
                        }
                    }
                }

                maleBox.setOnCheckedChangeListener(this)
                maleButton.setOnCheckedChangeListener(this)
                femaleBox.setOnCheckedChangeListener(this)
                femaleButton.setOnCheckedChangeListener(this)
            }
        }

        maleBox.setOnCheckedChangeListener(onCheckedChangeListener)
        maleButton.setOnCheckedChangeListener(onCheckedChangeListener)
        femaleBox.setOnCheckedChangeListener(onCheckedChangeListener)
        femaleButton.setOnCheckedChangeListener(onCheckedChangeListener)






        binding.next.setOnClickListener {
            val gender :String = if(maleBox.isChecked) {
                "male"
            } else {
                "female"
            }
            getSharedPreferences("questions", MODE_PRIVATE).edit().putString("gender",gender).apply()
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid!=null){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("gender")
                db.setValue(gender)
            }

            startActivity(Intent(this,Age::class.java))
        }



    }
}
