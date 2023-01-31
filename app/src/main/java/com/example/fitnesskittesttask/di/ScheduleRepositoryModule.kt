package com.example.fitnesskittesttask.di

import com.example.fitnesskittesttask.repository.ScheduleRepository
import com.example.fitnesskittesttask.repository.ScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository
}