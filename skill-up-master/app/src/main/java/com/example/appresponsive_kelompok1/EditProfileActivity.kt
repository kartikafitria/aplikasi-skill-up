package com.example.appresponsive_kelompok1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etName: EditText = findViewById(R.id.et_name)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etPhoneNumber: EditText = findViewById(R.id.et_phone_number)
        val bSave: Button = findViewById(R.id.b_save)
        val ivBack: ImageView = findViewById(R.id.iv_back)

        val sharedPreferences = getSharedPreferences("SkillUp", MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val phoneNumber = sharedPreferences.getString("phoneNumber", "")

        etName.setText(name)
        etEmail.setText(email)
        etPhoneNumber.setText(phoneNumber)

        bSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phoneNumber = etPhoneNumber.text.toString().trim()

            if (name.isEmpty()) etName.error = "Nama harus diisi"
            if (email.isEmpty()) etEmail.error = "Email harus diisi"
            if (phoneNumber.isEmpty()) etPhoneNumber.error = "Nomor telepon harus diisi"

            if (name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()) {
                AndroidNetworking.post("$baseUrl/api/update-profile/$userId")
                    .addBodyParameter("name", name)
                    .addBodyParameter("email", email)
                    .addBodyParameter("phone_number", phoneNumber)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getString("status").equals("success")) {
                                val editor = sharedPreferences.edit()
                                editor.putString("name", name)
                                editor.putString("email", email)
                                editor.putString("phoneNumber", phoneNumber)
                                editor.apply()
                            }

                            onBackPressed()
                            Toast.makeText(this@EditProfileActivity, response.getString("message"), Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(error: ANError) {
                            if (error.errorCode != 0) {
                                Log.d("Skill Up", "onError errorCode: " + error.errorCode)
                                Log.d("Skill Up", "onError errorBody: " + error.errorBody)
                                Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                            } else {
                                Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                            }

                            Toast.makeText(this@EditProfileActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}