package net.away0x.lib_base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import net.away0x.lib_base.R

/* Glide工具类 */
object GlideUtils {

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(imageView)
    }

    /* 当 fragment 或者 activity 失去焦点或者 destroyed 的时候，Glide 会自动停止加载相关资源，确保资源不会被浪费 */
    fun loadUrlImage(context: Context, url: String, imageView: ImageView){
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.icon_back)
            .error(R.drawable.icon_back)
            .centerCrop()
            .into(object : DrawableImageViewTarget(imageView) {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    imageView.setImageDrawable(resource)
                }
            })
    }

}
