package com.jesualex.itunessearchapp.itunes.data.domain.use_case

import com.jesualex.itunessearchapp.base.remote.UseCaseParam
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.entity.SearchByTermParams
import com.jesualex.itunessearchapp.itunes.data.domain.mapper.ItunesItemRemoteMapper
import com.jesualex.itunessearchapp.itunes.data.remote.api.ItunesSearchApiImpl
import com.jesualex.itunessearchapp.itunes.data.utils.ItunesConstants.SEARCH_DEF_LIMIT
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by jesualex on 2019-10-02.
 */

class SearchByTermUseCase @Inject constructor(
    private val itunesSearchApi: ItunesSearchApiImpl,
    private val itunesItemMapper: ItunesItemRemoteMapper
): UseCaseParam<List<ItunesItem>, SearchByTermParams>() {

    override fun createObservableUseCase(param: SearchByTermParams?): Observable<List<ItunesItem>>? {
        return param?.let { itunesSearchApi.search(it.term, it.limit, it.mediaType).map {
                resp -> itunesItemMapper.map(resp.results)
        }}
    }
}