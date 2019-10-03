package com.jesualex.itunessearchapp.base.presentation

import android.content.Context
import androidx.annotation.StringRes
import com.jesualex.itunessearchapp.App

/**
 * Created by jesualex on 18-02-19.
 */
interface ViewBase {
    fun getContext(): Context?

    fun showProgress(show: Boolean){}

    fun showMessage(message: String?) {
        message?.let { App.showMessage(it, getContext()) }
    }

    fun showMessage(@StringRes message: Int) {
        App.showMessage(message, getContext())
    }

    fun showErrorMessage(message: String?) {
        showMessage(message)
    }

    fun showErrorMessage(@StringRes message: Int) {
        showMessage(message)
    }
}
