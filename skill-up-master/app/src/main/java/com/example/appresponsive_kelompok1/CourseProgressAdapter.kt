package com.example.appresponsive_kelompok1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseProgressAdapter(private val list: ArrayList<CourseProgress>): RecyclerView.Adapter<CourseProgressAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_course)
        val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_course_in_progress, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.tvName.text = item.name
        holder.tvTime.text = item.time
        holder.ivImage.setImageResource(item.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCourseActivity::class.java)
            intent.putExtra("name", item.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}