package roxwin.tun.baseui.skeleton

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

typealias OnRunListener = () -> Unit

interface OnSkeletonRunListener {
    fun onRun()
}

object AltitudeSkeleton {
    public const val TAG = "Skeleton screen"

    @JvmStatic
    fun bind(recyclerView: RecyclerView): RecyclerViewSkeletonScreen.Builder {
        return RecyclerViewSkeletonScreen.Builder(recyclerView)
    }

    @JvmStatic
    fun bind(view: View): ViewSkeletonScreen.Builder {
        return ViewSkeletonScreen.Builder(view)
    }

    @JvmStatic
    fun bind(view: ViewPager, tab: TabLayout, fragmentManager: FragmentManager): ViewPagerSkeleton.Builder {
        return ViewPagerSkeleton.Builder(view, tab, fragmentManager)
    }

    @JvmStatic
    fun bind(view: ViewPager2, tab: TabLayout, fragment: Fragment): ViewPager2Skeleton.Builder {
        return ViewPager2Skeleton.Builder(view, tab, fragment)
    }


    interface SkeletonScreen {
        fun run(onRunListener: OnRunListener?) {
            run(object : OnSkeletonRunListener {
                override fun onRun() {
                    onRunListener?.let { it() }
                }
            })
        }

        fun run(onRunListener: OnSkeletonRunListener?)

        fun run() {
            run(null as OnSkeletonRunListener?)
        }

        fun hide() {
            hide(null as OnSkeletonRunListener?)
        }

        fun hide(onRunListener: OnRunListener?) {
            hide(object : OnSkeletonRunListener {
                override fun onRun() {
                    onRunListener?.let { it() }
                }
            })
        }

        fun hide(onRunListener: OnSkeletonRunListener?)

    }

}