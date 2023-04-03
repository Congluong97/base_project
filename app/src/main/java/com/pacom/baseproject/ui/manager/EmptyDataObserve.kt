package com.pacom.baseproject.ui.manager

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserve(private var recyclerView: RecyclerView,private var view: View): RecyclerView.AdapterDataObserver() {

    init {
        checkEmpty()
    }

    private fun checkEmpty(){
        val viewEmptyVisible = recyclerView.adapter?.itemCount == 0
        if(viewEmptyVisible){
            view.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }else{
            view.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkEmpty()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
    }
}