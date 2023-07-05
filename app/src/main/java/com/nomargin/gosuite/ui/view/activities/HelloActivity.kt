package com.nomargin.gosuite.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.ActivityHelloBinding
import com.nomargin.gosuite.databinding.ActivitySplashBinding

class HelloActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.buttonGetStarted.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}