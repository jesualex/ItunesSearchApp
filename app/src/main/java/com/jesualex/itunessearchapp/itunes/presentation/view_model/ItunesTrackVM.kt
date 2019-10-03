package com.jesualex.itunessearchapp.itunes.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jesualex.itunessearchapp.base.remote.UseCaseObserver
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.entity.SearchByTermParams
import com.jesualex.itunessearchapp.itunes.data.domain.mapper.ItunesItemLocalMapper
import com.jesualex.itunessearchapp.itunes.data.domain.use_case.SearchByTermUseCase
import com.jesualex.itunessearchapp.itunes.data.local.repo.ItunesItemRepo
import com.jesualex.itunessearchapp.itunes.data.local.repo.ItunesSearchRepo
import com.jesualex.itunessearchapp.itunes.data.utils.ItunesConstants.SEARCH_DEF_LIMIT
import com.jesualex.itunessearchapp.itunes.presentation.contract.ItunesTrackView
import com.zhuinden.monarchy.Monarchy
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-02.
 */
class ItunesTrackVM: ViewModel() {
    private val pageSize = SEARCH_DEF_LIMIT
    private val monarchy = Monarchy.Builder().build()
    private val comp: Comp = DaggerItunesTrackVM_Comp.create()
    private val itunesItemLocalMapper = comp.getItunesItemLocalMapper()

    private lateinit var livePagedListBuilder: LivePagedListBuilder<Int, ItunesItem>

    var view: ItunesTrackView? = null

    fun setTerm(term: String): LiveData<PagedList<ItunesItem>>{
        val realmDataSourceFactory = comp.getItunesItemRepo().getMonarchySource(monarchy, term)
        val dataSourceFactory = realmDataSourceFactory.map {
            itunesItemLocalMapper.map(it)
        }

        livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, pageSize)
        livePagedListBuilder.setBoundaryCallback(object: PagedList.BoundaryCallback<ItunesItem>(){
            override fun onItemAtEndLoaded(itemAtEnd: ItunesItem) {
                super.onItemAtEndLoaded(itemAtEnd)
                comp.getItunesSearchUseCase().execute(
                    SearchByTermParams(
                        term,
                        (comp.getItunesItemRepo().countByTerm(term) + pageSize).toInt()
                    ), object: UseCaseObserver<List<ItunesItem>>(){
                        override fun onNext(value: List<ItunesItem>) {
                            super.onNext(value)
                            comp.getItunesSearchRepo().saveSearch(term, value)
                        }
                    })
            }

            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()

                comp.getItunesSearchUseCase().execute(
                    SearchByTermParams(
                        term,
                        pageSize
                    ), object: UseCaseObserver<List<ItunesItem>>(){
                        override fun onNext(value: List<ItunesItem>) {
                            super.onNext(value)
                            comp.getItunesSearchRepo().saveSearch(term, value)
                        }

                        override fun onError(e: Throwable) {
                            super.onError(e)
                            view?.onNotFound()
                        }
                    }
                )
            }
        })

        return monarchy.findAllPagedWithChanges(realmDataSourceFactory, livePagedListBuilder)
    }

    @Singleton @Component interface Comp{
        fun getItunesSearchRepo(): ItunesSearchRepo
        fun getItunesItemRepo(): ItunesItemRepo
        fun getItunesSearchUseCase(): SearchByTermUseCase
        fun getItunesItemLocalMapper(): ItunesItemLocalMapper
    }
}