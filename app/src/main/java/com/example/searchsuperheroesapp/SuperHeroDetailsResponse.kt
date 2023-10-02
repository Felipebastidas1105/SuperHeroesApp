package com.example.searchsuperheroesapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailsResponse(
    @SerializedName("name") val name:String,
    @SerializedName("powerstats") val powerStats:PowerStatsResponse,
    @SerializedName("image") val superHeroImg:SuperHeroImageDetailResponse,
    @SerializedName("biography") val biography:SuperHeroBiographyResponse
)
data class PowerStatsResponse(
    @SerializedName("intelligence") val intelligence:String,
    @SerializedName("strength") val strength:String,
    @SerializedName("speed") val speed:String,
    @SerializedName("durability") val durability:String,
    @SerializedName("power") val power:String,
    @SerializedName("combat") val combat:String,
)
data class SuperHeroImageDetailResponse(@SerializedName("url") val url:String)

data class SuperHeroBiographyResponse(
    @SerializedName("full-name") val fullName:String,
//    @SerializedName("alter-egos") val alterEgos:String,
//    @SerializedName("aliases") val aliases:List<String>,
//    @SerializedName("first-appearance") val firstAppearance:String,
    @SerializedName("publisher") val publisher:String,
//    @SerializedName("alignment") val alignment:String
)