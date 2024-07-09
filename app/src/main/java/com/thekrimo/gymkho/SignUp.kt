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
import com.google.firebase.database.FirebaseDatabase
import com.thekrimo.gymkho.databinding.ActivitySignUpBinding
import com.thekrimo.gymkho.questions.Gender

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.back.setOnClickListener {
            startActivity(Intent(this,GetStarted::class.java))
            finish()
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }




        val displayMetrics = DisplayMetrics()
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density

        if (screenWidthDp<400){
            binding.email.setPadding(10)
            binding.password.setPadding(10)
            binding.name.setPadding(10)
            binding.confirmpassword.setPadding(10)
        }

        binding.signupButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmpassword.text.toString()
            val name = binding.name.text.toString()


            if (name.isEmpty()){
                showToast("Name Cannot Be Empty")
            }
            else if (email.isEmpty()){
                showToast("Email Cannot Be Empty")
            }
            else if(password.isEmpty()||confirmPassword.isEmpty()){
                showToast("Password or Confirm Password Cannot Be Empty")
            }
           else  if(password != confirmPassword){
                showToast("Password MissMatch")
            }
            else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            getSharedPreferences("userInfo", MODE_PRIVATE).edit().putString("email",email).putString("name",name).apply()
                            val uid = auth.currentUser?.uid
                            if(uid != null){
                                val db = FirebaseDatabase.getInstance().getReference("users").child(uid)
                                val userData = HashMap<String,Any>()
                                userData["email"]= email
                                userData["name"]= name
                                userData["gender"] ="male"
                                userData["age"]= 20
                                userData["height"] = 184
                                userData["weight"] =  68
                                userData["goal"] = "build"
                                userData["equipments"] = false
                                userData["change"]= 0.5
                                userData["intensity"]= "extreme"
                                userData["rate"]=2
                                userData["program"]="ppl"

                                db.setValue(userData)
                            }

                            startActivity(Intent(this,Gender::class.java))
                            finish()
                        }
                        else {
                            showToast("Sign Up Failed Please Check Your Network Connection")
                        }
                    }
            }





        }


        binding.toggle1.setOnClickListener {
            if (binding.password.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding.confirmpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.confirmpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

        }

        binding.toggle2.setOnClickListener {
            if (binding.password.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding.confirmpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.confirmpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

        }

    }

    private fun showToast(message: String){
        Toast.makeText(baseContext,message,Toast.LENGTH_SHORT).show()
    }
}