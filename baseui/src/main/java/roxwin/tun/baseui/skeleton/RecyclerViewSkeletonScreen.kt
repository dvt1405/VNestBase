package roxwin.tun.baseui.skeleton

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import roxwin.tun.baseui.utils.runLayoutAnimation

class RecyclerViewSkeletonScreen(
    val recyclerView: RecyclerView,
    val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
    val duration: Long?,
    val layoutRes: Int?,
    val titleLayout: Int?,
    val itemCount: Int?,
    val runLayoutAnimation: Boolean,
    val isSectionList: Boolean,
    layoutManager: RecyclerView.LayoutManager?,
    val showAsGridLayoutManager: Boolean,
    val numGridLayout: Int
) : AltitudeSkeleton.SkeletonScreen {
    private val adapterSkeleton by lazy {
        try {
            if (isSectionList) {
                AdapterSectionSkeleton(titleLayout, layoutRes, itemCount, false)
            } else {
                AdapterSkeleton(layoutRes!!, itemCount)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private val layoutManager: RecyclerView.LayoutManager?

    init {
        if (layoutManager == null) {
            this.layoutManager = recyclerView.layoutManager
        } else {
            this.layoutManager = layoutManager
        }
    }

    override fun run(onRunListener: OnSkeletonRunListener?) {
        if (showAsGridLayoutManager) {
            recyclerView.layoutManager = GridLayoutManager(recyclerView.context, numGridLayout)
        } else {
            when (layoutManager) {
                is GridLayoutManager, is GridLayoutManager? -> {
                    recyclerView.layoutManager = layoutManager
                }
                is LinearLayoutManager, is LinearLayoutManager? -> {
                    recyclerView.layoutManager =
                        object : LinearLayoutManager(recyclerView.context) {
                            override fun canScrollVertically(): Boolean {
                                return false
                            }

                            override fun canScrollHorizontally(): Boolean {
                                return false
                            }
                        }
                }

            }
        }
        recyclerView.adapter = adapterSkeleton
        if (runLayoutAnimation) {
            recyclerView.runLayoutAnimation()
        }
        onRunListener?.onRun()
    }


    override fun hide(onRunListener: OnSkeletonRunListener?) {
        recyclerView.adapter = adapter
        recyclerView.runLayoutAnimation()
        recyclerView.layoutManager = layoutManager
        onRunListener?.let { it.onRun() }
    }


    class Builder(private val recyclerView: RecyclerView) {
        private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
        private var duration: Long? = null
        private var layoutItem: Int? = null
        private var itemCount: Int? = null
        private var runLayoutAnimation: Boolean = true
        private var layoutManager: RecyclerView.LayoutManager? = null
        private var isSectionRecyclerView: Boolean = false
        private var titleLayout: Int? = null
        private var isShowAsGridLayoutManager: Boolean = false
        private var numGridLayoutColumn = 1

        fun layoutManager(layoutManager: RecyclerView.LayoutManager): Builder {
            this.layoutManager = layoutManager
            return this
        }

        fun adapter(adapter: Any?): Builder {
            this.adapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>?
            return this
        }

        fun duration(duration: Long): Builder {
            this.duration = duration
            return this
        }

        fun showAsGridLayoutManager(shouldShow: Boolean, numColumn: Int = 2): Builder {
            this.isShowAsGridLayoutManager = shouldShow
            this.numGridLayoutColumn = numColumn
            return this
        }

        fun layoutItem(@LayoutRes layoutRes: Int): Builder {
            this.layoutItem = layoutRes
            return this
        }

        fun itemCount(itemCount: Int): Builder {
            this.itemCount = itemCount
            return this
        }

        fun runLayoutAnimation(runLayoutAnimation: Boolean): Builder {
            this.runLayoutAnimation = runLayoutAnimation
            return this
        }

        fun isSectionList(isSection: Boolean): Builder {
            this.isSectionRecyclerView = isSection
            return this
        }

        fun titleLayout(layoutRes: Int?): Builder {
            this.titleLayout = layoutRes
            return this
        }

        fun build(): RecyclerViewSkeletonScreen = RecyclerViewSkeletonScreen(
            recyclerView,
            adapter,
            duration,
            layoutItem,
            titleLayout,
            itemCount,
            runLayoutAnimation,
            isSectionRecyclerView,
            layoutManager,
            isShowAsGridLayoutManager,
            numGridLayoutColumn
        )

        fun run(): RecyclerViewSkeletonScreen = build().also {
            it.run()
        }
    }
}