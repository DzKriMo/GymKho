package com.thekrimo.gymkho.questions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ActivityChangeBinding

class Change : AppCompatActivity() {
    private lateinit var binding: ActivityChangeBinding
    private lateinit var seekBarValue: TextView
    private lateinit var seekBarValueContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val flag: String
        flag = getSharedPreferences("questions", MODE_PRIVATE).getString("flag","").toString()
        val toptxt = binding.toptxt
        val subtxt = binding.subtxt
        if (flag == "fat") {
            toptxt.text = "How Much To Lose\nin one week?"
            subtxt.text = "Set your weight loss goals to tailor your fitness plan and achieve your desired results."
        } else if(flag == "muscle") {
            toptxt.text = "How Much To Gain\nin one week?"
            subtxt.text = "Set your weight gain goals to tailor your fitness plan and achieve your desired results."
        }

        val seekBar = binding.customSeekBar
        seekBarValue = binding.seekBarValue
        seekBarValueContainer = binding.seekBarValueContainer
        updateSeekBarValue(seekBar.progress)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateSeekBarValue(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.next.setOnClickListener {
            getSharedPreferences("questions", MODE_PRIVATE).edit().putFloat("change",
                (seekBar.progress*0.01).toFloat()).apply()

            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("change")
                db.setValue(seekBar.progress*0.01)
            }
            startActivity(Intent(this,Intensity::class.java))
        }
    }

    private fun updateSeekBarValue(progress: Int) {
        seekBarValue.text = String.format("%.2f",(progress* 0.01 ))
        val end : ImageView = binding.end
        if (progress==100){
            end.setImageResource(R.drawable.seekthumb)
        }
        else{
            end.setImageResource(R.drawable.end)
        }

        val thumbPosX = binding.customSeekBar.thumb.bounds.exactCenterX()

        seekBarValueContainer.x = thumbPosX + (seekBarValueContainer.width / 4)
        seekBarValue.x = thumbPosX + (seekBarValueContainer.width / 2)

    }
}


