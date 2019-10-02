package com.jesualex.itunessearchapp.itunes.data.remote.api

import com.jesualex.itunessearchapp.itunes.data.remote.entity.ItunesBaseRespRemote
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jesualex on 2019-10-01.
 */

interface ItunesSearchApi {
    @GET("search") fun search(
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("mediaType") mediaType: String?
    ): Observable<ItunesBaseRespRemote>
}