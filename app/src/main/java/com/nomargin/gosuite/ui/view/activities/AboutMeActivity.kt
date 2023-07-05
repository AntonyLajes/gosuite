package com.nomargin.gosuite.ui.view.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nomargin.gosuite.databinding.ActivityAboutMeBinding

class AboutMeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAboutMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLinkedin.setOnClickListener(this)
        binding.buttonGithub.setOnClickListener(this)
        binding.buttonMailMe.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonLinkedin.id -> {
                browseIntent("https://www.linkedin.com/in/antonylajes/")
            }
            binding.buttonGithub.id -> {
                browseIntent("https://github.com/AntonyLajes")
            }
            binding.buttonMailMe.id -> {
                browseIntent("mailto:tonymateuslajes@gmail.com")
            }
        }
    }

    private fun browseIntent(url: String){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}