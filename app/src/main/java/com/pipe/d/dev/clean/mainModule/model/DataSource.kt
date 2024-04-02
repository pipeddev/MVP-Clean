package com.pipe.d.dev.clean.mainModule.model

import com.pipe.d.dev.clean.common.SportEvent

interface DataSource {
    fun save(result: SportEvent.ResultSuccess): SportEvent
    fun getAllEvents(): List<SportEvent>
    fun registerAd(): SportEvent.AdEvent
}