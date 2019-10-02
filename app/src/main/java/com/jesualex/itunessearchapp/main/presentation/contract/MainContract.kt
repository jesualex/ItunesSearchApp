package com.jesualex.itunessearchapp.main.presentation.contract

import com.jesualex.itunessearchapp.base.presentation.PresenterBase
import com.jesualex.itunessearchapp.base.presentation.ViewBase

/**
 * Created by jesualex on 2019-10-02.
 */
interface MainContract {
    interface MainView: ViewBase{
        fun onSearchError()
    }

    interface MainPresenter: PresenterBase<MainView>{
        fun findAndSave(term: String)
    }
}