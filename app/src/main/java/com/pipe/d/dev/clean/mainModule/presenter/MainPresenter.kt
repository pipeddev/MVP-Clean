package com.pipe.d.dev.clean.mainModule.presenter

import com.pipe.d.dev.clean.common.SportEvent

interface MainPresenter {
    fun onCreate()
    fun onDestroy()
    suspend fun refresh()
    suspend fun getEvents()
    suspend fun registerAd()
    suspend fun closeAd()
    suspend fun saveResult(result: SportEvent.ResultSuccess)
}