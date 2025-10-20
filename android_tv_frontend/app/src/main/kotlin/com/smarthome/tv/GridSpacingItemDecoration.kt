package com.smarthome.tv

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// PUBLIC_INTERFACE
/**
 * ItemDecoration for controlling spacing between grid items with configurable gaps
 * @property spanCount Number of columns in the grid
 * @property spacing Spacing between items in pixels
 * @property includeEdge Whether to include spacing at the edges
 */
class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            // No edge gutters: distribute spacing only between items
            // Ensures inner gaps total to 'spacing' while edges remain 0
            outRect.left = (column * spacing) / spanCount
            outRect.right = spacing - ((column + 1) * spacing) / spanCount

            // Vertical: only apply top for rows after the first to avoid double gaps
            outRect.top = if (position >= spanCount) spacing else 0

            // Avoid adding extra bottom spacing which can create unintended gutters
            outRect.bottom = 0
        }
    }
}
