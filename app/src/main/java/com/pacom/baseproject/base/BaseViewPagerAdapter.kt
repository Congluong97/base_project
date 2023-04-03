package com.pacom.baseproject.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BaseViewPagerAdapter(fragmentActivity: FragmentActivity, var listFragment: List<Fragment>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}