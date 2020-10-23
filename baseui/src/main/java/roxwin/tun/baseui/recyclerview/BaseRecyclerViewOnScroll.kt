package roxwin.tun.baseui.recyclerview

import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerViewOnScroll : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        recyclerView.forEachVisibleHolder { holder: BaseViewHolder<Any> ->
            holder.rotation
                .setStartVelocity(holder.currentVelocity - dx * RecyclerViewConstant.SCROLL_ROTATION_MAGNITUDE)
                .start()
        }
    }
}