package com.duckinfotech.stikerdemo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.duckinfotech.stikerdemo.adapter.StickerPackListAdapter
import com.duckinfotech.stikerdemo.databinding.FragmentSearchStickerPackBinding
import com.duckinfotech.stikerdemo.viewModel.PackListViewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchStickerPackFragment : Fragment() {

    private lateinit var packListViewModel: PackListViewModel
    private lateinit var binding: FragmentSearchStickerPackBinding
    private val args: StickerPackListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchStickerPackBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        packListViewModel = ViewModelProviders.of(this)[PackListViewModel::class.java]
        binding.stickerPackList.adapter = StickerPackListAdapter()
        Toast.makeText(requireContext(), args.searchTerm, Toast.LENGTH_LONG).show()

        packListViewModel.searchOnList(args.searchTerm.split("|"))

        packListViewModel.searchList.observe(this, Observer { stickerPackList ->
            with(binding.stickerPackList.adapter as StickerPackListAdapter) {
                submitList(stickerPackList)
            }
            stickerPackList.forEach {
                Log.d(TAG, it.identifier)
            }
        })
    }

    companion object {
        const val TAG = "StickerPackListFragment"
    }

}
