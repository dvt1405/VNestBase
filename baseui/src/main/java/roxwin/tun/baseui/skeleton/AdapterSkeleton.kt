package roxwin.tun.baseui.skeleton

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import roxwin.tun.baseui.utils.runChildAnimDrawable

class AdapterSkeleton(private val layoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemCount: Int? = null

    constructor(layoutRes: Int, itemCount: Int?) : this(layoutRes) {
        this.itemCount = itemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false), layoutRes)
    }


    override fun getItemCount(): Int = if (itemCount != null) itemCount!! else  10

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(position)
    }

    class ViewHolder(view: View, val layoutRes: Int) : RecyclerView.ViewHolder(view) {
        companion object {
            public const val DELAY_TIME = 100
        }

        private val topMargin by lazy {
            (itemView.resources.displayMetrics.scaledDensity * 20).toInt()
        }
        private val margin by lazy {
            (itemView.resources.displayMetrics.scaledDensity * 5).toInt()
        }

        init {
            (itemView as ViewGroup).runChildAnimDrawable()
        }

        fun onBind(position: Int) {
            val layoutParams = itemView.layoutParams as RecyclerView.LayoutParams
            val horizontal = (20 * itemView.context.resources.displayMetrics.scaledDensity).toInt()
            val top = (20 * itemView.context.resources.displayMetrics.scaledDensity).toInt()
            val bottom = (10 * itemView.context.resources.displayMetrics.scaledDensity).toInt()

//            when (layoutRes) {
//                else -> {
//                    Handler().postDelayed({
//                        (itemView as ViewGroup).forEach {
//                            it.runFadeInAnimation()
//                        }
//                    }, position * DELAY_TIME.toLong())
//                }
//            }
        }

    }
}