package com.jesualex.itunessearchapp.main.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.jesualex.itunessearchapp.R
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.presentation.contract.ItunesTrackView
import com.jesualex.itunessearchapp.itunes.presentation.view_model.ItunesTrackVM
import com.jesualex.itunessearchapp.main.presentation.contract.MainContract
import com.jesualex.itunessearchapp.main.presentation.presenter.MainPresenter
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Singleton

class MainActivity : AppCompatActivity(), MainContract.MainView, ItunesTrackView {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val comp: Comp = DaggerMainActivity_Comp.create()
        presenter = comp.getPresenter()

        presenter.setView(this)
        presenter.initAdapterListener()
        mainTracksRv.adapter = presenter.getAdapter()
        mainTracksRv.itemAnimator = null
        mainSearchView.setOnQueryTextListener(presenter.getSearchBarListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopTrack()
    }

    override fun getContext(): Context? {
        return this
    }

    override fun getVMAndObserve(
        term: String,
        observer: Observer<PagedList<ItunesItem>>
    ): LiveData<PagedList<ItunesItem>> {
        val trackLiveData = ViewModelProviders
            .of(this@MainActivity)
            .get(ItunesTrackVM::class.java)
            .setView(this)
            .setTerm(term)

        trackLiveData.observe(this@MainActivity, observer)

        return trackLiveData
    }

    override fun onSearch(notFound: Boolean) {
        mainNotFoundTV.visibility = if(notFound) View.VISIBLE else View.GONE
        mainTracksRv.visibility = if(!notFound) View.VISIBLE else View.GONE
        presenter.stopTrack()
    }

    @Singleton @Component interface Comp{
        fun getPresenter(): MainPresenter
    }
}
