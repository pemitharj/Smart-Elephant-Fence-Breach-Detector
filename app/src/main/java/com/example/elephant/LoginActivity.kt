package com.example.elephant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.elephant.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            val phoneNumber = binding.editTextText.text.toString().trim()

            if (isValidPhoneNumber(phoneNumber)) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("phone", phoneNumber)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Invalid Phone Number! Enter valid number.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isValidPhoneNumber(number: String): Boolean{
        return number.length== 10 && number.startsWith("0") && number.all{it.isDigit()}
    }
}