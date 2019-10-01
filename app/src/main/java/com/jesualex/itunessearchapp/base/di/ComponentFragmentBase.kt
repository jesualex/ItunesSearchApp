package com.jesualex.itunessearchapp.base.di

import androidx.fragment.app.Fragment

/**
 * Created by jesualex on 2019-07-30.
 */
interface ComponentFragmentBase <T: Fragment>{
    fun inject(target: T)
}