package roxwin.tun.baseui.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import roxwin.tun.baseui.R
import roxwin.tun.baseui.utils.convertDpToPixel
import kotlin.math.roundToInt

class CustomDivider(
    val context: Context,
    val orientation: Int,
    private val margin: Float = 16f,
    private val useDefault: Boolean = false
) : DividerItemDecoration(context, orientation) {
    private var customDrawable: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.dividers_item_decoration)!!

    override fun setDrawable(drawable: Drawable) {
        super.setDrawable(drawable)
        customDrawable = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == VERTICAL) {
            c.save()
            val leftMargin = convertDpToPixel(margin, context)
            val right: Int
            val left: Int
            if (parent.clipToPadding) {
                left = (parent.paddingLeft + leftMargin).toInt()
                right = parent.width - parent.paddingRight
                c.clipRect(
                    left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom
                )
            } else {
                left = leftMargin.toInt()
                right = parent.width
            }
            val mFieldBounds = DividerItemDecoration::class.java.getDeclaredField("mBounds")
            val mDrawable = DividerItemDecoration::class.java.getDeclaredField("mDivider")
            mDrawable.isAccessible = true
            mFieldBounds.isAccessible = true
            val mBounds = mFieldBounds.get(this) as Rect
            val mDivider = if (useDefault) mDrawable.get(this) as Drawable else customDrawable!!
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + child.translationY.roundToInt()
                val top = bottom - convertDpToPixel(1f, context).roundToInt()
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
            c.restore()
        } else {
            super.onDraw(c, parent, state)
        }
    }

}