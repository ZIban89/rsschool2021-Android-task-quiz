package com.rsschool.quiz


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        if(position > 4) return ResultFragment.getInstance(5)
        return QuestionFragment.getInstance(position)
    }

}