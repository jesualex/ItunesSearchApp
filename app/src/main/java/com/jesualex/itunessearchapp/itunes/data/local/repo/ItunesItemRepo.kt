package com.jesualex.itunessearchapp.itunes.data.local.repo

import com.jesualex.itunessearchapp.base.presentation.LiveDataRepository
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.data.domain.mapper.ItunesItemLocalMapper
import com.jesualex.itunessearchapp.itunes.data.local.entity.ItunesItemLocal
import com.jesualex.itunessearchapp.itunes.data.local.entity.ItunesSearchLocal
import com.zhuinden.monarchy.Monarchy
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.Sort
import javax.inject.Inject

/**
 * Created by jesualex on 2019-10-02.
 */
class ItunesItemRepo @Inject constructor(
    private val itunesItemLocalMapper: ItunesItemLocalMapper
) {
    private val realmClass get() = ItunesItemLocal::class.java

    private fun getTermQuery(realm: Realm, term: String): RealmQuery<ItunesItemLocal> {
        return realm
            .where(realmClass)
            .equalTo(
                "${ItunesItemLocal.Keys.searches}.${ItunesSearchLocal.Keys.term}",
                term
            )
    }

    fun save(items: List<ItunesItem>){
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                it.copyToRealmOrUpdate(itunesItemLocalMapper.reverseMap(items))
            }
        }
    }

    fun save(item: ItunesItem){
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync {
                it.copyToRealmOrUpdate(itunesItemLocalMapper.reverseMap(item))
            }
        }
    }

    fun getMonarchySource(monarchy: Monarchy, term: String): Monarchy.RealmDataSourceFactory<ItunesItemLocal>{
        return monarchy.createDataSourceFactory {
            getTermQuery(it, term)
        }
    }

    fun countByTerm(term: String): Long{
        return  getTermQuery(Realm.getDefaultInstance(), term).count()
    }

    fun getAlbumLiveData(albumId: Long) = LiveDataRepository(
        Realm.getDefaultInstance()
                .where(realmClass)
                .equalTo(ItunesItemLocal.Keys.collectionId, albumId)
                .equalTo(ItunesItemLocal.Keys.wrapperType, "track")
                .findAllAsync()
    )
}