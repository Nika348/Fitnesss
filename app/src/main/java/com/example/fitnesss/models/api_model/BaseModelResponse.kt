package com.example.fitnesss.models.api_model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class BaseModelResponse<T>(
    val data: T,
    @SerializedName("request_result")
    val requestResult: String,
    @SerializedName("status_code")
    val statusCode: Int
)

