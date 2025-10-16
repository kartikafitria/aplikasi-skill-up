package com.example.appresponsive_kelompok1

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvUserProfile: TextView = view.findViewById(R.id.tv_user_profile)
        val tvSettings: TextView = view.findViewById(R.id.tv_settings)

        replaceFragemnt(UserFragment())

        tvUserProfile.setOnClickListener {
            replaceFragemnt(UserFragment())
            tvUserProfile.setBackgroundResource(R.drawable.selector_10)
            tvUserProfile.setTextColor(Color.parseColor("#FFFFFF"))
            tvSettings.setBackgroundResource(R.drawable.selector_11)
            tvSettings.setTextColor(Color.parseColor("#0F273F"))
        }

        tvSettings.setOnClickListener {
            replaceFragemnt(SettingsFragment())
            tvSettings.setBackgroundResource(R.drawable.selector_10)
            tvSettings.setTextColor(Color.parseColor("#FFFFFF"))
            tvUserProfile.setBackgroundResource(R.drawable.selector_11)
            tvUserProfile.setTextColor(Color.parseColor("#0F273F"))
        }

        return view
    }

    private fun replaceFragemnt(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.profile_fragment_container_view, fragment).commit()
    }
}