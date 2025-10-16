package com.example.appresponsive_kelompok1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_course)

        val tvAbout: TextView = findViewById(R.id.tv_about)
        val tvLesson: TextView = findViewById(R.id.tv_lesson)
        val tvNameTop: TextView = findViewById(R.id.tv_name_top)
        val tvName: TextView = findViewById(R.id.tv_name)
        val ivBack: ImageView = findViewById(R.id.iv_back)

//        tvNameTop.text = intent.getStringExtra("name")
//        tvName.text = intent.getStringExtra("name")

        ivBack.setOnClickListener {
            onBackPressed()
        }

        replaceFragemnt(AboutFragment())

        tvAbout.setOnClickListener {
            replaceFragemnt(AboutFragment())
            tvAbout.setBackgroundResource(R.drawable.selector_10)
            tvAbout.setTextColor(Color.parseColor("#FFFFFF"))
            tvLesson.setBackgroundColor(Color.parseColor("#FFFFFF"))
            tvLesson.setTextColor(Color.parseColor("#0F273F"))
        }

        tvLesson.setOnClickListener {
            replaceFragemnt(LessonFragment())
            tvLesson.setBackgroundResource(R.drawable.selector_10)
            tvLesson.setTextColor(Color.parseColor("#FFFFFF"))
            tvAbout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            tvAbout.setTextColor(Color.parseColor("#0F273F"))
        }
    }

    private fun replaceFragemnt(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.detail_course_fragment_container_view, fragment).commit()
    }
}