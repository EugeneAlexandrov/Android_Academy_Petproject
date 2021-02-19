package com.mybclym.androidacademy.petproject.dataModel.domainModel

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)