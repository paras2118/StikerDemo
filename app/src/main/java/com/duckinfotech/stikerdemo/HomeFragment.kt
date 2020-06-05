package com.duckinfotech.stikerdemo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.duckinfotech.stikerdemo.adapter.HomeCatAdapter
import com.duckinfotech.stikerdemo.databinding.FragmentHomeBinding
import com.duckinfotech.stikerdemo.viewModel.HomeViewModelNew

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModelNew: HomeViewModelNew

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModelNew = ViewModelProviders.of(this).get(HomeViewModelNew::class.java)
        binding.recycleView.adapter = HomeCatAdapter()
    }

    override fun onStart() {
        super.onStart()

        homeViewModelNew.categoryAndPack.observe(
            viewLifecycleOwner,
            Observer { stickerCategoryList ->
                stickerCategoryList.forEach { stickerCategory ->
                    Log.d(TAG, stickerCategory.title)
                    Log.d(TAG, "${stickerCategory.stickerPackList.size}")
                    stickerCategory.stickerPackList.forEach {
                        Log.d(TAG, it.identifier)
                    }
                }
                with(binding.recycleView!!.adapter!! as HomeCatAdapter) {
                    submitList(stickerCategoryList)
                }
            })
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        const val TAG = "HomeFragment"
    }

}
