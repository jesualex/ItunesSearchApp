package com.jesualex.itunessearchapp.main.presentation.presenter

import android.util.Log
import com.jesualex.itunessearchapp.base.remote.UseCaseObserver
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.entity.SearchByTermParams
import com.jesualex.itunessearchapp.itunes.data.domain.use_case.SearchByTermUseCase
import com.jesualex.itunessearchapp.main.presentation.contract.MainContract
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-01.
 */
@Singleton class MainPresenter @Inject constructor(
    private val searchByTermUseCase: SearchByTermUseCase
): MainContract.MainPresenter {
    private lateinit var view: MainContract.MainView

    override fun findAndSave(term: String) {
        searchByTermUseCase.execute(
            SearchByTermParams(term, 1),
            object: UseCaseObserver<List<ItunesItem>>(){
                override fun onNext(value: List<ItunesItem>) {
                    super.onNext(value)
                    Log.i("test", value.toString())
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    view.onSearchError()
                }
            }
        )
    }

    override fun setView(view: MainContract.MainView) {
        this.view = view
    }
}