package com.pipe.d.dev.clean.common

import com.pipe.d.dev.clean.mainModule.model.DataSource
import com.pipe.d.dev.clean.mainModule.model.DataSourceImpl
import com.pipe.d.dev.clean.mainModule.model.MainRepository
import com.pipe.d.dev.clean.mainModule.model.MainRepositoryImpl
import com.pipe.d.dev.clean.mainModule.presenter.MainPresenter
import com.pipe.d.dev.clean.mainModule.presenter.MainPresenterImpl
import com.pipe.d.dev.clean.mainModule.view.MainView
import com.pipe.d.dev.clean.mainModule.view.OnClickListener
import com.pipe.d.dev.clean.mainModule.view.ResultAdapter
import org.koin.dsl.module

fun provideMainRepository(dataSource: DataSource): MainRepository = MainRepositoryImpl(dataSource)
fun provideDataSource(): DataSource = DataSourceImpl()

val mainModule = module {
    factory<ResultAdapter> { (listener: OnClickListener) -> ResultAdapter(listener) }

    single { provideDataSource() }
    single { provideMainRepository(get()) }
    factory<MainPresenter> { (view: MainView) -> MainPresenterImpl(view, provideMainRepository(provideDataSource())) }
}
