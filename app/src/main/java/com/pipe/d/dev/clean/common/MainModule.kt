package com.pipe.d.dev.clean.common

import com.pipe.d.dev.clean.mainModule.view.OnClickListener
import com.pipe.d.dev.clean.mainModule.view.ResultAdapter
import org.koin.dsl.module

val mainModule = module {
    factory<ResultAdapter> { (listener: OnClickListener) -> ResultAdapter(listener) }
}
