package roxwin.tun.baseui

import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class AltitudeGlide : AppGlideModule() {
    companion object {
        const val RELOAD_IMAGE = 1
        const val http: String = "http://"
        const val https: String = "https://"
        var hrefReferer: String = "https://alt-admin.dev.altitudehq.com/maintenance"
        var hostDef: String = "alt-fileservice-private-dev.s3.amazonaws.com"
        val handler: Handler by lazy {
            Handler {
                when (it.what) {
                    RELOAD_IMAGE -> {
                        val loadImage = it.obj as RetryLoadImage
                        val isHttp = loadImage.url?.contains(http) ?: false
                        val isHttps = loadImage.url?.contains(https) ?: false
                        val host =
                            loadImage.url!!.replace(if (isHttp) http else https, "").split("/")[0]
                        loadImage.host = hostDef
                        loadImage.referer = hrefReferer
                        loadImage.retryTime++
                        if (loadImage.retryTime <= 1) {
                            val glideUrl = createRetryGlideUrl(loadImage.url!!, loadImage.host!!, loadImage.referer!!)
                            GlideApp.with(loadImage.imageView)
                                .load(glideUrl)
                                .error(loadImage.error)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .thumbnail(0.5f)
                                .into(loadImage.imageView)
                        }
                    }
                }
                return@Handler true
            }
        }

        private fun createRetryGlideUrl(url: String, host: String, referer: String): GlideUrl {
            return GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader("accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                    .addHeader("Accept-Encoding", "gzip, deflate, br")
                    .addHeader(
                        "Accept-Language",
                        "vi-VN,vi;q=0.9,fr-FR;q=0.8,fr;q=0.7,en-US;q=0.6,en;q=0.5,am;q=0.4,en-AU;q=0.3"
                    )
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Host", host)
                    .addHeader("Referer", referer)
                    .addHeader("Sec-Fetch-Dest","image")
                    .addHeader("Sec-Fetch-Site","cross-site")
                    .addHeader("Sec-Fetch-Mode","no-cors")
                    .build()

            )
        }
    }

    data class RetryLoadImage(
        var imageView: ImageView,
        var url: String?,
        var error: Int = R.drawable.image_place_holder_top_8,
        var referer: String? = hrefReferer,
        var retryTime: Int = 0
    ) {
        var host: String? = null
    }

}