package com.sixt.pdfviewer

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class DividerItemDecoration(private val gridSpacingPx: Int, private val columns: Int) : RecyclerView.ItemDecoration() {

    private var needLeftSpacing = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val frameWidth = ((parent.width - gridSpacingPx.toFloat() * (columns - 1)) / columns).toInt()
        val padding = parent.width / columns - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < columns) {
            outRect.top = 0
        } else {
            outRect.top = gridSpacingPx
        }
        if (itemPosition % columns == 0) {
            outRect.left = 0
            outRect.right = padding
            needLeftSpacing = true
        } else if ((itemPosition + 1) % columns == 0) {
            needLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (needLeftSpacing) {
            needLeftSpacing = false
            outRect.left = gridSpacingPx - padding
            if ((itemPosition + 2) % columns == 0) {
                outRect.right = gridSpacingPx - padding
            } else {
                outRect.right = gridSpacingPx / 2
            }
        } else if ((itemPosition + 2) % columns == 0) {
            needLeftSpacing = false
            outRect.left = gridSpacingPx / 2
            outRect.right = gridSpacingPx - padding
        } else {
            needLeftSpacing = false
            outRect.left = gridSpacingPx / 2
            outRect.right = gridSpacingPx / 2
        }
        outRect.bottom = 0
    }
}