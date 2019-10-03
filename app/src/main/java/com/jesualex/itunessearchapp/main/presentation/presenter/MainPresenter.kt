package com.jesualex.itunessearchapp.main.presentation.presenter

import com.jesualex.itunessearchapp.base.remote.UseCaseObserver
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.entity.SearchByTermParams
import com.jesualex.itunessearchapp.itunes.data.domain.use_case.SearchByTermUseCase
import com.jesualex.itunessearchapp.itunes.data.local.repo.ItunesSearchRepo
import com.jesualex.itunessearchapp.itunes.data.utils.ItunesConstants
import com.jesualex.itunessearchapp.main.presentation.contract.MainContract
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-01.
 */
@Singleton class MainPresenter @Inject constructor(

): MainContract.MainPresenter {
    private lateinit var view: MainContract.MainView

    override fun setView(view: MainContract.MainView) {
        this.view = view
    }
}