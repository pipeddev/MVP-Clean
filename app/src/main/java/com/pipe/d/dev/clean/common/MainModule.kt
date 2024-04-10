package com.pipe.d.dev.clean.common

import com.pipe.d.dev.clean.mainModule.model.DataSourceImpl
import org.koin.dsl.module

val mainModule = module {
    single {  }
    factory { DataSourceImpl() }
}
