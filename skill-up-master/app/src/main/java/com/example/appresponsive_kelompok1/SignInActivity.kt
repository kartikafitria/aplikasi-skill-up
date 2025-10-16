package com.example.appresponsive_kelompok1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btnSignIn: Button = findViewById(R.id.b_sign_in)
        val tvSignUpHere: TextView = findViewById(R.id.tv_sign_up_here)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etPassword: EditText = findViewById(R.id.et_password)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) etEmail.error = "Email harus diisi"
            if (password.isEmpty()) etPassword.error = "Kata sandi harus diisi"

            if (email.isNotEmpty() && password.isNotEmpty()) {
                AndroidNetworking.post("$baseUrl/api/login")
                    .addBodyParameter("email", email)
                    .addBodyParameter("password", password)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getString("status").equals("success")) {
                                val data = response.getJSONObject("data")

                                val sharedPreferences = getSharedPreferences("SkillUp", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("userId", data.getString("id"))
                                editor.putString("name", data.getString("name"))
                                editor.putString("email", data.getString("email"))
                                editor.putString("phoneNumber", data.getString("phone_number"))
                                editor.apply()

                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            Toast.makeText(this@SignInActivity, response.getString("message"), Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(error: ANError) {
                            if (error.errorCode != 0) {
                                Log.d("Skill Up", "onError errorCode: " + error.errorCode)
                                Log.d("Skill Up", "onError errorBody: " + error.errorBody)
                                Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                            } else {
                                Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                            }

                            Toast.makeText(this@SignInActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        tvSignUpHere.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}