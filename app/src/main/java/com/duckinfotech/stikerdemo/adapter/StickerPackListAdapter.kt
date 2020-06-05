package com.duckinfotech.stikerdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duckinfotech.stikerdemo.R
import com.duckinfotech.stikerdemo.StickerPackListFragmentDirections
import com.duckinfotech.stikerdemo.databinding.StickerPacksListItemBinding
import com.duckinfotech.stikerdemo.domainModel.StickerPack
import com.facebook.drawee.view.SimpleDraweeView

class StickerPackListAdapter :
    ListAdapter<StickerPack, StickerPackListAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.sticker_packs_list_item,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(private var stickerPacksListItemBinding: StickerPacksListItemBinding) :
        RecyclerView.ViewHolder(stickerPacksListItemBinding.root) {
        init {
            stickerPacksListItemBinding.setClickListenerPack { view ->
                stickerPacksListItemBinding.stickerPack?.let { stickerModel ->
                    navigateToDetailFragment(view, stickerModel)
                }
            }
        }

        fun bind(
            stickerPack: StickerPack?
        ) {
            stickerPacksListItemBinding.stickerPack = stickerPack

            stickerPack?.previewImages?.forEach {
                val rowImage: SimpleDraweeView =
                    LayoutInflater.from(stickerPacksListItemBinding.root.context).inflate(
                        R.layout.sticker_packs_list_image_item,
                        stickerPacksListItemBinding.stickerPacksListItemImageList,
                        false
                    ) as SimpleDraweeView
                rowImage.setImageURI(it!!.trim())
                stickerPacksListItemBinding.stickerPacksListItemImageList.addView(rowImage)
            }

        }


        private fun navigateToDetailFragment(
            view: View,
            stickerPack: StickerPack
        ) {
            val direction =
                StickerPackListFragmentDirections.actionStickerPackListFragmentToStickerPackDetailsFragment(
                    stickerPack.identifier,
                    stickerPack.categoryId
                )

            view.findNavController().navigate(direction)
        }

    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<StickerPack>() {
            override fun areItemsTheSame(oldItem: StickerPack, newItem: StickerPack): Boolean {
                return oldItem.identifier == newItem.identifier
            }

            override fun areContentsTheSame(oldItem: StickerPack, newItem: StickerPack): Boolean {
                return oldItem == newItem
            }
        }
    }
}