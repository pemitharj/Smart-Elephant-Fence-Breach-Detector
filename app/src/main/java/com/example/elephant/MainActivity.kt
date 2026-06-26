package com.example.elephant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import com.example.elephant.databinding.ActivityMainBinding
import com.example.elephant.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Ask for notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }

        // ✅ Subscribe to FCM topic
        FirebaseMessaging.getInstance().subscribeToTopic("allUsers")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "✅ Subscribed to topic: allUsers")
                } else {
                    Log.e("FCM", "❌ Topic subscription failed", task.exception)
                }
            }

        binding.receiveBtn.setOnClickListener {
            startActivity(Intent(this, ReceiveActivity::class.java))
        }

        binding.feedbackBtn.setOnClickListener {
            startActivity(Intent(this, FeedbackActivity::class.java))
        }

        binding.aboutBtn.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}