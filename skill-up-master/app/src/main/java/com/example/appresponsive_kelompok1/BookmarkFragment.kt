package com.example.appresponsive_kelompok1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

class BookmarkFragment : Fragment() {
    private lateinit var listBookmark: ArrayList<Bookmark>
    private lateinit var rvBookmark: RecyclerView
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_bookmark, container, false)

        rvBookmark = view.findViewById(R.id.rv_bookmark)
        listBookmark = arrayListOf()
        bookmarkAdapter = BookmarkAdapter(listBookmark)

        val sharedPreferences = requireContext().getSharedPreferences("SkillUp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")!!

        getBookmarks(userId)

        bookmarkAdapter.setOnItemClickCallback(object : BookmarkAdapter.OnItemClickCallback {
            override fun onItemRemoveBookmarkClicked(bookmark: Bookmark) {
                AndroidNetworking.post("$baseUrl/api/remove-bookmark/${bookmark.id}")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getString("status").equals("success")) {
                                getBookmarks(userId)
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
            }
        })

        return view
    }

    private fun getBookmarks(userId: String) {
        AndroidNetworking.get("$baseUrl/api/bookmark/$userId")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    listBookmark.clear()

                    for (position in 0 until response.length()) {
                        val item = response.getJSONObject(position)
                        val course = item.getJSONObject("course")

                        val bookmark = Bookmark()
                        bookmark.id = item.getInt("id")
                        bookmark.name = course.getString("nama")
                        bookmark.image = "$baseUrl/storage/" + course.getString("gambar")
                        listBookmark.add(bookmark)
                    }

                    rvBookmark.layoutManager = GridLayoutManager(context, 2)
                    rvBookmark.adapter = bookmarkAdapter
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