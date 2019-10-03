package com.jesualex.itunessearchapp.main.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.jesualex.itunessearchapp.R
import com.jesualex.itunessearchapp.itunes.data.domain.entity.ItunesItem
import com.jesualex.itunessearchapp.itunes.presentation.adapter.ItunesTrackAdapter
import com.jesualex.itunessearchapp.itunes.presentation.contract.ItunesTrackView
import com.jesualex.itunessearchapp.itunes.presentation.view_model.ItunesTrackVM
import com.jesualex.itunessearchapp.main.presentation.contract.MainContract
import com.jesualex.itunessearchapp.main.presentation.presenter.MainPresenter
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Singleton

class MainActivity : AppCompatActivity(), MainContract.MainView, ItunesTrackView {
    private val trackObserver = Observer<PagedList<ItunesItem>> { trackAdapter.submitList(it) }

    private lateinit var presenter: MainPresenter
    private lateinit var trackAdapter: ItunesTrackAdapter
    private lateinit var vm: ItunesTrackVM
    private var trackLiveData: LiveData<PagedList<ItunesItem>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val comp: Comp = DaggerMainActivity_Comp.create()
        presenter = comp.getPresenter()
        trackAdapter = comp.getTrackAdapter()

        mainTracksRv.adapter = trackAdapter

        vm = ViewModelProviders
            .of(this)
            .get(ItunesTrackVM::class.java)

        mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    if(it.isEmpty()){
                        trackAdapter.submitList(null)
                        return true
                    }

                    trackLiveData?.removeObserver(trackObserver)

                    trackLiveData = ViewModelProviders
                        .of(this@MainActivity)
                        .get(ItunesTrackVM::class.java)
                        .setTerm(it)

                    trackLiveData?.observe(this@MainActivity, trackObserver)
                }?:run{
                    trackAdapter.submitList(null)
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean { return false }
        })
    }

    override fun getContext(): Context? {
        return this
    }

    override fun onNotFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Singleton @Component interface Comp{
        fun getPresenter(): MainPresenter
        fun getTrackAdapter(): ItunesTrackAdapter
    }
}
