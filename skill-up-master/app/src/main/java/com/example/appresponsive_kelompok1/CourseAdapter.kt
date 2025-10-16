package com.example.appresponsive_kelompok1

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import org.json.JSONObject

class CourseAdapter(private val list: ArrayList<Course>): RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemAddBookmarkClicked(course: Course)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvLevel: TextView = itemView.findViewById(R.id.tv_level)
        val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        val ivAddBoomark: ImageView = itemView.findViewById(R.id.iv_add_bookmark)
        val ivStar: ImageView = itemView.findViewById(R.id.iv_star)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.tvName.text = item.name
        holder.tvDescription.text = item.description
        holder.tvLevel.text = item.level
        Glide.with(holder.itemView.context).load(item.image).into(holder.ivImage)

        if (item.level.equals("Dasar")) {
            holder.ivStar.setImageResource(R.drawable.icon_star)
        } else if (item.level.equals("Menengah")) {
            holder.ivStar.setImageResource(R.drawable.icon_star_half)
        } else if (item.level.equals("Mahir")) {
            holder.ivStar.setImageResource(R.drawable.icon_star_full)
        }

        if (item.isBookmark) {
            holder.ivAddBoomark.setImageResource(R.drawable.icon_bookmark_added)
        }

        holder.ivAddBoomark.setOnClickListener {
            onItemClickCallback.onItemAddBookmarkClicked(item)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCourseActivity::class.java)
            intent.putExtra("name", item.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}