package com.example.elephant

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class ReceiveActivity : AppCompatActivity() {

    private lateinit var messagesContainer: LinearLayout
    private lateinit var databaseRef: DatabaseReference
    private var lastOpenedTime: Long = 0L

    data class Message(
        val message: String? = null,
        val timestamp: Long? = null,
        val command: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive)

        val backButton = findViewById<Button>(R.id.backBtn3)
        messagesContainer = findViewById(R.id.messagesContainer)

        val prefs = getSharedPreferences("ReceivePrefs", MODE_PRIVATE)
        lastOpenedTime = prefs.getLong("lastOpenedTime", 0L)

        databaseRef = FirebaseDatabase.getInstance().getReference("SMS_messages")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagesContainer.removeAllViews()
                val messagesList = mutableListOf<Message>()

                for (messageSnapshot in snapshot.children) {
                    val msgObj = messageSnapshot.getValue(Message::class.java)
                    msgObj?.let { messagesList.add(it) }
                }

                messagesList.sortByDescending { it.timestamp ?: 0L }

                for (msgObj in messagesList) {
                    val timestamp = msgObj.timestamp ?: 0L
                    val isNew = timestamp > lastOpenedTime

                    addMessageBox(
                        message = msgObj.message ?: "No message",
                        timestamp = timestamp,
                        latitude = msgObj.latitude ?: 0.0,
                        longitude = msgObj.longitude ?: 0.0,
                        isNew = isNew
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ReceiveActivity, "Error loading messages", Toast.LENGTH_SHORT).show()
            }
        })

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun addMessageBox(
        message: String,
        timestamp: Long,
        latitude: Double,
        longitude: Double,
        isNew: Boolean
    ) {
        val box = TextView(this)

        val formattedTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(timestamp * 1000))
        val mapUrl = "https://maps.google.com/?q=$latitude,$longitude"

        val fullText = "📩 $message\n🕒 $formattedTime\n📍 Location (tap to view)"

        // SpannableString to style the "Location (tap to view)" part
        val spannable = SpannableString(fullText)
        val locationText = "Location (tap to view)"
        val start = fullText.indexOf(locationText)
        val end = start + locationText.length

        val linkColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
        spannable.setSpan(ForegroundColorSpan(linkColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        box.text = spannable
        box.setTextColor(Color.BLACK)
        box.setPadding(20, 20, 20, 20)
        box.setBackgroundResource(if (isNew) R.drawable.new_msg_box else R.drawable.old_msg_box)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 20)
        box.layoutParams = params

        box.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
            startActivity(intent)
        }

        messagesContainer.addView(box)
    }

    override fun onStop() {
        super.onStop()
        val prefs = getSharedPreferences("ReceivePrefs", MODE_PRIVATE).edit()
        prefs.putLong("lastOpenedTime", System.currentTimeMillis() / 1000)
        prefs.apply()
    }
}
