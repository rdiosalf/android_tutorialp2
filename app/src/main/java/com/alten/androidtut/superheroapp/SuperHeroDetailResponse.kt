package com.alten.androidtut.superheroapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailResponse  (
  @SerializedName("name") val name:String,
  @SerializedName("powerstats") val powerstats : PowerStarsResponse,
  @SerializedName("image") val image: SuperHeroImageDetailResponse,
  @SerializedName("biography") val biography: BiographyResponse
)

data class PowerStarsResponse(
  @SerializedName("intelligence") val intelligence:String,
  @SerializedName("strength") val strength:String,
  @SerializedName("speed") val speed:String,
  @SerializedName("durability") val durability:String,
  @SerializedName("power") val power:String,
  @SerializedName("combat") val combat:String
)

data class SuperHeroImageDetailResponse(
  @SerializedName("url") val url:String
)


data class BiographyResponse(
  @SerializedName("full-name") val fullName:String,
  @SerializedName("publisher") val publisher:String
)
