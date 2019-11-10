package com.alanginger.moments.view

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TweetDivider(private val mDivider: Drawable) : RecyclerView.ItemDecoration() {
    private val mBounds = Rect()

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount) {
            val child = parent.getChildAt(i)
            child?.let {
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + Math.round(child.translationY)
                val top = bottom - mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(canvas)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        if (childAdapterPosition < 1) {
            outRect.set(0, 0, 0, 0)
        } else {
            outRect.set(0, 0, 0, mDivider.intrinsicHeight)
        }
    }
}
