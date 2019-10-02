package com.jesualex.itunessearchapp.itunes.data.domain.entity

/**
 * Created by jesualex on 2019-10-01.
 */
data class ItunesItem (
    val wrapperType : String = "",
    val kind : String = "",
    val artistId : Int = -1,
    val collectionId : Int = -1,
    val trackId : Int = -1,
    val artistName : String = "",
    val collectionName : String = "",
    val trackName : String = "",
    val collectionCensoredName : String = "",
    val trackCensoredName : String = "",
    val artistViewUrl : String = "",
    val collectionViewUrl : String = "",
    val trackViewUrl : String = "",
    val previewUrl : String? = null,
    val artworkUrl30 : String = "",
    val artworkUrl60 : String? = null,
    val artworkUrl100 : String? = null,
    val collectionPrice : Double = 0.0,
    val trackPrice : Double = 0.0,
    val releaseDate : String = "",
    val collectionExplicitness : String = "",
    val trackExplicitness : String = "",
    val discCount : Int = -1,
    val discNumber : Int = -1,
    val trackCount : Int = -1,
    val trackNumber : Int = -1,
    val trackTimeMillis : Int? = null,
    val country : String = "",
    val currency : String = "",
    val primaryGenreName : String = "",
    val isStreamable : Boolean?  = null
)