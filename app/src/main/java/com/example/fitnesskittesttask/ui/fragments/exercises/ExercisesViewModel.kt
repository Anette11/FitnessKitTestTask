package com.example.fitnesskittesttask.ui.fragments.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesskittesttask.R
import com.example.fitnesskittesttask.adapter.Item
import com.example.fitnesskittesttask.data.GetScheduleResponse
import com.example.fitnesskittesttask.repository.ScheduleRepository
import com.example.fitnesskittesttask.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val repository: ScheduleRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBar: LiveData<Boolean> = _progressBar

    private val _exercises: MutableLiveData<List<Item>> = MutableLiveData(emptyList())
    val exercises: LiveData<List<Item>> = _exercises

    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String> = _toast

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
                _toast.value = resourcesProvider.getString(R.string.toast_generic_error_message)
            }

            override fun onComplete() {
                _progressBar.value = false
            }

            override fun onNext(t: GetScheduleResponse) {
                val trainers = t.trainers
                val exercises = mutableListOf<Item>()
                t.lessons
                    .filter { lesson -> createDateFromString(lesson.date) != null }
                    .sortedBy { lesson -> createDateFromString(date = lesson.date) }
                    .forEach { lesson ->
                        val date = Item.Date(date = lesson.date ?: "N/A")
                        if (exercises.contains(date).not()) {
                            exercises.add(Item.Date(date = lesson.date ?: "N/A"))
                        }
                        exercises.add(
                            Item.Training(
                                from = lesson.startTime ?: "N/A",
                                to = lesson.endTime ?: "N/A",
                                training = lesson.name ?: "N/A",
                                trainer = trainers.find { trainer ->
                                    trainer.id == lesson.coach_id
                                }?.name ?: "N/A",
                                place = lesson.place ?: "N/A",
                                color = lesson.color ?: "#F8F8F8"
                            )
                        )
                    }
                exercises.forEach { item ->
                    if (item is Item.Date) {
                        val transformedDate = createDateFromString(item.date)
                        val transformedDateString = createStringFromDate(transformedDate)
                        item.date = transformedDateString
                    }
                }
                _exercises.value = exercises
            }
        })

    init {
        getSchedules()
    }

    private fun createDateFromString(date: String?): Date? {
        if (date == null) return null
        return try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            simpleDateFormat.parse(date)
        } catch (e: ParseException) {
            null
        }
    }

    private fun createStringFromDate(date: Date?): String {
        if (date == null) return "N/A"
        return try {
            val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
            simpleDateFormat.format(date)
        } catch (e: ParseException) {
            "N/A"
        }
    }

    fun clearCompositeDisposable() = compositeDisposable.dispose()
}