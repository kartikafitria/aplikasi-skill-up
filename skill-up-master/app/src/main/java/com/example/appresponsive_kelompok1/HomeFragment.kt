package com.example.appresponsive_kelompok1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import org.json.JSONArray
import java.util.Calendar
import java.util.Date

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val rvCourseProgress: RecyclerView = view.findViewById(R.id.rv_course_progress)
        val rvCourseRecommended: RecyclerView = view.findViewById(R.id.rv_course_recommended)
        val tvWelcome: TextView = view.findViewById(R.id.tv_welcome)
        val tvGreeting: TextView = view.findViewById(R.id.tv_greeting)
        val listCourseProgress: ArrayList<CourseProgress> = arrayListOf()
        val listCourseRecommended: ArrayList<CourseRecommended> = arrayListOf()

        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.setTime(date)
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)

        val greeting = if(hour>= 12 && hour < 17) "Good Afternoon"
            else if(hour >= 17 && hour < 21) "Good Evening"
            else if(hour >= 21 && hour < 24) "Good Night"
            else "Good Morning"

        tvGreeting.text = greeting

        val sharedPreferences = requireContext().getSharedPreferences("SkillUp", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")

        tvWelcome.text = "Hi $name!"

        val courseNames = arrayOf("UI/UX Design", "Data Science", "ML")
        val courseImages = arrayOf(R.drawable.image_9, R.drawable.image_3, R.drawable.image_8)
        val courseTimes = arrayOf("5h 50min", "3h 10min", "1h 30min")

        for (position in 0 until courseNames.size) {
            val courseProgress = CourseProgress()
            courseProgress.name = courseNames[position]
            courseProgress.image = courseImages[position]
            courseProgress.time = courseTimes[position]
            listCourseProgress.add(courseProgress)
        }

        rvCourseProgress.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCourseProgress.adapter = CourseProgressAdapter(listCourseProgress)

        AndroidNetworking.get("$baseUrl/api/courses")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (position in 0 until response.length()) {
                        val item = response.getJSONObject(position)

                        val courseRecommended = CourseRecommended()
                        courseRecommended.name = item.getString("nama")
                        courseRecommended.image = "$baseUrl/storage/" + item.getString("gambar")
                        listCourseRecommended.add(courseRecommended)
                    }

                    listCourseRecommended.shuffle()
                    rvCourseRecommended.layoutManager = LinearLayoutManager(context)
                    rvCourseRecommended.adapter = CourseRecommendedAdapter(listCourseRecommended)
                }

                override fun onError(error: ANError) {
                    if (error.errorCode != 0) {
                        Log.d("Skill Up", "onError errorCode: " + error.errorCode)
                        Log.d("Skill Up", "onError errorBody: " + error.errorBody)
                        Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                    } else {
                        Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                    }

                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })

        return view
    }
}