package com.jesualex.itunessearchapp.itunes.data.domain.mapper

import com.jesualex.itunessearchapp.base.data.Mapper
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.local.entity.ItunesItemLocal
import com.jesualex.itunessearchapp.itunes.data.remote.entity.ItunesItemRemote
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jesualex on 2019-10-01.
 */

@Singleton class ItunesItemLocalMapper @Inject constructor(): Mapper<ItunesItemLocal, ItunesItem>() {
    override fun map(value: ItunesItemLocal): ItunesItem {
        return ItunesItem(
            wrapperType = value.wrapperType,
            kind = value.kind,
            artistId = value.artistId,
            collectionId = value.collectionId,
            trackId = value.trackId,
            artistName = value.artistName,
            collectionName = value.collectionName,
            trackName = value.trackName,
            collectionCensoredName = value.collectionCensoredName,
            trackCensoredName = value.trackCensoredName,
            artistViewUrl = value.artistViewUrl,
            collectionViewUrl = value.collectionViewUrl,
            trackViewUrl = value.trackViewUrl,
            previewUrl = value.previewUrl,
            artworkUrl30 = value.artworkUrl30,
            artworkUrl60 = value.artworkUrl60,
            artworkUrl100 = value.artworkUrl100,
            collectionPrice = value.collectionPrice,
            trackPrice = value.trackPrice,
            releaseDate = value.releaseDate,
            collectionExplicitness = value.collectionExplicitness,
            trackExplicitness = value.trackExplicitness,
            discCount = value.discCount,
            discNumber = value.discNumber,
            trackCount = value.trackCount,
            trackNumber = value.trackNumber,
            trackTimeMillis = value.trackTimeMillis,
            country = value.country,
            currency = value.currency,
            primaryGenreName = value.primaryGenreName,
            isStreamable = value.isStreamable,
            isPlaying = value.isPlaying,
            playedPercent = value.playedPercent,
            playDuration = value.playDuration
        )
    }

    override fun reverseMap(value: ItunesItem): ItunesItemLocal {
        return ItunesItemLocal(
            wrapperType = value.wrapperType,
            kind = value.kind,
            artistId = value.artistId,
            collectionId = value.collectionId,
            trackId = value.trackId,
            artistName = value.artistName,
            collectionName = value.collectionName,
            trackName = value.trackName,
            collectionCensoredName = value.collectionCensoredName,
            trackCensoredName = value.trackCensoredName,
            artistViewUrl = value.artistViewUrl,
            collectionViewUrl = value.collectionViewUrl,
            trackViewUrl = value.trackViewUrl,
            previewUrl = value.previewUrl,
            artworkUrl30 = value.artworkUrl30,
            artworkUrl60 = value.artworkUrl60,
            artworkUrl100 = value.artworkUrl100,
            collectionPrice = value.collectionPrice,
            trackPrice = value.trackPrice,
            releaseDate = value.releaseDate,
            collectionExplicitness = value.collectionExplicitness,
            trackExplicitness = value.trackExplicitness,
            discCount = value.discCount,
            discNumber = value.discNumber,
            trackCount = value.trackCount,
            trackNumber = value.trackNumber,
            trackTimeMillis = value.trackTimeMillis,
            country = value.country,
            currency = value.currency,
            primaryGenreName = value.primaryGenreName,
            isStreamable = value.isStreamable,
            isPlaying = value.isPlaying,
            playedPercent = value.playedPercent,
            playDuration = value.playDuration
        )
    }
}