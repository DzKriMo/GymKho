package com.thekrimo.gymkho.questions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ActivityAgeBinding

class Age : AppCompatActivity() {
    private lateinit var binding: ActivityAgeBinding
    private lateinit var adapter: AgeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val ages = (12..70).toList()
        adapter = AgeAdapter(ages) { age ->
            Toast.makeText(this, "Selected Age: $age", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.scrollToPosition(ages.size / 2)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateSelectedPosition()
            }
        })

        binding.next.setOnClickListener {
            getSharedPreferences("questions", MODE_PRIVATE).edit().putInt("age",adapter.selectedPosition + 12).apply()
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if(uid!=null){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("age")
                db.setValue(adapter.selectedPosition+12)
            }

            startActivity(Intent(this,Height::class.java))
        }

    }

    private fun updateSelectedPosition() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val middlePosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
        adapter.selectedPosition = middlePosition
        adapter.notifyDataSetChanged()
    }
}