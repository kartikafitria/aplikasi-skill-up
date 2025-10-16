package com.example.appresponsive_kelompok1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val etName: EditText = findViewById(R.id.et_name)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etPhoneNumber: EditText = findViewById(R.id.et_phone_number)
        val etPassword: EditText = findViewById(R.id.et_password)
        val bSignUp: Button = findViewById(R.id.b_sign_up)

        bSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phoneNumber = etPhoneNumber.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty()) etName.error = "Nama harus diisi"
            if (email.isEmpty()) etEmail.error = "Email harus diisi"
            if (phoneNumber.isEmpty()) etPhoneNumber.error = "Nomor telepon harus diisi"
            if (password.isEmpty()) etPassword.error = "Kata sandi harus diisi"

            if (name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                AndroidNetworking.post("$baseUrl/api/register")
                    .addBodyParameter("name", name)
                    .addBodyParameter("email", email)
                    .addBodyParameter("phone_number", phoneNumber)
                    .addBodyParameter("password", password)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getString("status").equals("success")) {
                                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            Toast.makeText(this@SignUpActivity, response.getString("message"), Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(error: ANError) {
                            if (error.errorCode != 0) {
                                Log.d("Skill Up", "onError errorCode: " + error.errorCode)
                                Log.d("Skill Up", "onError errorBody: " + error.errorBody)
                                Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                            } else {
                                Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                            }

                            Toast.makeText(this@SignUpActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }
}