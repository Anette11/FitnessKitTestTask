package com.example.fitnesskittesttask.ui.fragments.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesskittesttask.BuildConfig
import com.example.fitnesskittesttask.R
import com.example.fitnesskittesttask.adapter.Item
import com.example.fitnesskittesttask.data.GetScheduleResponse
import com.example.fitnesskittesttask.repository.ScheduleRepository
import com.example.fitnesskittesttask.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val repository: ScheduleRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _exercises: MutableLiveData<List<Item>> = MutableLiveData(emptyList())
    val exercises: LiveData<List<Item>> = _exercises

    private val _toast: MutableLiveData<String?> = MutableLiveData()
    val toast: LiveData<String?> = _toast

    fun refreshToast() {
        _toast.value = null
    }

    private fun initialSchedulesLoad() {
        Observable.create { emitter ->
            val list = repository.getSchedulesFromDatabase()
            emitter.onNext(list)
        }
            .map { list: List<Item> -> _exercises.postValue(list) }
            .subscribeOn(Schedulers.single())
            .subscribe(object : Observer<Unit> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                    _progressBar.postValue(true)
                }

                override fun onNext(t: Unit) {
                    repository.getSchedules()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.single())
                        .subscribe(object : Observer<GetScheduleResponse> {
                            override fun onSubscribe(d: Disposable) {
                                compositeDisposable.add(d)
                                _progressBar.postValue(true)
                            }

                            override fun onNext(t: GetScheduleResponse) {
                                Observable.create { emitter ->
                                    repository.refreshSchedulesInDatabase(
                                        lessons = t.lessons,
                                        trainers = t.trainers
                                    )
                                    val list = repository.getSchedulesFromDatabase()
                                    emitter.onNext(list)
                                }
                                    .map { list: List<Item> -> _exercises.postValue(list) }
                                    .subscribeOn(Schedulers.single())
                                    .observeOn(Schedulers.single())
                                    .subscribe(object : Observer<Unit> {
                                        override fun onSubscribe(d: Disposable) {
                                            compositeDisposable.add(d)
                                            _progressBar.postValue(true)
                                        }

                                        override fun onNext(t: Unit) {}

                                        override fun onError(e: Throwable) {
                                            _progressBar.postValue(false)
                                            _toast.postValue(resourcesProvider.getString(R.string.toast_generic_error_message))
                                            if (BuildConfig.DEBUG) e.printStackTrace()
                                        }

                                        override fun onComplete() = _progressBar.postValue(false)
                                    })
                            }

                            override fun onError(e: Throwable) {
                                _progressBar.postValue(false)
                                _toast.postValue(resourcesProvider.getString(R.string.toast_generic_error_message))
                                if (BuildConfig.DEBUG) e.printStackTrace()
                            }

                            override fun onComplete() = _progressBar.postValue(false)
                        })
                }

                override fun onError(e: Throwable) {
                    _progressBar.postValue(false)
                    _toast.postValue(resourcesProvider.getString(R.string.toast_generic_error_message))
                    if (BuildConfig.DEBUG) e.printStackTrace()
                }

                override fun onComplete() = _progressBar.postValue(false)
            })
    }

    fun refreshSchedulesLoad() {
        compositeDisposable.clear()
        repository.getSchedules()
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .subscribe(object : Observer<GetScheduleResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                    _progressBar.postValue(true)
                }

                override fun onNext(t: GetScheduleResponse) {
                    Observable.create { emitter ->
                        repository.refreshSchedulesInDatabase(
                            lessons = t.lessons,
                            trainers = t.trainers
                        )
                        val list = repository.getSchedulesFromDatabase()
                        emitter.onNext(list)
                    }
                        .map { list: List<Item> -> _exercises.postValue(list) }
                        .subscribeOn(Schedulers.single())
                        .observeOn(Schedulers.single())
                        .subscribe(object : Observer<Unit> {
                            override fun onSubscribe(d: Disposable) {
                                compositeDisposable.add(d)
                                _progressBar.postValue(true)
                            }

                            override fun onNext(t: Unit) {}

                            override fun onError(e: Throwable) {
                                _progressBar.postValue(false)
                                _toast.postValue(resourcesProvider.getString(R.string.toast_generic_error_message))
                                if (BuildConfig.DEBUG) e.printStackTrace()
                            }

                            override fun onComplete() = _progressBar.postValue(false)
                        })
                }

                override fun onError(e: Throwable) {
                    _progressBar.postValue(false)
                    _toast.postValue(resourcesProvider.getString(R.string.toast_generic_error_message))
                    if (BuildConfig.DEBUG) e.printStackTrace()
                }

                override fun onComplete() = _progressBar.postValue(false)
            })
    }

    fun clearCompositeDisposable() {
        compositeDisposable.clear()
        _progressBar.value = false
    }

    init {
        initialSchedulesLoad()
    }
}