package com.jesualex.itunessearchapp.itunes.data.remote.api

import com.jesualex.itunessearchapp.BuildConfig
import com.jesualex.itunessearchapp.base.remote.ApiServiceFactory
import com.jesualex.itunessearchapp.itunes.data.remote.entity.ItunesBaseRespRemote
import com.jesualex.itunessearchapp.itunes.data.remote.entity.ItunesItemRemote
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-01.
 */

@Singleton class ItunesSearchApiImpl @Inject constructor(): ItunesSearchApi{
    private val api = ApiServiceFactory.build(
        ItunesSearchApi::class.java,
        BuildConfig.BASE_URL
    )

    override fun search(
        term: String,
        limit: Int,
        mediaType: String?
    ): Observable<ItunesBaseRespRemote> {
        return api.search(term, limit, mediaType)
    }

    override fun lookup(id: Long, entity: String?): Observable<ItunesBaseRespRemote> {
        return api.lookup(id, entity)
    }
}
 