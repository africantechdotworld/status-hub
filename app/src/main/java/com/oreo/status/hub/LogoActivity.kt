package com.oreo.status.hub

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.content.Intent
import android.os.Looper


class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logo)
        val supportActionBar = getSupportActionBar()

        // Hide the app bar
        supportActionBar?.hide()


        // Handler for delayed action
        val handler = Handler()
        val runnable = Runnable {
            // This code will be executed after 3 seconds
            val intent = Intent(this, SetupActivity::class.java) // Replace with your next activity class
            startActivity(intent)
        }

        // Delay for 3 seconds (3000 milliseconds)
        handler.postDelayed(runnable, 3000)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}