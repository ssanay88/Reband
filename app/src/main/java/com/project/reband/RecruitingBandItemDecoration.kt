package com.project.reband

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecruitingBandItemDecoration : RecyclerView.ItemDecoration() {

    private val verticalMargin = 10
    private val horizontalMargin = 10

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = verticalMargin
        outRect.bottom = verticalMargin
        outRect.left = horizontalMargin
        outRect.right = horizontalMargin
    }

}