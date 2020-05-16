package com.example.android.covid19tracker.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("app:srcCompat")
fun setSrcCompat(view: ImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}
