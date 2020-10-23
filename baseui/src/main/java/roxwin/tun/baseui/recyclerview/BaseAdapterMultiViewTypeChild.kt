package roxwin.tun.baseui.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapterMultiViewTypeChild<T : BaseAdapter.ViewType> : BaseAdapter<T>() {
}

//abstract class BaseSectionAdapter<ITEM : BaseAdapter.Item, TITLE : BaseAdapter.Title> : BaseAdapter<BaseAdapter.ViewType>() {
//    override fun onBindItem(item: ViewType, itemView: View) {
//        when (item) {
//            is Item -> {
//                onBindItem(item as ITEM)
//            }
//            is Title -> {
//                onBindTitle(item as TITLE)
//            }
//        }
//    }
//
//    abstract fun onBindItem(item: ITEM)
//    abstract fun onBindTitle(title: TITLE)
//}