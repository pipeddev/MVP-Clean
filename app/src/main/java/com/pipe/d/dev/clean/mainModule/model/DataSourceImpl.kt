package com.pipe.d.dev.clean.mainModule.model

import com.pipe.d.dev.clean.common.SportEvent
import com.pipe.d.dev.clean.common.getAdEventsInRealtime
import com.pipe.d.dev.clean.common.getResultEventsInRealtime

class DataSourceImpl: DataSource {
    override fun save(result: SportEvent.ResultSuccess): SportEvent {
        return if (result.isWarning)
            SportEvent.ResultError(30, "Error al guardar.")
        else SportEvent.SaveEvent
    }

    override fun getAllEvents(): List<SportEvent> {
        return getResultEventsInRealtime()
    }

    override fun registerAd(): SportEvent.AdEvent {
        return getAdEventsInRealtime().first()
    }
}