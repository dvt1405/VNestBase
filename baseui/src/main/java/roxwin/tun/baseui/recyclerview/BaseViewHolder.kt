package roxwin.tun.baseui.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import roxwin.tun.baseui.dialog.setOnSingleClickListener

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    var currentVelocity = 0f

    val rotation = SpringAnimation(itemView, SpringAnimation.ROTATION)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )
        .addUpdateListener { animation, value, velocity ->
            currentVelocity = velocity
        }
    val translationY: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )

    val translationX: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_X)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )

    fun onBind(item: T, onItemClick: ((item: T, position: Int) -> Unit)? = null) {
        onBindItem(item)
        itemView.setOnClickListener {
            onItemClick?.let {
                it(item, adapterPosition)
            }
        }
    }

    abstract fun onBindItem(item: T)

}