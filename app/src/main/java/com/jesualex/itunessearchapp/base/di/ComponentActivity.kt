package com.jesualex.itunessearchapp.base.di

import android.app.Activity

/**
 * Created by jesualex on 2019-04-26.
 */

interface ComponentActivity<T: Activity>{
    fun inject(target: T)
}