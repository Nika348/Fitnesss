package com.example.fitnesss.models.api_model

import com.google.gson.annotations.SerializedName

data class IdealWeightModel(
    @SerializedName("Devine")
    val devine: Double,
    @SerializedName("Hamwi")
    val hamwi: Double,
    @SerializedName("Miller")
    val miller: Double,
    @SerializedName("Robinson")
    val robinson: Double
)