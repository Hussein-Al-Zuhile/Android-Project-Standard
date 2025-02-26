package com.hussein.androidprojectstandard.di

import com.hussein.androidprojectstandard.data.datasource.remote.http.ExampleHttpClient
import com.hussein.androidprojectstandard.data.datasource.remote.mqtt.ExampleMQTTClient
import com.hussein.androidprojectstandard.data.repository.MainRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MainModule = module {
    single { ExampleHttpClient }
    singleOf(::MainRepository)
    singleOf(::ExampleMQTTClient)
}