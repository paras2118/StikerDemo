package com.duckinfotech.stikerdemo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duckinfotech.stikerdemo.HomeFragmentDirections
import com.duckinfotech.stikerdemo.R
import com.duckinfotech.stikerdemo.databinding.RecyclerViewItemBinding
import com.duckinfotech.stikerdemo.domainModel.StickerCategory

class HomeCatAdapter :
    ListAdapter<StickerCategory, HomeCatAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var viewPool = RecyclerView.RecycledViewPool()


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d("#####", "Attach ho gaya hai")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item,
                parent,
                false
            ),
            viewPool
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val recyclerViewItemBinding: RecyclerViewItemBinding,
        private val pool: RecyclerView.RecycledViewPool
    ) :
        RecyclerView.ViewHolder(recyclerViewItemBinding.root) {

        init {
            recyclerViewItemBinding.setClickListenerMore {
                navigateToMore(it, recyclerViewItemBinding.categories!!)
            }
        }

        fun bind(cat: StickerCategory) {
            recyclerViewItemBinding.categories = cat
            recyclerViewItemBinding.rvNested!!.apply {
                setRecycledViewPool(pool)
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = SubCatAdapter().also {
                    it.submitList(cat.stickerPackList)
                }
            }
        }

        private fun navigateToMore(
            it: View,
            categories: StickerCategory
        ) {
            /*     val direction = object : NavDirections {
                     override fun getArguments(): Bundle {
                         return bundleOf(Pair("a", "a"))
                     }

                     override fun getActionId(): Int =
                         R.id.action_homeFragment_to_stickerPackListFragment
                 }*/

            val direction = HomeFragmentDirections.actionHomeFragmentToStickerPackListFragment(
                categories.searchTerm!!
            )

            it.findNavController().navigate(direction)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StickerCategory>() {
            override fun areItemsTheSame(
                oldItem: StickerCategory,
                newItem: StickerCategory
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StickerCategory,
                newItem: StickerCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}