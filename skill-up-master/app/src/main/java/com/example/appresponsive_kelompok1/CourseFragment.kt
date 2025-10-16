package com.example.appresponsive_kelompok1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONArray
import org.json.JSONObject

class CourseFragment : Fragment() {
    private lateinit var listCourse: ArrayList<Course>
    private lateinit var listBookmark: ArrayList<Bookmark>
    private lateinit var rvCourse: RecyclerView
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_course, container, false)

        rvCourse = view.findViewById(R.id.rv_course)
        listCourse = arrayListOf()
        listBookmark= arrayListOf()
        courseAdapter = CourseAdapter(listCourse)

        val sharedPreferences = requireContext().getSharedPreferences("SkillUp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")!!

        getCourses(userId)

        courseAdapter.setOnItemClickCallback(object : CourseAdapter.OnItemClickCallback{
            override fun onItemAddBookmarkClicked(course: Course) {
                if (course.isBookmark) {
                    AndroidNetworking.post("$baseUrl/api/remove-bookmark/${course.bookmarkId}")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {
                                if (response.getString("status").equals("success")) {
                                    getCourses(userId)
                                    Toast.makeText(context, "Remove bookmark successfully", Toast.LENGTH_SHORT).show()
                                }
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
                } else {
                    AndroidNetworking.post("$baseUrl/api/bookmark/$userId/${course.id}")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {
                                if (response.getString("status").equals("success")) {
                                    getCourses(userId)
                                    Toast.makeText(
                                        context,
                                        "Add bookmark successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onError(error: ANError) {
                                if (error.errorCode != 0) {
                                    Log.d("Skill Up", "onError errorCode: " + error.errorCode)
                                    Log.d("Skill Up", "onError errorBody: " + error.errorBody)
                                    Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                                } else {
                                    Log.d("Skill Up", "onError errorDetail: " + error.errorDetail)
                                }

                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }
            }
        })

        return view
    }

    private fun getCourses(userId: String) {
        AndroidNetworking.get("$baseUrl/api/courses")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    AndroidNetworking.get("$baseUrl/api/bookmark/$userId")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONArray(object : JSONArrayRequestListener {
                            override fun onResponse(responseBookmark: JSONArray) {
                                listBookmark.clear()
                                listCourse.clear()

                                for (position in 0 until responseBookmark.length()) {
                                    val itemBookmark = responseBookmark.getJSONObject(position)
                                    val course = itemBookmark.getJSONObject("course")

                                    val bookmark = Bookmark()
                                    bookmark.id = itemBookmark.getInt("id")
                                    bookmark.courseId = course.getInt("id")
                                    listBookmark.add(bookmark)
                                }

                                for (position in 0 until response.length()) {
                                    val item = response.getJSONObject(position)

                                    val course = Course()
                                    course.id = item.getInt("id")
                                    course.name = item.getString("nama")
                                    course.description = item.getString("deskripsi")
                                    course.level = item.getString("level")
                                    course.image = "$baseUrl/storage/" + item.getString("gambar")
                                    course.isBookmark = listBookmark.any { it.courseId == course.id }
                                    if (listBookmark.any { it.courseId == course.id }) course.bookmarkId = listBookmark.find { it.courseId == course.id }!!.id
                                    listCourse.add(course)
                                }

                                rvCourse.layoutManager = LinearLayoutManager(context)
                                rvCourse.adapter = courseAdapter
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
    }
}