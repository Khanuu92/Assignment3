package com.example.bilalhamid.api


import com.example.bilalhamid.model.MusicModel

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceClassic {
   // https://itunes.apple.com/search?term=pop&amp;media=music&amp;entity=song&amp;limit=50
    @GET("search")
    fun getClassicMusic(
       @Query("term") term:String ="classic",
       @Query(";media") media:String ="music",
       @Query(";entity") entity:String ="song",
       @Query(";limit") limit:Int =50):Call<MusicModel>

    @GET("search")
    fun getPopMusic(
        @Query("term") term:String ="pop",
        @Query(";media") media:String ="music",
        @Query(";entity") entity:String ="song",
        @Query(";limit") limit:Int =50):Call<MusicModel>

    @GET("search")
    fun getRockMusic(
        @Query("term") term:String ="rock",
        @Query(";media") media:String ="music",
        @Query(";entity") entity:String ="song",
        @Query(";limit") limit:Int =50):Call<MusicModel>



    companion object{
        private var instance : Retrofit?=null
        fun createRetrofit() :Retrofit{
            if(instance==null){
                instance=Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance!!
        }
    }
}