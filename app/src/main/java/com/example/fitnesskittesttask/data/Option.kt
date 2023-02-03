package com.example.fitnesskittesttask.data

import com.google.gson.annotations.SerializedName

data class Option(

    @SerializedName("club_name")
    val clubName: String?,

    @SerializedName("link_android")
    val link_android: String?,

    @SerializedName("link_ios")
    val linkIos: String?,

    @SerializedName("primary_color")
    val primaryColor: String?,

    @SerializedName("secondary_color")
    val secondaryColor: String?
)