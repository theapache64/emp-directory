package com.sifar.whiterabbit.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sifar.whiterabbit.R

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 18:27
 */

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView)
        .load(imageUrl)
        .error(R.drawable.ic_no_photo)
        .into(imageView)
}