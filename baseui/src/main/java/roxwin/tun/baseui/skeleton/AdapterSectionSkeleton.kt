package roxwin.tun.baseui.skeleton

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import roxwin.tun.baseui.R
import roxwin.tun.baseui.utils.runChildAnimDrawable
import roxwin.tun.baseui.utils.runFadeInAnimation
import java.lang.IllegalStateException

class AdapterSectionSkeleton(
    val titleLayout: Int?,
    val itemLayout: Int?,
    var itemCount: Int?,
    val runFadeInAnim: Boolean
) : RecyclerView.Adapter<AdapterSectionSkeleton.SectionViewHolder>() {
    companion object {
        const val NO_ITEM_VIEW_TYPE = "No item view type found!"
    }

    val listItem by lazy { arrayListOf<Type>() }

    init {
        itemCount = itemCount ?: 13
        listItem.add(Type.TITLE)
        for (i in 1..itemCount!!) {
            listItem.add(if (i == 4) Type.TITLE else Type.ITEM)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            titleLayout ?: Type.TITLE.value -> {
                TitleViewHolder(view)
            }
            itemLayout ?: Type.ITEM.value -> {
                ItemViewHolder(view)
            }
            else -> {
                throw IllegalStateException(NO_ITEM_VIEW_TYPE)
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position]) {
            Type.TITLE -> titleLayout ?: Type.TITLE.value
            Type.ITEM -> itemLayout ?: Type.ITEM.value
            else -> {
                throw IllegalStateException(NO_ITEM_VIEW_TYPE)
            }
        }
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.onBindAndRunAnim(listItem[position], runFadeInAnim)
    }

    class ItemViewHolder(val view: View) : SectionViewHolder(view) {
        init {
            (itemView as ViewGroup).runChildAnimDrawable()
        }

        override fun onBind(type: Type) {

        }
    }

    inner class TitleViewHolder(val view: View) : SectionViewHolder(view) {
        init {
            (itemView as ViewGroup).runChildAnimDrawable()
        }

        override fun onBind(type: Type) {

        }
    }

    abstract class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBindAndRunAnim(type: Type, runAnim: Boolean) {
            Handler().postDelayed({
                (itemView as ViewGroup).forEach {
                    if (runAnim) {
                        it.runFadeInAnimation()
                    }
                }
            }, adapterPosition * AdapterSkeleton.ViewHolder.DELAY_TIME.toLong())

            onBind(type)
        }

        abstract fun onBind(type: Type)
    }

    enum class Type(@LayoutRes val value: Int) {
        TITLE(R.layout.skeleton_single_title_text_view), ITEM(R.layout.skeleton_minibar_item)
    }


}