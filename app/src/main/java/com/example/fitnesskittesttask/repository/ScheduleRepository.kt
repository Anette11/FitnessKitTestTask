package com.example.fitnesskittesttask.repository

import com.example.fitnesskittesttask.data.GetScheduleResponse
import io.reactivex.rxjava3.core.Observable

interface ScheduleRepository {

    fun getSchedules(): Observable<GetScheduleResponse>
}