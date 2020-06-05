package com.duckinfotech.stikerdemo


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.duckinfotech.stikerdemo.adapter.StickerPreviewAdapter
import com.duckinfotech.stikerdemo.databinding.FragmentStickerPackDetailsBinding
import com.duckinfotech.stikerdemo.domainModel.StickerPack
import com.duckinfotech.stikerdemo.viewModel.PackDetailsVieModelFactoryNew
import com.duckinfotech.stikerdemo.viewModel.PackDetailsViewModelNew

/**
 * A simple [Fragment] subclass.
 */
class StickerPackDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStickerPackDetailsBinding
    private lateinit var packDetailsViewModelNew: PackDetailsViewModelNew

    private val args: StickerPackDetailsFragmentArgs by navArgs()

    private var stickerPack: StickerPack? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStickerPackDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pdvmfNew = PackDetailsVieModelFactoryNew(args.identifier, args.catId)
        packDetailsViewModelNew =
            ViewModelProviders.of(this, pdvmfNew).get(PackDetailsViewModelNew::class.java)
        binding.stickerList.adapter = StickerPreviewAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        packDetailsViewModelNew.stickerPack.observe(this, Observer {
            stickerPack = it
            binding.author.text = it.publisher
            binding.packName.text = it.identifier
            binding.packSize.text = "${it.stickersCount}"

        })

        packDetailsViewModelNew.stickers.observe(this, Observer {
            with(binding.stickerList.adapter as StickerPreviewAdapter) {
                Log.d("------PackDetail----", "${it.size}")
                submitList(it)
            }
        })

        binding.btnAddSticker.setOnClickListener {
            launchIntentToAddPackToSpecificPackage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, data?.extras?.get("validation_error").toString())
    }

    private fun launchIntentToAddPackToSpecificPackage() {
        val intent = createIntentToAddStickerPack().also {
            it.setPackage("com.whatsapp")
        }

        try {
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            Log.e("###", "Start sticker add activity failed", e)
        }
    }

    private fun createIntentToAddStickerPack(): Intent {
        return Intent().apply {
            action = "com.whatsapp.intent.action.ENABLE_STICKER_PACK"
            putExtra("sticker_pack_id", stickerPack!!.identifier)
            putExtra("sticker_pack_authority", BuildConfig.CONTENT_PROVIDER_AUTHORITY)
            putExtra("sticker_pack_name", stickerPack!!.name)

        }
    }

    companion object {
        const val TAG = "StickerPackDetailsFragment"
    }
}
