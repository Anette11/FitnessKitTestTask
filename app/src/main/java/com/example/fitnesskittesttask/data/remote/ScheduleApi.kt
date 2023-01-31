package com.example.fitnesskittesttask.data.remote

import com.example.fitnesskittesttask.data.GetScheduleResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ScheduleApi {

    @GET("schedule/get_v3/?club_id=2")
    fun getSchedules(): Observable<GetScheduleResponse>
}