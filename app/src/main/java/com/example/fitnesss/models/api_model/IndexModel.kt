package com.example.fitnesss.models.api_model

import com.google.gson.annotations.SerializedName

data class IndexModel(
    val bmi: Double,
    val health: String,
    @SerializedName("healthy_bmi_range")
    val healthyBmiRange: String
)