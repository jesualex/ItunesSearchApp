package com.jesualex.itunessearchapp.itunes.data.local.repo

import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.mapper.ItunesItemLocalMapper
import com.jesualex.itunessearchapp.itunes.data.local.entity.ItunesSearchLocal
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject

/**
 * Created by jesualex on 2019-10-02.
 */
class ItunesSearchRepo @Inject constructor(
    private val itunesItemLocalMapper: ItunesItemLocalMapper
) {
    private val realmClass get() = ItunesSearchLocal::class.java

    fun saveSearch(term: String, items: List<ItunesItem>){
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                val item = ItunesSearchLocal(
                    term,
                    RealmList()
                )

                item.items.addAll(itunesItemLocalMapper.reverseMap(items))

                it.copyToRealmOrUpdate(item)
            }
        }
    }
}