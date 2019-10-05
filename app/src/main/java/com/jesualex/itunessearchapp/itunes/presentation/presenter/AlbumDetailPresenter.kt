package com.jesualex.itunessearchapp.itunes.presentation.presenter

import com.jesualex.itunessearchapp.R
import com.jesualex.itunessearchapp.base.presentation.adapter.ItemAdapterListener
import com.jesualex.itunessearchapp.base.remote.UseCaseObserver
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.use_case.SearchByIdUseCase
import com.jesualex.itunessearchapp.itunes.data.local.repo.ItunesItemRepo
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackAdapter
import com.jesualex.itunessearchapp.itunes.presentation.contract.AlbumDetailContract
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-01.
 */
@Singleton class AlbumDetailPresenter @Inject constructor(
    private val trackAdapter: ItunesTrackAdapter,
    private val itunesPlayer: ItunesPlayer,
    private val searchByIdUseCase: SearchByIdUseCase,
    private val itunesItemRepo: ItunesItemRepo
): AlbumDetailContract.AlbumDetailPresenter {
    private var itemPlaying: Long? = null

    private lateinit var view: AlbumDetailContract.AlbumDetailView

    override fun setView(view: AlbumDetailContract.AlbumDetailView) {
        this.view = view
    }

    override fun initAdapterListener() {
        trackAdapter.playListener = ItemAdapterListener { item, pos ->
            if(itemPlaying == item.trackId){
                itunesPlayer.stopTrack()
                itemPlaying = null
            }else {
                itemPlaying = item.trackId
                itunesPlayer.playTrack(item)
            }
        }
    }

    override fun findAlbumTracks(id: Long) {
        view.showProgress(true)

        searchByIdUseCase.execute(
            id, object: UseCaseObserver<List<ItunesItem>>(){
                override fun onNext(value: List<ItunesItem>) {
                    super.onNext(value)

                    val mutableTracks = value.toMutableList()

                    for (item in value){
                        if(item.wrapperType == "collection"){
                            view.showAlbumInfo(item)
                            mutableTracks.remove(item)
                            break
                        }
                    }

                    itunesItemRepo.save(mutableTracks)
                    view.showProgress(false)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    view.showErrorMessage(R.string.ErrorLoadingTracks)
                    view.showProgress(false)
                }
            }
        )
    }

    override fun getAdapter(): ItunesTrackAdapter {
        return trackAdapter
    }

    override fun stopTrack() {
        itunesPlayer.stopTrack()
    }
}