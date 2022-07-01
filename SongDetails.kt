package com.example.bilalhamid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.bilalhamid.R
import com.squareup.picasso.Picasso

class SongDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_details)
        val tvCollection: TextView = findViewById(R.id.tvdetailCollectionName)
        val tvPrice: TextView = findViewById(R.id.tvDetailTrackPrice)
        val tvArtist: TextView = findViewById(R.id.tvDetailArtist)
        val tvType: TextView = findViewById(R.id.tvDetailTrack)
        val imageView: ImageView = findViewById(R.id.ivDetailArtView)
        val btnPlay:ImageView=findViewById(R.id.btnImage)
        val bundle: Bundle? =intent.extras

        tvArtist.text = bundle?.getString("aName")
        tvCollection.text = bundle?.getString("c_name")
        tvPrice.text=bundle?.getString("price")
        tvType.text=bundle?.getString("type")
        Picasso.get()
            .load(bundle?.getString("KEY"))
            .placeholder(R.drawable.ic_launcher_foreground)

            .into(imageView)

btnPlay.setOnClickListener{

    run {
        if (MainActivity.mediaPlayer.isPlaying) {
            btnPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
            MainActivity.mediaPlayer.pause()
        }else{
            btnPlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            MainActivity.mediaPlayer.start()
        }

    }
}
    }
}
