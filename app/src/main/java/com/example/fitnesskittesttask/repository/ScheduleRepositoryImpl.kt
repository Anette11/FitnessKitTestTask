package com.example.fitnesskittesttask.repository

import com.example.fitnesskittesttask.data.GetScheduleResponse
import com.example.fitnesskittesttask.data.remote.ScheduleApi
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val api: ScheduleApi
) : ScheduleRepository {

    override fun getSchedules(): Observable<GetScheduleResponse> = api.getSchedules()
}