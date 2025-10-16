package com.example.appresponsive_kelompok1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)

        val tvEditProfile: TextView = view.findViewById(R.id.tv_edit_profile)
        val tvLogOut: TextView = view.findViewById(R.id.tv_log_out)
        val tvEmail: TextView = view.findViewById(R.id.tv_email)

        val sharedPreferences = requireContext().getSharedPreferences("SkillUp", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")

        tvEmail.text = email

        tvEditProfile.setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }

        tvLogOut.setOnClickListener {
            sharedPreferences.edit().clear().apply();

            startActivity(Intent(context, SignInActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}