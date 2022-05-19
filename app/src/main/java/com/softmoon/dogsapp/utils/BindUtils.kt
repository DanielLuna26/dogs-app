package com.softmoon.dogsapp.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.softmoon.dogsapp.R

@SuppressLint("CheckResult")
@BindingAdapter("app:loadImgUrl")
fun loadImgUrl(imgView: ShapeableImageView, url: String?) {
    url?.let {
        val requestOptions = RequestOptions()
        requestOptions.error(R.drawable.ic_without_img)
        requestOptions.placeholder(R.drawable.ic_load)

        Glide
            .with(imgView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(it)
            .into(imgView)
    }
}

@BindingAdapter("app:visibility")
fun setVisibility(view: View, isVisible: Boolean?) {
    isVisible?.let {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}