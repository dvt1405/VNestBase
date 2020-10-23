package roxwin.tun.baseui.recyclerview

import android.view.LayoutInflater
import android.view.PointerIcon
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseSectionAdapter<ITEM : BaseSectionAdapter.Item, TITLE : BaseSectionAdapter.Title>() :
    RecyclerView.Adapter<BaseViewHolder<BaseSectionAdapter.SectionViewType>>() {
    val listItem by lazy { arrayListOf<SectionViewType>() }
    private var _isFilter: Boolean = false
    private var currentFilter: String = ""
    private lateinit var _filterList: List<SectionViewType>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SectionViewType> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            itemViewRes -> {
                object : BaseViewHolder<SectionViewType>(view) {
                    override fun onBindItem(item: SectionViewType) {
                        onBindItemViewHolder(item as ITEM, itemView)
                    }

                }
            }
            titleViewRes -> {
                object : BaseViewHolder<SectionViewType>(view) {
                    override fun onBindItem(item: SectionViewType) {
                        onBindTitleViewHolder(item as TITLE, itemView)
                    }

                }
            }
            else -> throw IllegalStateException(NO_ITEM_VIEW_TYPE_FOUND)
        }
    }

    private fun getFilterList(key: String): List<SectionViewType> {
        val filterList = listItem.filter { item ->
            _isFilter = true
            currentFilter = key
            filterItem(key, item) || item is Title
        }
        return filterList.filterIndexed { index, sectionViewType ->
            sectionViewType is Item || (sectionViewType is Title && index < filterList.size - 1 && filterList[index + 1] is Item)
        }
    }


    private fun isFilter() = _isFilter && currentFilter.trim().isNotEmpty()

    fun onFilter(key: String) {
        _isFilter = true
        currentFilter = key
        _filterList = getFilterList(key)
        notifyDataSetChanged()
    }

    abstract fun filterItem(key: String, item: SectionViewType): Boolean

    override fun getItemCount(): Int = if (isFilter()) _filterList.size else listItem.size

    override fun getItemViewType(position: Int): Int {
        val listItem = if (isFilter()) _filterList else listItem
        return when (listItem[position].itemType) {
            ItemType.TITLE -> titleViewRes
            ItemType.ITEM -> itemViewRes
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<SectionViewType>, position: Int) {
        if (isFilter()) {
            holder.onBind(_filterList[position]) { item, position ->
                onItemClick(item, position, holder.itemView)
            }
        } else {
            holder.onBind(listItem[position]) { item, position ->
                onItemClick(item, position, holder.itemView)
            }
        }
    }

    abstract fun onItemClick(item: SectionViewType, position: Int, itemView: View)

    open fun onRefresh(listItem: List<SectionViewType>, shouldRefresh: Boolean = false) {
        currentFilter = ""
        _isFilter = false
        this.listItem.clear()
        this.listItem.addAll(listItem)
        if (shouldRefresh) {
            notifyDataSetChanged()
        }
    }

    open fun onRefresh(listItem: List<ITEM>) {
        onRefresh(listItem, true)
    }

    open fun onAdd(listItem: List<ITEM>) {
        this.listItem.addAll(listItem)
        notifyItemRangeChanged(this.listItem.size - listItem.size, listItem.size)
    }

    fun onDelete(listItem: List<ITEM>) {
        this.listItem.removeAll(listItem)
        notifyItemRangeChanged(this.listItem.size - listItem.size, listItem.size)
    }

    abstract fun onBindItemViewHolder(item: ITEM, itemView: View)
    abstract fun onBindTitleViewHolder(title: TITLE, itemView: View)

    interface SectionViewType {
        val itemType: ItemType
    }

    interface Item : SectionViewType {
        override val itemType
            get() = ItemType.ITEM
    }

    interface Title : SectionViewType {
        override val itemType: ItemType
            get() = ItemType.TITLE
    }

    interface FilterAdapter {
        fun onFilter(key: String)
    }


    enum class ItemType {
        TITLE, ITEM
    }

    companion object {
        const val NO_ITEM_VIEW_TYPE_FOUND = "No item view type found!"
    }

    abstract val itemViewRes: Int
    abstract val titleViewRes: Int
}