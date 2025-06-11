package com.alten.androidtut.superheroapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
  @GET("/api/945298e9e73982b94f7a94326743de91/search/{name}")
  suspend fun getSuperheroes(@Path("name") superheroName:String):Response<SuperHeroDataResponse>

  @GET("/api/945298e9e73982b94f7a94326743de91/{id}")
  suspend fun getSuperheroDetail(@Path("id") superheroName:String):Response<SuperHeroDetailResponse>

}