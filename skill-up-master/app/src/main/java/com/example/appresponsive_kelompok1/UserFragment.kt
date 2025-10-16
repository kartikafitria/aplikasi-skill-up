package com.example.appresponsive_kelompok1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONArray
import org.json.JSONObject


class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val etName: EditText = view.findViewById(R.id.et_name)
        val etEmail: EditText = view.findViewById(R.id.et_email)
        val etPhoneNumber: EditText = view.findViewById(R.id.et_phone_number)

        val sharedPreferences = requireContext().getSharedPreferences("SkillUp", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val phoneNumber = sharedPreferences.getString("phoneNumber", "")

        etName.setText(name)
        etEmail.setText(email)
        etPhoneNumber.setText(phoneNumber)

        return view
    }
}