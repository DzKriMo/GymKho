package com.thekrimo.gymkho

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.thekrimo.gymkho.databinding.ActivityMainBinding
import com.thekrimo.gymkho.fragments.HomeFragment
import com.thekrimo.gymkho.questions.Change
import com.thekrimo.gymkho.questions.Equipments
import com.thekrimo.gymkho.questions.Goal
import com.thekrimo.gymkho.questions.Intensity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var currentFragmentTag = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null){
            startActivity(Intent(this, GetStarted::class.java))
            finish()
        }

        if(intent.hasExtra("new")){
             binding.newUser.visibility = View.VISIBLE
            binding.okDialogueButton.setOnClickListener {
                binding.newUser.visibility = View.GONE
            }

        }
        replaceFragment(HomeFragment(),"Home")
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.navHome ->{
                    replaceFragment(HomeFragment(),"Home")
                    true
                }
                else -> false
            }
        }

    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        if (currentFragmentTag != tag) {
            currentFragmentTag = tag
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment, tag)
            transaction.commitAllowingStateLoss()
        }
    }
}