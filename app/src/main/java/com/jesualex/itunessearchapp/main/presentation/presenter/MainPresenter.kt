package com.jesualex.itunessearchapp.main.presentation.presenter

import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.jesualex.itunessearchapp.base.presentation.adapter.ItemAdapterListener
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackPagedAdapter
import com.jesualex.itunessearchapp.itunes.presentation.presenter.ItunesPlayer
import com.jesualex.itunessearchapp.main.presentation.contract.MainContract
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-01.
 */
@Singleton class MainPresenter @Inject constructor(
    private val trackPagedAdapter: ItunesTrackPagedAdapter,
    private val itunesPlayer: ItunesPlayer
): MainContract.MainPresenter {
    private val trackObserver = Observer<PagedList<ItunesItem>> { trackPagedAdapter.submitList(it) }
    private var itemPlaying: Long? = null

    private lateinit var view: MainContract.MainView

    private var trackLiveData: LiveData<PagedList<ItunesItem>>? = null

    override fun setView(view: MainContract.MainView) {
        this.view = view
    }

    override fun initAdapterListener() {
        trackPagedAdapter.playListener = ItemAdapterListener { item, pos ->
            if(itemPlaying == item.trackId){
                itunesPlayer.stopTrack()
                itemPlaying = null
            }else {
                itemPlaying = item.trackId
                itunesPlayer.playTrack(item)
            }
        }

        trackPagedAdapter.clickListener = ItemAdapterListener { item, pos ->
            itunesPlayer.stopTrack()
            view.goToAlbumDetail(item.collectionId)
        }
    }

    override fun getAdapter(): ItunesTrackPagedAdapter {
        return trackPagedAdapter
    }

    override fun getSearchBarListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    if(it.isEmpty()){
                        trackPagedAdapter.submitList(null)
                        return true
                    }

                    trackLiveData?.removeObserver(trackObserver)

                    trackLiveData = view.getVMAndObserve(it, trackObserver)
                }?:run{
                    trackPagedAdapter.submitList(null)
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean { return false }
        }
    }

    override fun stopTrack() {
        itunesPlayer.stopTrack()
    }
}