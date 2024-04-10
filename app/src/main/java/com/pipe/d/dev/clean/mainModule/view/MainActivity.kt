package com.pipe.d.dev.clean.mainModule.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pipe.d.dev.clean.databinding.ActivityMainBinding
import com.pipe.d.dev.clean.common.SportEvent
import com.pipe.d.dev.clean.mainModule.model.DataSourceImpl
import com.pipe.d.dev.clean.mainModule.model.MainRepositoryImpl
import com.pipe.d.dev.clean.mainModule.presenter.MainPresenter
import com.pipe.d.dev.clean.mainModule.presenter.MainPresenterImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainView, OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val adapter: ResultAdapter by inject { parametersOf(this) }
    private val presenter: MainPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onCreate()

        setupRecyclerView()
        setupSwipeRefresh()
        setupClicks()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.srlResults.setOnRefreshListener {
            //adapter.clear()
            //getEvents()
            //binding.btnAd.visibility = View.VISIBLE
            lifecycleScope.launch { presenter.refresh() }
        }
    }

    private fun setupClicks() {
        binding.btnAd.run {
            setOnClickListener {
                lifecycleScope.launch {
                    //binding.srlResults.isRefreshing = true
                    //val events = getAdEventsInRealtime()
                    //EventBus.instance().publish(events.first())
                    lifecycleScope.launch { presenter.registerAd() }
                }

            }

            setOnLongClickListener { _ ->
                lifecycleScope.launch {
                    //binding.srlResults.isRefreshing = true
                    //EventBus.instance().publish(SportEvent.CloseAdEvent)
                    //view.visibility = View.GONE
                    lifecycleScope.launch { presenter.closeAd() }
                }
                true
            }
        }
    }

    /*private fun getEvents() {
        lifecycleScope.launch {
            /*val events = getResultEventsInRealtime()
            events.forEach { event ->
                delay(someTime())
                EventBus.instance().publish(event)
            }*/
            presenter.getEvents()
        }
    }*/

    override fun onStart() {
        super.onStart()
        //binding.srlResults.isRefreshing = true
        //getEvents()
        lifecycleScope.launch { presenter.getEvents() }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    /*
        OnclickListener
     */
    override fun onClick(result: SportEvent.ResultSuccess) {
        lifecycleScope.launch {
            //binding.srlResults.isRefreshing = true
            //EventBus.instance().publish(SportEvent.SaveEvent)
            //SportService.instance().saveResult(result)
            presenter.saveResult(result)
        }
    }

    /*
    * View Layer
    * */

    override fun add(event: SportEvent.ResultSuccess) {
        adapter.add(event)
    }

    override fun clearAdapter(){
        adapter.clear()
    }

    override suspend fun showAdUI(isVisible: Boolean) = withContext(Dispatchers.Main) {
        binding.btnAd.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showProgress(isVisible: Boolean) {
        binding.srlResults.isRefreshing = isVisible
    }

    override suspend fun showToast(msg: String) = withContext(Dispatchers.Main){
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
    }

    override fun showSnackbar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
}