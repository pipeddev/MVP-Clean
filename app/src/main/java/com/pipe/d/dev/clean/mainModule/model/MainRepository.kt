package com.pipe.d.dev.clean.mainModule.model

import com.pipe.d.dev.clean.common.SportEvent

interface MainRepository {
    suspend fun getEvents()
    suspend fun saveResult(result: SportEvent.ResultSuccess)
    suspend fun registerAd()
    //suspend fun closeAd()
}