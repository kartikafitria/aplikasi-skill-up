package com.example.appresponsive_kelompok1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.androidnetworking.AndroidNetworking

const val baseUrl = "https://api-skill-up.permen.my.id"

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AndroidNetworking.initialize(getApplicationContext());

        val sharedPreferences = getSharedPreferences("SkillUp", MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")

        Handler(Looper.getMainLooper()).postDelayed({
            if (userId!!.isEmpty()) {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 1000)
    }
}