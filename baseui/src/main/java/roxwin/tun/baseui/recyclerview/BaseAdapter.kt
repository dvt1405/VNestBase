package roxwin.tun.baseui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import roxwin.tun.baseui.R
import java.lang.IllegalStateException

/**
 * With Single view type list: override layoutRes while init base adapter
 * With Multi view type lis: item view have to implement ViewType interface
 *                         ex: T:ViewType
 * **/
abstract class BaseAdapter<T>(
    open val layoutRes: Int? = null,
    open val onItemClick: ((item: T, position: Int) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    val listItem by lazy { arrayListOf<T>() }

    open fun onRefresh(_listItem: List<T>?, shouldNotifyAdapterChange: Boolean = true) {
        listItem.clear()
        _listItem?.let {
            listItem.addAll(it)

        }
        if (shouldNotifyAdapterChange) {
            notifyDataSetChanged()
        }
    }

    open fun onAdd(_listItem: List<T>, shouldRNotifyAdapterChange: Boolean = true) {
        listItem.addAll(_listItem)
        if (shouldRNotifyAdapterChange) {
            notifyItemInserted(listItem.size - _listItem.size)
        }
    }

    open fun onAdd(item: T, position: Int = listItem.size) {
        listItem.add(position, item)
        notifyItemInserted(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItem[position] is ViewType) {
            (listItem[position] as ViewType).layoutRes!!
        } else {
            layoutRes ?: throw IllegalStateException("No item layoutRes found")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return object : BaseViewHolder<T>(view) {
            override fun onBindItem(item: T) {
                onBindItem(item, itemView)
            }

        }
    }

    abstract fun onBindItem(item: T, itemView: View)

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(listItem[position], onItemClick)
    }

    /**
     *
     * */
    interface ViewType {
        val layoutRes: Int?
    }

    interface Title : ViewType {
        override val layoutRes: Int
            get() = R.layout.item_base_section_title
    }
    interface Item :ViewType
}

inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(action: (T) -> Unit) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}