package com.xcaret.loyaltyv2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter (private val fragments: List<Fragment>, fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        val fragment = fragments[position]
        return fragment
    }
}