package com.jesualex.itunessearchapp.base.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import java.util.ArrayList

/**
 * Created by jesualex on 12-12-18.
 */
open class FragmentAdapter @JvmOverloads constructor(
    fm: FragmentManager,
    fragments: List<Fragment> = ArrayList()
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var fragments: List<Fragment> = fragments
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
