package com.example.fitnesskittesttask.adapter

sealed class Item {

    data class Date(
        val date: String
    ) : Item()

    data class Training(
        val from: String,
        val to: String,
        val training: String,
        val trainer: String,
        val place: String
    ) : Item()
}
