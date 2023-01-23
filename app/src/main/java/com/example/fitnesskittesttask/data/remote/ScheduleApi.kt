package com.example.fitnesskittesttask.data.remote

import com.example.fitnesskittesttask.data.GetScheduleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {

    @GET("schedule/get_v3/?club_id=2")
    suspend fun getSchedule(): Response<GetScheduleResponse>
}