package roxwin.tun.baseui.skeleton

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import roxwin.tun.baseui.R
import roxwin.tun.baseui.utils.bindAnimationDrawable
import roxwin.tun.baseui.utils.replaceView
import roxwin.tun.baseui.utils.runAnimationChangeBackground


@SuppressLint("LogNotTimber")
class ViewSkeletonScreen(private val actualView: View,
                         private val layoutRes: Int,
                         private var shouldPlayAnim: Boolean,
                         private var isAnimChangeBackgroundDrawable: Boolean,
                         private var anim: Animation?) : AltitudeSkeleton.SkeletonScreen {
    private var currentView: View? = null

    companion object{
        const val TAG = "ViewSkeletonScreen"
        const val NO_VIEW_GROUP_FOUND_MESSAGE = "No view group found!"
    }
    class Builder(private val actualView: View) {
        private var layoutRes: Int? = null
        private var duration: Long? = null
        private var animation: Animation? = null
        private var shouldPlayAnim: Boolean = true
        private var isAnimChangeBackgroundDrawable: Boolean = true
        fun layout(@LayoutRes layoutRes: Int?): Builder {
            this.layoutRes = layoutRes
            return this
        }

        fun duration(duration: Long?): Builder {
            this.duration = duration
            return this
        }

        fun anim(anim: Animation?): Builder {
            this.animation = anim
            return this
        }

        fun playAnim(shouldPlayAnim: Boolean): Builder {
            this.shouldPlayAnim = shouldPlayAnim
            return this
        }

        fun isAnimChangeBackgroundDrawable(isAnimChangeBackgroundDrawable: Boolean): Builder {
            this.isAnimChangeBackgroundDrawable = isAnimChangeBackgroundDrawable
            return this
        }

        fun build(): ViewSkeletonScreen = ViewSkeletonScreen(actualView,
                layoutRes ?: R.layout.skeleton_image,
                shouldPlayAnim,
                isAnimChangeBackgroundDrawable,
                animation)

        fun run(): ViewSkeletonScreen = build().also {
            it.run()
        }
    }

    private lateinit var skeletonView: View
    private lateinit var parent: ViewParent
    private var actualViewLayoutParams: ViewGroup.LayoutParams = actualView.layoutParams
    private val actualViewIndex: Int by lazy { (parent as ViewGroup).indexOfChild(actualView) }

    init {
        generateSkeletonLoadingView()
    }

    @SuppressLint("LogNotTimber")
    private fun generateSkeletonLoadingView(): View? {
        val viewParent: ViewParent? = actualView.parent
        if (viewParent == null) {
            Log.e(AltitudeSkeleton.TAG, "the source view have not attach to any view")
            return null
        }
        parent = viewParent
        skeletonView = LayoutInflater.from(actualView.context).inflate(layoutRes, parent as ViewGroup, false)

        if (shouldPlayAnim) {
            initAnim()
        }
        return skeletonView
    }

    private fun initAnim() {
        if (skeletonView is ViewGroup) {
            val childCount = (skeletonView as ViewGroup).childCount
            (skeletonView as ViewGroup).forEach {
                try {
                    it.bindAnimationDrawable()
                } catch (e: Exception) {

                }
            }
        }
    }

    private fun runAnim() {
        if (shouldPlayAnim) {
            when {
                isAnimChangeBackgroundDrawable -> {
                    handleChildSkeletonView { childView ->
                        val view = childView
                        if (view is ViewGroup && view.childCount > 1) {
                            when(view) {
                                is LinearLayout -> {
                                    view.forEach {
                                        it.bindAnimationDrawable()
                                        it.runAnimationChangeBackground()
                                    }
                                }
                                is ConstraintLayout -> {
                                    view.forEach {
                                        it.bindAnimationDrawable()
                                        it.runAnimationChangeBackground()
                                    }
                                }
                                is CoordinatorLayout -> {
                                    view.forEach {
                                        it.bindAnimationDrawable()
                                        it.runAnimationChangeBackground()
                                    }
                                }
                                else -> {
                                    Log.e("View", view.javaClass.name)
                                    Log.e(TAG, NO_VIEW_GROUP_FOUND_MESSAGE)
                                }
                            }
//                            view.forEach {
//                                it.bindAnimationDrawable()
//                                it.runAnimationChangeBackground()
//                            }
                        } else {
                            try {
                                childView.bindAnimationDrawable()
                                childView.runAnimationChangeBackground()
                            } catch (e: Exception) {
                                (childView as ConstraintLayout).forEach {
                                    it.runAnimationChangeBackground()
                                }

                            }
                        }

                    }
                }
                anim != null -> {
                    handleChildSkeletonView {
                        it.startAnimation(anim!!)
                    }
                }
                else -> {
                    handleChildSkeletonView { it.runAnimationChangeBackground(actualView.resources.getColor(R.color.skeleton_elements_light_50), actualView.resources.getColor(R.color.skeleton_elements)) }
                }
            }
        }
    }

    private fun handleChildSkeletonView(onChildHandle: (it: View) -> Unit) {
        if (skeletonView is ViewGroup) {
            (skeletonView as ViewGroup).forEach {
                onChildHandle(it)
            }
        }
    }

    private fun restoreActualView() {
        handleChildSkeletonView { it.clearAnimation() }
        (skeletonView as ViewGroup).removeAllViews()
        (parent as ViewGroup).replaceView(skeletonView, actualView)
        currentView = actualView
    }

    override fun run(onRunListener: OnSkeletonRunListener?) {
        (parent as ViewGroup).replaceView(actualView, skeletonView)
        onRunListener?.onRun()
        runAnim()
    }

    override fun hide(onRunListener: OnSkeletonRunListener?) {
        if (currentView != actualView) {
            restoreActualView()
        }
        onRunListener?.onRun()
    }
}