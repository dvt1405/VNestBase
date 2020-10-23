package roxwin.tun.baseui.recyclerview

import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewEdgeEffectFactory(val vertical: Boolean = true) : RecyclerView.EdgeEffectFactory() {
    override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
        return object : EdgeEffect(view.context) {
            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)
                view.forEachVisibleHolder { holder: BaseViewHolder<Any> ->
                    val sign = if (vertical) {
                        if (direction == DIRECTION_BOTTOM) -1 else 1
                    } else {
                        if (direction == DIRECTION_RIGHT) -1 else 1
                    }

                    val translationVelocity =
                        sign * velocity * 2 * RecyclerViewConstant.FLING_TRANSLATION_MAGNITUDE
                    if (vertical) {
                        holder.translationY
                            .setStartVelocity(translationVelocity)
                            .start()
                    } else {
                        holder.translationX
                            .setStartVelocity(translationVelocity)
                            .start()
                    }

                }
            }

            override fun onRelease() {
                super.onRelease()
                view.forEachVisibleHolder { holder: BaseViewHolder<Any> ->
                    holder.rotation.start()
                    if (vertical) {
                        holder.translationY.start()
                    } else {
                        holder.translationX.start()
                    }
                }
            }

            private fun handlePull(deltaDistance: Float) {
                val sign = if (vertical) {
                    if (direction == DIRECTION_BOTTOM) -1 else 1
                } else {
                    if (direction == DIRECTION_RIGHT) -1 else 1
                }
                val rotationDelta = sign * deltaDistance * RecyclerViewConstant.OVER_SCROLL_ROTATION_MAGNITUDE
                val translationYDelta =
                    if (vertical) sign * view.height * deltaDistance * RecyclerViewConstant.OVER_SCROLL_TRANSLATION_MAGNITUDE else sign * view.width * deltaDistance * RecyclerViewConstant.OVER_SCROLL_TRANSLATION_MAGNITUDE
                view.forEachVisibleHolder { holder: BaseViewHolder<Any> ->
                    holder.rotation.cancel()
                    if (vertical) {
                        holder.translationY.cancel()
//                            it.itemView.rotation += rotationDelta
                        holder.itemView.translationY += translationYDelta
                    } else {
                        holder.translationX.cancel()
//                            it.itemView.rotation += rotationDelta
                        holder.itemView.translationX += translationYDelta
                    }
                }
            }
        }
    }
}

