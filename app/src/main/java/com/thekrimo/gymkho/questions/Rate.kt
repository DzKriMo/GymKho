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
import com.thekrimo.gymkho.MainActivity
import com.thekrimo.gymkho.R
import com.thekrimo.gymkho.databinding.ActivityRateBinding

class Rate : AppCompatActivity() {
    private lateinit var binding: ActivityRateBinding
    private lateinit var adapter: RateAdapter
    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rates = listOf("1-3 days","3-4 days","5-7 days")

        adapter = RateAdapter(rates){ rate ->
            Toast.makeText(this, "Selected RATE: $rate", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.scrollToPosition(rates.size / 2)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateSelectedPosition()
            }
        })

        binding.next.setOnClickListener {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid !=null ){
                val db = FirebaseDatabase.getInstance().getReference("users").child(uid).child("rate")
                db.setValue(adapter.selectedPosition+1)
            }


            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("new",true)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun updateSelectedPosition() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        val middlePosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
        selectedPosition = middlePosition
        adapter.selectedPosition = selectedPosition
        adapter.notifyDataSetChanged()
    }
}

