package com.nomargin.gosuite.ui.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nomargin.gosuite.databinding.ActivitySplashBinding
import com.nomargin.gosuite.util.constants.ApplicationConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        //Clear SharedPreferences
        getSharedPreferences(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesGeneralKey, Context.MODE_PRIVATE).edit().clear().apply()
        lifecycleScope.launch{
            delay(1500)
            startActivity(Intent(applicationContext, HelloActivity::class.java))
            finish()
        }
    }
}