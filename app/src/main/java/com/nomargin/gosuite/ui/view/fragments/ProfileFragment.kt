package com.nomargin.gosuite.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.FragmentProfileBinding
import com.nomargin.gosuite.ui.view.activities.AboutMeActivity

class ProfileFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(requireContext(), AboutMeActivity::class.java))
        return true
    }
}