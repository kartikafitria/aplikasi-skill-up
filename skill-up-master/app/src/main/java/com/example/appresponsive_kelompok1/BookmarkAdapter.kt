package com.example.appresponsive_kelompok1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookmarkAdapter(private val list: ArrayList<Bookmark>): RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemRemoveBookmarkClicked(bookmark: Bookmark)
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        val ivRemoveBookmark: ImageView = itemView.findViewById(R.id.iv_remove_bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.tvName.text = item.name
        Glide.with(holder.itemView.context).load(item.image).into(holder.ivImage)

        holder.ivRemoveBookmark.setOnClickListener {
            onItemClickCallback.onItemRemoveBookmarkClicked(item)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailCourseActivity::class.java)
            intent.putExtra("name", item.name)
            holder.itemView.context.startActivity(intent)
        }
    }
}