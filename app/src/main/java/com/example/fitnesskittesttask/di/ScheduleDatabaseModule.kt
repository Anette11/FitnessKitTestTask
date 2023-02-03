package com.example.fitnesskittesttask.di

import android.content.Context
import com.example.fitnesskittesttask.data.local.util.LocalConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleDatabaseModule {

    @Provides
    @Singleton
    fun provideRealmDatabase(
        @ApplicationContext context: Context
    ): Realm = io.reactivex.rxjava3.core.Observable.create { emitter ->
        Realm.init(context)
        val realmConfiguration = RealmConfiguration.Builder()
            .name(LocalConstants.databaseName)
            .schemaVersion(LocalConstants.databaseSchemaVersion)
            .allowWritesOnUiThread(false)
            .allowQueriesOnUiThread(false)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        val realm = Realm.getDefaultInstance()
        emitter.onNext(realm)
    }.subscribeOn(Schedulers.single())
        .blockingFirst()
}