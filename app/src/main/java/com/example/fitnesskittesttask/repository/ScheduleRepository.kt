package com.example.fitnesskittesttask.repository

import com.example.fitnesskittesttask.adapter.Item
import com.example.fitnesskittesttask.data.GetScheduleResponse
import com.example.fitnesskittesttask.data.Lesson
import com.example.fitnesskittesttask.data.Trainer
import com.example.fitnesskittesttask.data.local.LessonDbo
import com.example.fitnesskittesttask.data.local.TrainerDbo
import io.reactivex.rxjava3.core.Observable

interface ScheduleRepository {

    fun getSchedules(): Observable<GetScheduleResponse>

    fun createExercisesList(
        lessons: List<LessonDbo>,
        trainers: List<TrainerDbo>
    ): List<Item>

    fun refreshSchedulesInDatabase(
        lessons: List<Lesson>,
        trainers: List<Trainer>
    )

    fun getSchedulesFromDatabase(): List<Item>
}