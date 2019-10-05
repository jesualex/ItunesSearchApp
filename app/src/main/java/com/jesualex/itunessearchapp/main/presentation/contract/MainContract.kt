package com.jesualex.itunessearchapp.main.presentation.contract

import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.jesualex.itunessearchapp.base.presentation.PresenterBase
import com.jesualex.itunessearchapp.base.presentation.ViewBase
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackPagedAdapter

/**
 * Created by jesualex on 2019-10-02.
 */
interface MainContract {
    interface MainView: ViewBase{
        fun getVMAndObserve(
            term: String,
            observer: Observer<PagedList<ItunesItem>>
        ): LiveData<PagedList<ItunesItem>>

        fun goToAlbumDetail(id:Long)
    }

    interface MainPresenter: PresenterBase<MainView>{
        fun initAdapterListener()
        fun getSearchBarListener(): SearchView.OnQueryTextListener
        fun getAdapter(): ItunesTrackPagedAdapter
        fun stopTrack()
    }
}