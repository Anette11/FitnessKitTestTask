package com.example.fitnesskittesttask.data.local

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class TrainerDbo : RealmModel {
    @PrimaryKey
    var dbId: String? = null
    var description: String? = null
    var fullName: String? = null
    var id: String? = null
    var imageUrl: String? = null
    var imageUrlMedium: String? = null
    var imageUrlSmall: String? = null
    var lastName: String? = null
    var name: String? = null
    var position: String? = null
}