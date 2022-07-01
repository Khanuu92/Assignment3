package com.example.bilalhamid.model

data class MusicModel (
    val resultCount: Int,
    val results: List<ResultMusicModel>
    )

data class ResultMusicModel(
    val trackName:String,
    val artistName:String,
    val collectionName:String,
    val artworkUrl100:String,
    val trackPrice:Double,
    val previewUrl:String
)
