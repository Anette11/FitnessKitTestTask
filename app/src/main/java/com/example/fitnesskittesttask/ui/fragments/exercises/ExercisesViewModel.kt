package com.example.fitnesskittesttask.ui.fragments.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesskittesttask.adapter.Item
import com.example.fitnesskittesttask.data.GetScheduleResponse
import com.example.fitnesskittesttask.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _exercises: MutableLiveData<List<Item>> = MutableLiveData(emptyList())
    val exercises: LiveData<List<Item>> = _exercises

    private fun getSchedules() = repository.getSchedules()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<GetScheduleResponse> {
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                _progressBar.value = true
            }

            override fun onError(e: Throwable) {
                _progressBar.value = false
            }

            override fun onComplete() {
                _progressBar.value = false
            }

            override fun onNext(t: GetScheduleResponse) {
                val exercises = t.lessons.map { lesson ->
                    Item.Training(
                        from = lesson.startTime ?: "N/A",
                        to = lesson.endTime ?: "N/A",
                        training = lesson.name ?: "N/A",
                        trainer = "N/A",
                        place = lesson.place ?: "N/A"
                    )
                }
                _exercises.value = exercises
            }
        })

    init {
        getSchedules()
    }

    fun clearCompositeDisposable() = compositeDisposable.dispose()
}