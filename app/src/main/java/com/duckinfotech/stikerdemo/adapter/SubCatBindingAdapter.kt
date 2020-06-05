package com.duckinfotech.stikerdemo.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("previewImage")
fun loadIamge(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.load(imageUrl?.trim())
    }
    /*  if (!imageUrl.isNullOrEmpty()) {
          Glide.with(view.context)
              .load(imageUrl.trim())
              .transition(DrawableTransitionOptions.withCrossFade())
              .into(view)
      }*/
}
