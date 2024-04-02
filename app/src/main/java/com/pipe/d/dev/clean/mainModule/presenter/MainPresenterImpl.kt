package com.pipe.d.dev.clean.mainModule.presenter

import android.util.Log
import com.pipe.d.dev.clean.common.EventBus
import com.pipe.d.dev.clean.common.SportEvent
import com.pipe.d.dev.clean.mainModule.model.MainRepository
import com.pipe.d.dev.clean.mainModule.model.MainRepositoryImpl
import com.pipe.d.dev.clean.mainModule.view.MainActivity
import com.pipe.d.dev.clean.mainModule.view.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainPresenterImpl(private val view: MainView,
                        private val repository: MainRepository): MainPresenter {
    //private val repository = MainRepositoryImpl()
    private lateinit var viewScope: CoroutineScope

    override fun onCreate() {
        viewScope = CoroutineScope(Dispatchers.IO + Job())
        onEvent()
    }

    override fun onDestroy() {
        viewScope.cancel()
    }

    override suspend fun refresh() {
        view.clearAdapter()  //adapter.clear()
        getEvents()
        view.showAdUI(true) //binding.btnAd.visibility = View.VISIBLE
    }

    override suspend fun getEvents() {
        view.showProgress(true)
        repository.getEvents()
    }

    override suspend fun registerAd() {
        repository.registerAd()
    }

    override suspend fun closeAd() {
        view.showAdUI(false)
        Log.i("MainPresenter", "Ad was closed. Send data to server.....")
    }

    override suspend fun saveResult(result: SportEvent.ResultSuccess) {
        view.showProgress(true)
        repository.saveResult(result)
    }

    private fun onEvent() {
        viewScope.launch {
            EventBus.instance().subscribe<SportEvent> { event ->
                this.launch {
                    when(event) {
                        is SportEvent.ResultSuccess -> {
                            view.add(event)
                            view.showProgress(false)
                        }
                        is SportEvent.ResultError -> {
                            view.showSnackbar("Code: ${event.msg}, Message: ${event.msg}")
                            view.showProgress(false)
                        }
                        is SportEvent.AdEvent -> {
                            view.showToast("Ad Click, Send data to server")
                        }
                        is SportEvent.SaveEvent -> {
                            view.showToast("Guardado")
                            view.showProgress(false)
                        }
                    }
                }

            }
        }
    }

}