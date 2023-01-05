package com.example.fitnesss.data.api

import com.example.fitnesss.models.api_model.BaseModelResponse
import com.example.fitnesss.models.api_model.IdealWeightModel
import com.example.fitnesss.models.api_model.IndexModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("bmi")
    suspend fun getIndex(
        @Query("age")
        age: Int,
        @Query("weight")
        weight: Int,
        @Query("height")
        height: Int
    ): Response<BaseModelResponse<IndexModel>>

    @GET("idealweight")
    suspend fun getIdealWeight(
        @Query("gender")
        gender: String,
        @Query("height")
        height: Int
    ): Response<BaseModelResponse<IdealWeightModel>>

}