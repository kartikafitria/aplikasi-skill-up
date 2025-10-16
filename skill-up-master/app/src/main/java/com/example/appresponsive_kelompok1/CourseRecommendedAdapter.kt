package com.example.appresponsive_kelompok1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CourseRecommendedAdapter(private val list: ArrayList<CourseRecommended>): RecyclerView.Adapter<CourseRecommendedAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_course)
        val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_course_recomended, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.tvName.text = item.name
        Glide.with(holder.itemView.context).load(item.image).into(holder.ivImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCourseActivity::class.java)
            intent.putExtra("name", item.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}