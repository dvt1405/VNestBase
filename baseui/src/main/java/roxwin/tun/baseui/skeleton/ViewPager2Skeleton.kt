package roxwin.tun.baseui.skeleton

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import roxwin.tun.baseui.R

class ViewPager2Skeleton(
    val viewpager: ViewPager2,
    val tabLayout: TabLayout,
    val actualAdapter: FragmentStateAdapter?,
    val fragment: Fragment,
    val tabLayoutRes: Int,
    val itemLayoutRes: Int
) : AltitudeSkeleton.SkeletonScreen {
    private var defItemCount: Int = 4
    private val skeletonAdapter by lazy {
        object : FragmentStateAdapter(fragment) {
            override fun getItemCount(): Int = defItemCount
            override fun createFragment(position: Int): Fragment = ViewPagerSkeleton.DefFragment(itemLayoutRes)
        }
    }
    private val skeletonTabLayout by lazy {
        AltitudeSkeleton.bind(tabLayout)
            .layout(tabLayoutRes)
            .build()
    }

    class Builder(val viewpager: ViewPager2, val tabLayout: TabLayout, val fragment: Fragment) {
        private var adapter: FragmentStateAdapter? = null
        private var tabBarLayoutRes: Int = R.layout.skeleton_tab_layout
        private var viewPagerItemRes: Int = R.layout.skeleton_minibar_item
        fun adapter(adapter: FragmentStateAdapter?): Builder {
            this.adapter = adapter
            return this
        }

        fun tabLayout(@LayoutRes layout: Int?): Builder {
            layout?.let {
                this.tabBarLayoutRes = it
            }
            return this
        }

        fun fragmentItemLayout(@LayoutRes layout: Int?): Builder {
            layout?.let {
                this.viewPagerItemRes = it
            }
            return this
        }

        fun build(): ViewPager2Skeleton = ViewPager2Skeleton(viewpager, tabLayout, adapter, fragment, tabBarLayoutRes, viewPagerItemRes)
        fun run() = build().run()
        fun run(onRunListener: OnSkeletonRunListener?) = build().run(onRunListener)
    }

    override fun run(onRunListener: OnSkeletonRunListener?) {
        viewpager.adapter = skeletonAdapter
        skeletonTabLayout.run(onRunListener)
        onRunListener?.onRun()
    }

    override fun hide(onRunListener: OnSkeletonRunListener?) {
        skeletonTabLayout.hide()
        actualAdapter?.let {
            viewpager.adapter = it
        }
        onRunListener?.onRun()
    }
}