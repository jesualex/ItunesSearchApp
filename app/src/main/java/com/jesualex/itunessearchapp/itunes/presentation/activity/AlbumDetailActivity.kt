package com.jesualex.itunessearchapp.itunes.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jesualex.itunessearchapp.R
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.presentation.contract.AlbumDetailContract
import com.jesualex.itunessearchapp.itunes.presentation.presenter.AlbumDetailPresenter
import com.jesualex.itunessearchapp.itunes.presentation.view_model.ItunesTrackVM
import com.squareup.picasso.Picasso
import dagger.Component
import kotlinx.android.synthetic.main.activity_album_detail.*
import javax.inject.Singleton

class AlbumDetailActivity : AppCompatActivity(), AlbumDetailContract.AlbumDetailView {
    private lateinit var presenter: AlbumDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        if(!intent.hasExtra(ID_KEY)){
            finish()
        }

        val comp: Comp = DaggerAlbumDetailActivity_Comp.create()
        presenter = comp.getPresenter()

        presenter.setView(this)
        presenter.initAdapterListener()
        val adapter = presenter.getAdapter()
        albumDetTracksRv.adapter = adapter
        albumDetTracksRv.itemAnimator = null
        val id = intent.getLongExtra(ID_KEY, -1)
        presenter.findAlbumTracks(id)

        val trackLiveData = ViewModelProviders
            .of(this@AlbumDetailActivity)
            .get(ItunesTrackVM::class.java)
            .getAlbumTracksLiveData(id)

        trackLiveData.observe(this@AlbumDetailActivity, Observer{ adapter.items = it })
    }

    override fun getContext(): Context? {
        return this
    }

    override fun showProgress(show: Boolean) {
        super.showProgress(show)
        albumDetProgress.visibility = if(show) View.VISIBLE else View.GONE
        albumDetTracksRv.visibility = if(!show) View.VISIBLE else View.GONE
    }

    override fun showAlbumInfo(album: ItunesItem) {
        album.artworkUrl100?.let {
            Picasso.get().load(it).into(albumDetThumb)
        }

        albumDetTitle.text = album.collectionName
        albumDetArtist.text = album.artistName
    }

    companion object{
        private val ID_KEY = "idkey"

        fun newInstance(context: Context, id: Long): Intent{
            val intent = Intent(context, AlbumDetailActivity::class.java)
            intent.putExtra(ID_KEY, id)

            return intent
        }
    }

    @Singleton @Component interface Comp{
        fun getPresenter(): AlbumDetailPresenter
    }
}
