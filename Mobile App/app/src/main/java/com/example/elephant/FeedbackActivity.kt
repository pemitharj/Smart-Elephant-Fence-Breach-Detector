package com.example.elephant

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feedback)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views by ID
        val feedbackEditText = findViewById<EditText>(R.id.editTextText2)
        val enterButton = findViewById<Button>(R.id.enterBtn1)
        val backButton = findViewById<Button>(R.id.backBtn2)

        // Handle Enter button click
        enterButton.setOnClickListener {
            val feedbackText = feedbackEditText.text.toString().trim()

            if (feedbackText.isNotEmpty()) {
                Toast.makeText(this, "Successfully sent your feedback", Toast.LENGTH_SHORT).show()
                feedbackEditText.setText("") // Optional: Clear input after sending
            } else {
                Toast.makeText(this, "Please enter your feedback before submitting", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Back button click
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }
}