package com.haystack.news.photoapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haystack.news.photoapp.R
import com.haystack.news.photoapp.domain.model.ViewPhoto
import com.haystack.news.photoapp.utils.StringUtils

class PhotoRVAdapter(
    private val photoList: ArrayList<ViewPhoto>,
    private val context: Context
): RecyclerView.Adapter<PhotoRVAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)

        val params = itemView.layoutParams
        params.width = getWidthInPercent(context, 33)
        params.height = getHeightInPercent(context, 32)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.photoTitle.text = photoList[position].title
        holder.photoSubtitle.text = photoList[position].subtitle
        Glide.with(holder.itemView.context!!)
            .load(photoList[position].url)
            .centerCrop()
            .into(holder.photoImg)

    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun getWidthInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.widthPixels
        return (width * percent) / 100
    }

    fun getHeightInPercent(context: Context, percent: Int): Int {
        val width = context.resources.displayMetrics.heightPixels ?: 0
        return (width * percent) / 100
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val photoSubtitle: TextView = itemView.findViewById(R.id.txtSubtitle)
        val photoImg: ImageView = itemView.findViewById(R.id.imgPhoto)
    }
}