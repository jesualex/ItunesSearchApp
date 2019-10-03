package com.jesualex.itunessearchapp.itunes.data.local.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by jesualex on 2019-10-02.
 */

open class ItunesSearchLocal(
    @PrimaryKey var term: String = "",
    var items: RealmList<ItunesItemLocal> = RealmList()
): RealmObject(){
    fun cascadeDelete() {
        while(this.items.iterator().hasNext()){
            this.items.iterator().next().cascadeDelete()
        }

        deleteFromRealm()
    }

    object Keys{
        const val term = "term"
        const val items = "items"
    }
}