package com.example.fitnesskittesttask.repository

import android.database.Observable
import com.example.fitnesskittesttask.data.GetScheduleResponse

interface ScheduleRepository {

    fun getSchedules(): Observable<GetScheduleResponse>
}