package com.alten.androidtut.superheroapp

import com.google.gson.annotations.SerializedName

//data class SuperHeroDataResponse (@SerializedName("response") val response : String) con esto solo llego a la response del arbol donde el arbol tenia 3 cosas, entre ella los resultados a la búsqueda
data class SuperHeroDataResponse (
  @SerializedName("response") val response : String,
  @SerializedName("results") val superheros : List<SuperHeroItemResponse>
  )

data class SuperHeroItemResponse(
  @SerializedName("id") val superheroId: String,
  @SerializedName("name") val name: String,
  @SerializedName("image") val superheroImage: SuperHeroImageResponse
)

data class SuperHeroImageResponse(
  @SerializedName("url") val url: String
)