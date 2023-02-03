package com.example.fitnesskittesttask.data.local

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class LessonDbo : RealmModel {
    @PrimaryKey
    var dbId: String? = null
    var appointmentId: String? = null
    var availableSlots: Int? = null
    var clientRecorded: Boolean? = null
    var coachId: String? = null
    var color: String? = null
    var commercial: Boolean? = null
    var date: String? = null
    var description: String? = null
    var endTime: String? = null
    var isCancelled: Boolean? = null
    var name: String? = null
    var place: String? = null
    var serviceId: String? = null
    var startTime: String? = null
    var tab: String? = null
    var tabId: Int? = null
}