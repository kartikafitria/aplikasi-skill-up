package com.example.appresponsive_kelompok1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LessonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_lesson, container, false)

        val rvLesson: RecyclerView = view.findViewById(R.id.rv_lesson)
        val listLesson: ArrayList<Lesson> = arrayListOf()

        val lessonNames = arrayOf("Introduction to UI/UX", "UI/UX Tools for Succes", "UX Project: Let’s Create a Responsive Website", "Futher UI/UX Essensials", "UI UX Essential Vocabulary")
        val lessonNumbers = arrayOf("01", "02", "03", "04", "05")

        for (position in 0 until lessonNames.size) {
            val lesson = Lesson()
            lesson.name = lessonNames[position]
            lesson.number = lessonNumbers[position]
            listLesson.add(lesson)
        }
        rvLesson.layoutManager = LinearLayoutManager(context)
        rvLesson.adapter = LessonAdapter(listLesson)

        return view
    }
}