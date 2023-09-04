package com.example.searchsuperheroesapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superHeroes: List<SuperHeroItem>
)

data class SuperHeroItem(
    @SerializedName("id") val superHeroId: String,
    @SerializedName("name") val superHeroName: String,
    @SerializedName("image") val superHeroImage: SuperHeroImage
)
data class SuperHeroImage(
    @SerializedName("url") val url:String
)