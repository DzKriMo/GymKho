package com.thekrimo.gymkho

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.google.firebase.auth.FirebaseAuth
import com.thekrimo.gymkho.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.back.setOnClickListener {
        startActivity(Intent(this, GetStarted::class.java))
          finish()
        }

        binding.signup.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()

        binding.toggle1.setOnClickListener {
            if (binding.password.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

        }



        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty()){
                showToast("Email Cannot Be Empty")
            }else if (password.isEmpty()){
                showToast("Password Cannot Be Empty")
            }else {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            showToast("Logged In Successfully")
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else {
                            showToast("Failed to Login, Please Check Network Connection\n and email and password")
                        }
                    }
            }

        }

        val displayMetrics = DisplayMetrics()
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density

        if (screenWidthDp<400){
            binding.email.setPadding(10)
            binding.password.setPadding(10)
        }
    }
    private fun showToast(message: String){
        Toast.makeText(baseContext,message,Toast.LENGTH_SHORT).show()
    }
}