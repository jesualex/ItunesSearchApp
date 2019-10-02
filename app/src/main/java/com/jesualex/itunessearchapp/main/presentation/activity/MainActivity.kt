package com.jesualex.itunessearchapp.main.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.jesualex.itunessearchapp.R
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackAdapter
import com.jesualex.itunessearchapp.main.presentation.contract.MainContract
import com.jesualex.itunessearchapp.main.presentation.presenter.MainPresenter
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Singleton

class MainActivity : AppCompatActivity(), MainContract.MainView {
    private lateinit var presenter: MainPresenter
    private lateinit var trackAdapter: ItunesTrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val comp: Comp = DaggerMainActivity_Comp.create()
        presenter = comp.getPresenter()
        trackAdapter = comp.getTrackAdapter()

        mainTracksRv.adapter = trackAdapter

        mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { presenter.findAndSave(it) }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean { return false }
        })
    }

    override fun getContext(): Context? {
        return this
    }

    override fun onSearchError() {
        TODO("not implemented") //set no result message.
    }

    @Singleton @Component interface Comp{
        fun getPresenter(): MainPresenter
        fun getTrackAdapter(): ItunesTrackAdapter
    }
}
