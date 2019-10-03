package com.jesualex.itunessearchapp.itunes.data.domain.entity

/**
 * Created by jesualex on 2019-10-02.
 */
class SearchByTermParams(
    val term: String,
    val limit: Int,
    val mediaType: String = "music"
)