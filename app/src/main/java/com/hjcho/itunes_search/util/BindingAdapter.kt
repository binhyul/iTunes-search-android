package com.hjcho.itunes_search.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hjcho.itunes_search.R


@BindingAdapter("android:glideSrcUrl")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).addListener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            val defaultCover =
                ResourcesCompat.getDrawable(view.resources, R.drawable.default_cover, null)
            view.setImageDrawable(defaultCover)
            return true
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            view.setImageDrawable(resource)
            return true
        }

    }).into(view)
}