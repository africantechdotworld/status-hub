package com.oreo.status.hub

import android.app.ActivityOptions
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.content.Intent
import android.os.Handler
import android.os.Looper

class HubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)
        supportActionBar?.hide()
        val mainLooper = Looper.getMainLooper()

        // Create a Handler associated with the main looper
        val handler = Handler(mainLooper)
        handler.postDelayed({
            val intent = Intent(this, SetupActivity::class.java) // Replace "NextActivity" with your actual next activity class name

            startActivity(intent)
            finish()
        }, 3000) // 3 seconds in milliseconds
    }
}