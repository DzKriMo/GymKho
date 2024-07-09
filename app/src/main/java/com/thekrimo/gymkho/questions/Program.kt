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
import com.thekrimo.gymkho.databinding.ActivityProgramBinding

class Program : AppCompatActivity() {
    private lateinit var binding:ActivityProgramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.next.setOnClickListener {
            var program : String = ""
            if(binding.ppl.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("program","ppl").apply()
                program = "ppl"
            }
            else if(binding.upperlower.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("program","upperLower").apply()
                program = "upperLower"
            }
            else if(binding.full.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("program","full").apply()
                program = "full"
            }
            else if(binding.bros.isChecked){
                getSharedPreferences("questions", MODE_PRIVATE).edit().putString("program","bros").apply()
                program = "bros"
            }
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("program")
                db.setValue(program)
            }


            val flag = getSharedPreferences("questions", MODE_PRIVATE).getString("flag",null)
            if (flag == null ){
                startActivity(Intent(this,Intensity::class.java))
            }
            else{
                startActivity(Intent(this,Change::class.java))
            }

        }

        val ppl = binding.ppl
        val upperlower = binding.upperlower
        val full  = binding.full
        val bros = binding.bros


        val onCheckedChangeListener = object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                ppl.setOnCheckedChangeListener(null)
                upperlower.setOnCheckedChangeListener(null)
                full.setOnCheckedChangeListener(null)
                bros.setOnCheckedChangeListener(null)

                when (buttonView?.id) {
                    R.id.ppl -> {
                        if (isChecked) {
                            upperlower.isChecked = false
                            full.isChecked = false
                            bros.isChecked = false
                            ppl.isChecked = true

                        } else {
                            ppl.isChecked = true
                        }
                    }
                    R.id.upperlower -> {
                        if (isChecked) {
                            ppl.isChecked = false
                            bros.isChecked = false
                            full.isChecked = false
                            upperlower.isChecked = true

                        } else {
                            upperlower.isChecked = true
                        }
                    }

                    R.id.full -> {
                        if (isChecked) {
                            ppl.isChecked = false
                            upperlower.isChecked = false
                            bros.isChecked = false
                            full.isChecked = true


                        } else {
                            full.isChecked = true
                        }
                    }
                    R.id.bros -> {
                        if(isChecked){
                            ppl.isChecked = false
                            upperlower.isChecked = false
                            full.isChecked = false
                            bros.isChecked = true

                        }
                        else{
                            bros.isChecked = true
                        }
                    }

                }

                ppl.setOnCheckedChangeListener(this)
                upperlower.setOnCheckedChangeListener(this)
                full.setOnCheckedChangeListener(this)
                bros.setOnCheckedChangeListener(this)
            }
        }

        ppl.setOnCheckedChangeListener(onCheckedChangeListener)
        upperlower.setOnCheckedChangeListener(onCheckedChangeListener)
        full.setOnCheckedChangeListener(onCheckedChangeListener)
        bros.setOnCheckedChangeListener(onCheckedChangeListener)


    }
}