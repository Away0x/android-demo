package net.away0x.lib_base.widgets

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader
import net.away0x.lib_base.utils.GlideUtils

/* Banner图片加载器 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideUtils.loadUrlImage(context, path.toString(), imageView)
    }
}
