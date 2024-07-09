package com.thekrimo.gymkho.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ActivityEquipmentsBinding

class Equipments : AppCompatActivity() {
    private lateinit var binding: ActivityEquipmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityEquipmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val switchEquipment = binding.switchequipment
        val tvYes= binding.tvYes
        val tvNo = binding.tvNo
        val switchcontainer = binding.switchcontainer

        val displayMetrics = DisplayMetrics()
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        Log.d("widthdpp", screenWidthDp.toString())
        if (screenWidthDp > 400){
        if (screenWidthDp < 500 ) {
            val scale = 1.1f
            switchcontainer.scaleX = scale
            switchcontainer.scaleY = scale
        }
        else if(screenWidthDp < 700){
            val scale = 1.2f
            switchcontainer.scaleX = scale
            switchcontainer.scaleY = scale

        }

        else if(screenWidthDp < 900){
            val scale = 1.3f
            switchcontainer.scaleX = scale
            switchcontainer.scaleY = scale

        }
        }

        switchEquipment.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvYes.setTextColor(resources.getColor(R.color.unselected))
                tvNo.setTextColor(resources.getColor(R.color.white))

            } else {
                tvYes.setTextColor(resources.getColor(R.color.white))
                tvNo.setTextColor(resources.getColor(R.color.unselected))

            }
        }

        switchEquipment.isChecked = false

        binding.next.setOnClickListener {
            if (!switchEquipment.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putBoolean("equipments",true).apply()
            }else getSharedPreferences("questions", MODE_PRIVATE).edit().putBoolean("equipments",false).apply()

            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("equipments")
                db.setValue(!switchEquipment.isChecked)
            }


            startActivity(Intent(this , Program::class.java))
        }
    }
}