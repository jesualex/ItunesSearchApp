package com.jesualex.itunessearchapp.itunes.presentation.contract

import com.jesualex.itunessearchapp.base.presentation.PresenterBase
import com.jesualex.itunessearchapp.base.presentation.ViewBase
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackAdapter
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackPagedAdapter

/**
 * Created by jesualex on 2019-10-02.
 */
interface AlbumDetailContract {
    interface AlbumDetailView: ViewBase{
        fun showAlbumInfo(album: ItunesItem)
    }

    interface AlbumDetailPresenter: PresenterBase<AlbumDetailView>{
        fun initAdapterListener()
        fun getAdapter(): ItunesTrackAdapter
        fun findAlbumTracks(id: Long)
        fun stopTrack()
    }
}