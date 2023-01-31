package com.example.fitnesskittesttask.di

import com.example.fitnesskittesttask.util.ResourcesProvider
import com.example.fitnesskittesttask.util.ResourcesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleResourcesModule {

    @Singleton
    @Binds
    abstract fun bindResourcesProvider(resourcesProviderImpl: ResourcesProviderImpl): ResourcesProvider
}