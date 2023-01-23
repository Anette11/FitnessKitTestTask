package com.example.fitnesskittesttask.data

data class GetScheduleResponse(
    val lessons: List<Lesson> = emptyList(),
    val option: Option?,
    val tabs: List<Tab> = emptyList(),
    val trainers: List<Trainer> = emptyList()
)