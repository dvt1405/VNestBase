package roxwin.tun.baseui.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import roxwin.tun.baseui.R

object Utils {
    const val WIDTH_INDEX = 0
    const val HEIGHT_INDEX = 1
    fun getScreenSize(context: Context): IntArray {
        val size = Point()
        val widthHeightScreen = IntArray(2)
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val disPlay = windowManager.defaultDisplay
        widthHeightScreen[WIDTH_INDEX] = 0
        widthHeightScreen[HEIGHT_INDEX] = 1
        disPlay.getSize(size)
        widthHeightScreen[WIDTH_INDEX] = size.x
        widthHeightScreen[HEIGHT_INDEX] = size.y
        if (isScreenSizeRetrieved(widthHeightScreen)) {

        }
        return widthHeightScreen
    }

    fun isScreenSizeRetrieved(widthHeightScreen: IntArray): Boolean =
        widthHeightScreen[WIDTH_INDEX] != 0 && widthHeightScreen[HEIGHT_INDEX] != 0
}

fun View.setVisible(shouldVisible: Boolean) {
    this.visibility = if (shouldVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.setVisible(shouldVisible: Boolean?) {
    if (shouldVisible == null) {
        this.visibility = View.GONE
        return
    }
    if (shouldVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun SwipeRefreshLayout.setColorSchemaDefault() {
    setColorSchemeColors(
        Color.DKGRAY,
        ContextCompat.getColor(context, R.color.accent),
        Color.GREEN,
        ContextCompat.getColor(context, R.color.colorPrimaryDark)
    )
}
