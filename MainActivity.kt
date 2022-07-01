package com.example.bilalhamid.view

import android.content.Context
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.bilalhamid.R
import com.example.bilalhamid.databinding.ActivityMainBinding
import com.example.bilalhamid.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)


        tabs.getTabAt(0)?.setIcon(R.drawable.rock_music)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_baseline_queue_music_24)
        tabs.getTabAt(2)?.setIcon(R.drawable.pop_icon)


    }
    companion object {
         var mediaPlayer = MediaPlayer()


        fun playSong(url: String) {
            try {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }

                mediaPlayer.reset()
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                    return true
                }

            }
            Toast.makeText(context, "Please Check your Internet Connection!", Toast.LENGTH_LONG)
                .show()
            return false
        }
        fun makeToastmsg(msg:String,context:Context){
            Toast.makeText(context, msg, Toast.LENGTH_LONG)
                .show()
        }
    }

    }
