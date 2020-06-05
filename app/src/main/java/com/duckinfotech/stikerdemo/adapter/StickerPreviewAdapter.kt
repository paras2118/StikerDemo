package com.duckinfotech.stikerdemo.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duckinfotech.stikerdemo.R
import com.duckinfotech.stikerdemo.domainModel.Sticker
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.sticker_image_item.view.*
import java.io.File

class StickerPreviewAdapter : ListAdapter<Sticker, StickerPreviewAdapter.ViewHolder>(DIFF_UTILL) {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private var imageView: SimpleDraweeView = item.ivSticker

        fun bind(imageUri: String) {
            Log.d("---++++", imageUri)
            // Log.d("---++++",File)
            // Glide.with(itemView).load(Uri.parse(imageUri)).into(imageView)

            imageView.setImageURI(Uri.fromFile(File(imageUri)))

        }

    }

    companion object {
        private val DIFF_UTILL = object : DiffUtil.ItemCallback<Sticker>() {
            override fun areItemsTheSame(oldItem: Sticker, newItem: Sticker): Boolean {
                return oldItem.imageFileName == newItem.imageFileName
            }

            override fun areContentsTheSame(oldItem: Sticker, newItem: Sticker): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vi =
            LayoutInflater.from(parent.context).inflate(R.layout.sticker_image_item, parent, false)
        return ViewHolder(vi)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position).imageFileName.trim())
    }
}