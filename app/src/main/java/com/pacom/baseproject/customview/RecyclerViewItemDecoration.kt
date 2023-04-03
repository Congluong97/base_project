package com.pacom.baseproject.customview

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(
    context: Context,
    resId: Int
) : RecyclerView.ItemDecoration(){
    val mDivider = ContextCompat.getDrawable(context, resId)!!

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val dividerLeft = 32
        val dividerRight = parent.width - 32
        for (i in 0 until parent.childCount){
            if( i != parent.childCount - 1){
                val child = parent.getChildAt(i)
                val param = child.layoutParams as RecyclerView.LayoutParams
                val dividerTop = child.bottom + param.bottomMargin
                val dividerBottom = dividerTop + mDivider.intrinsicHeight

                mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                mDivider.draw(c)
            }
        }
    }
}