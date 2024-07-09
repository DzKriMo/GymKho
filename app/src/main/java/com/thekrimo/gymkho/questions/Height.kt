package com.thekrimo.gymkho.questions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ActivityHeightBinding

class Height : AppCompatActivity() {
    private lateinit var binding: ActivityHeightBinding
    private lateinit var adapter: HeightAdapter

    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val heights = (140..228).toList()

        // Initialize adapter
        adapter = HeightAdapter(heights) { height ->
            Toast.makeText(this, "Selected Height: $height", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.scrollToPosition(heights.size / 2)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateSelectedPosition()
            }
        })

        binding.next.setOnClickListener {
            val height = adapter.selectedPosition  + 140
            getSharedPreferences("questions", MODE_PRIVATE).edit().putInt("height",height).apply()
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("height")
                db.setValue(height)
            }
            startActivity(Intent(this,Weight::class.java))
        }
    }

    private fun updateSelectedPosition() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        val middlePosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
        selectedPosition = middlePosition
        adapter.selectedPosition = selectedPosition // Update adapter with selected position
        adapter.notifyDataSetChanged() // Notify adapter of changes
    }
}

