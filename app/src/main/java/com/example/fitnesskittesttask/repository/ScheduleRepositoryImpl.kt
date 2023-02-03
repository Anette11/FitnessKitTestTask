package com.example.fitnesskittesttask.repository

import com.example.fitnesskittesttask.R
import com.example.fitnesskittesttask.adapter.Item
import com.example.fitnesskittesttask.data.GetScheduleResponse
import com.example.fitnesskittesttask.data.Lesson
import com.example.fitnesskittesttask.data.Trainer
import com.example.fitnesskittesttask.data.local.LessonDbo
import com.example.fitnesskittesttask.data.local.TrainerDbo
import com.example.fitnesskittesttask.data.mappers.toLessonDbo
import com.example.fitnesskittesttask.data.mappers.toTrainerDbo
import com.example.fitnesskittesttask.data.remote.ScheduleApi
import com.example.fitnesskittesttask.util.ResourcesProvider
import com.example.fitnesskittesttask.util.createDateFromString
import com.example.fitnesskittesttask.util.createDuration
import com.example.fitnesskittesttask.util.createStringFromDate
import io.reactivex.rxjava3.core.Observable
import io.realm.Realm
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val api: ScheduleApi,
    private val resourcesProvider: ResourcesProvider,
    private val realm: Realm
) : ScheduleRepository {

    override fun getSchedules(): Observable<GetScheduleResponse> = api.getSchedules()

    override fun getSchedulesFromDatabase(): List<Item> {
        val reamResultLessons = realm.where(LessonDbo::class.java).findAll()
        val lessons = reamResultLessons?.subList(0, reamResultLessons.size)
            ?: emptyList()
        val reamResultTrainers = realm.where(TrainerDbo::class.java).findAll()
        val trainers = reamResultTrainers?.subList(0, reamResultTrainers.size)
            ?: emptyList()
        return createExercisesList(
            lessons = lessons,
            trainers = trainers
        )
    }

    override fun refreshSchedulesInDatabase(
        lessons: List<Lesson>,
        trainers: List<Trainer>
    ) {
        realm.executeTransaction { realm ->
            realm.delete(LessonDbo::class.java)
            realm.insert(lessons.map { lesson -> lesson.toLessonDbo() })
        }
        realm.executeTransaction { realm ->
            realm.delete(TrainerDbo::class.java)
            realm.insert(trainers.map { trainer -> trainer.toTrainerDbo() })
        }
    }

    override fun createExercisesList(
        lessons: List<LessonDbo>,
        trainers: List<TrainerDbo>
    ): List<Item> {
        val exercises = mutableListOf<Item>()
        lessons
            .filter { lesson -> lesson.date.createDateFromString() != null }
            .sortedBy { lesson ->
                lesson.startTime ?: resourcesProvider.getString(R.string.default_value)
            }
            .sortedBy { lesson -> lesson.date.createDateFromString() }
            .forEach { lesson ->
                val date = Item.Date(
                    date = lesson.date ?: resourcesProvider.getString(R.string.default_value)
                )
                if (exercises.contains(date).not()) {
                    exercises.add(
                        Item.Date(
                            date = lesson.date
                                ?: resourcesProvider.getString(R.string.default_value)
                        )
                    )
                }
                exercises.add(
                    Item.Training(
                        from = lesson.startTime
                            ?: resourcesProvider.getString(R.string.default_value),
                        to = lesson.endTime ?: resourcesProvider.getString(R.string.default_value),
                        training = lesson.name
                            ?: resourcesProvider.getString(R.string.default_value),
                        trainer = trainers.find { trainer ->
                            trainer.id == lesson.coachId
                        }?.name ?: resourcesProvider.getString(R.string.default_value),
                        place = lesson.place ?: resourcesProvider.getString(R.string.default_value),
                        color = lesson.color ?: resourcesProvider.getString(R.string.default_color),
                        duration = createDuration(
                            from = lesson.startTime
                                ?: resourcesProvider.getString(R.string.default_value),
                            to = lesson.endTime
                                ?: resourcesProvider.getString(R.string.default_value),
                            defaultValue = resourcesProvider.getString(R.string.default_value)
                        )
                    )
                )
            }
        exercises.forEach { item ->
            if (item is Item.Date) {
                val transformedDate = item.date.createDateFromString()
                val transformedDateString = transformedDate.createStringFromDate(
                    defaultValue = resourcesProvider.getString(R.string.default_value)
                )
                item.date = transformedDateString
            }
        }
        return exercises
    }
}