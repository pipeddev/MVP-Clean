package com.pipe.d.dev.clean.mainModule.model

import com.pipe.d.dev.clean.common.EventBus
import com.pipe.d.dev.clean.common.SportEvent
import com.pipe.d.dev.clean.common.getAdEventsInRealtime
import com.pipe.d.dev.clean.common.getResultEventsInRealtime
import com.pipe.d.dev.clean.common.someTime
import kotlinx.coroutines.delay

class MainRepositoryImpl(
    private val ds: DataSource
): MainRepository {
    override suspend fun getEvents() {
        val events = ds.getAllEvents()
        events.forEach { event ->
            delay(someTime())
            publishEvent(event)
        }
    }

    override suspend fun saveResult(result: SportEvent.ResultSuccess) {
        val response = ds.save(result)
        publishEvent(response)
    }

    override suspend fun registerAd() {
        val event = ds.registerAd()
        publishEvent(event)
    }

    private suspend fun publishEvent(event: SportEvent) {
        EventBus.instance().publish(event)
    }
}