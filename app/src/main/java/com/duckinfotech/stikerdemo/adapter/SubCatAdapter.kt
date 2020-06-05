package com.duckinfotech.stikerdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duckinfotech.stikerdemo.HomeFragmentDirections
import com.duckinfotech.stikerdemo.R
import com.duckinfotech.stikerdemo.databinding.RecyclerViewSubItemBinding
import com.duckinfotech.stikerdemo.domainModel.StickerPack

class SubCatAdapter : ListAdapter<StickerPack, SubCatAdapter.ViewHolder>(DIF_UTILS) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_sub_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val recyclerViewSubItemBinding: RecyclerViewSubItemBinding) :
        RecyclerView.ViewHolder(recyclerViewSubItemBinding.root) {

        init {
            recyclerViewSubItemBinding.setClickListenerPack { view ->
                recyclerViewSubItemBinding.stickerPack?.let { stickerModel ->
                    navigateToDetailFragment(view, stickerModel)
                }
            }
        }

        private fun navigateToDetailFragment(
            view: View,
            stickerPack: StickerPack
        ) {
            val direction = HomeFragmentDirections.actionHomeFragmentToStickerPackDetailsFragment(
                stickerPack.identifier,
                stickerPack.categoryId
            )

            view.findNavController().navigate(direction)
        }

        fun bind(sticker: StickerPack) {
            recyclerViewSubItemBinding.stickerPack = sticker
        }
    }


    companion object {
        val DIF_UTILS = object : DiffUtil.ItemCallback<StickerPack>() {
            override fun areItemsTheSame(oldItem: StickerPack, newItem: StickerPack): Boolean {
                return oldItem.identifier == newItem.identifier
            }

            override fun areContentsTheSame(oldItem: StickerPack, newItem: StickerPack): Boolean {
                return oldItem == newItem
            }

        }

    }

}