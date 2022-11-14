package com.example.android.covid19tracker.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android.covid19tracker.R


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("app:srcCompat")
fun setSrcCompat(view: ImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}

@BindingAdapter("app:loadingStatus")
fun setLoadingStatus(view: ImageView, status: LoadingStatus?) {
    when (status) {
        LoadingStatus.LOADING -> {
            view.visibility = View.VISIBLE
            view.setImageResource(R.drawable.loading_animation)
        }
        LoadingStatus.DONE -> {
            view.visibility = View.GONE
        }
        else -> {
            view.visibility = View.VISIBLE
            view.setImageResource(R.drawable.ic_connection_error)
        }
    }
}