package com.example.appresponsive_kelompok1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragemnt(HomeFragment())
                R.id.course -> replaceFragemnt(CourseFragment())
                R.id.bookmark -> replaceFragemnt(BookmarkFragment())
                R.id.profile -> replaceFragemnt(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragemnt(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit()
    }
}