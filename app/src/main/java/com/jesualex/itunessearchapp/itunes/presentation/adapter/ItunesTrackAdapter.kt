package com.jesualex.itunessearchapp.itunes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jesualex.itunessearchapp.R
import com.jesualex.itunessearchapp.base.presentation.adapter.ItemAdapterListener
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import kotlinx.android.synthetic.main.item_track.view.*
import javax.inject.Inject

/**
 * Created by jesualex on 2019-10-01.
 */
class ItunesTrackAdapter @Inject constructor(

) : PagedListAdapter<ItunesItem, ItunesTrackAdapter.ViewHolder>(DIFF_CALLBACK) {
    var clickListener: ItemAdapterListener<ItunesItem>? = null

    override fun getItemId(position: Int): Long {
        return getItem(position)!!.trackId.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initView(getItem(position), position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun initView(item: ItunesItem?, pos: Int){
            if(item == null){
                itemView.visibility = View.GONE
                return
            }

            itemView.itunesItemName.text = item.trackName
            itemView.itunesItemArtist.text = item.artistName

            itemView.setOnClickListener {
                clickListener?.onItem(item, pos)
            }

            itemView.visibility = View.VISIBLE
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<ItunesItem>() {
            override fun areItemsTheSame(oldItem: ItunesItem, newItem: ItunesItem) = oldItem.trackId == newItem.trackId

            override fun areContentsTheSame(oldItem: ItunesItem, newItem: ItunesItem) = oldItem == newItem
        }
    }
}