package com.xcaret.loyaltyreps.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TrainingDetailInfoAdapter (private val fragments: List<Fragment>, fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = fragments.count()

    override fun createFragment(position: Int): Fragment {
        val fragment = fragments[position]
        return fragment
    }
}