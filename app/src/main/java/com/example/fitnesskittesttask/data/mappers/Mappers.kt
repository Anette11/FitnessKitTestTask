package com.example.fitnesskittesttask.data.mappers

import com.example.fitnesskittesttask.data.Lesson
import com.example.fitnesskittesttask.data.Trainer
import com.example.fitnesskittesttask.data.local.LessonDbo
import com.example.fitnesskittesttask.data.local.TrainerDbo
import java.util.*

fun Lesson.toLessonDbo(): LessonDbo =
    LessonDbo().apply {
        dbId = UUID.randomUUID().toString()
        appointmentId = this@toLessonDbo.appointmentId
        availableSlots = this@toLessonDbo.availableSlots
        clientRecorded = this@toLessonDbo.clientRecorded
        coachId = this@toLessonDbo.coachId
        color = this@toLessonDbo.color
        commercial = this@toLessonDbo.commercial
        date = this@toLessonDbo.date
        description = this@toLessonDbo.description
        endTime = this@toLessonDbo.endTime
        isCancelled = this@toLessonDbo.isCancelled
        name = this@toLessonDbo.name
        place = this@toLessonDbo.place
        serviceId = this@toLessonDbo.serviceId
        startTime = this@toLessonDbo.startTime
        tab = this@toLessonDbo.tab
        tabId = this@toLessonDbo.tabId
    }

fun Trainer.toTrainerDbo(): TrainerDbo =
    TrainerDbo().apply {
        dbId = UUID.randomUUID().toString()
        description = this@toTrainerDbo.description
        fullName = this@toTrainerDbo.fullName
        id = this@toTrainerDbo.id
        imageUrl = this@toTrainerDbo.imageUrl
        imageUrlMedium = this@toTrainerDbo.imageUrlMedium
        imageUrlSmall = this@toTrainerDbo.imageUrlSmall
        lastName = this@toTrainerDbo.lastName
        name = this@toTrainerDbo.name
        position = this@toTrainerDbo.position
    }