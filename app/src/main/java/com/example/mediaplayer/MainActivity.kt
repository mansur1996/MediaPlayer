package com.example.mediaplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayer.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer : MediaPlayer? = null
//    private var audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    private var audioUrl = "https://www.youtube.com/watch?v=WVl6g5hvcDA"
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(mainLooper)
        initViews()
    }

    private fun initViews() {
        binding.btnPlay.setOnClickListener {
            playMusic()
        }

        binding.btnResume.setOnClickListener {
            resumeMusic()
        }

        binding.btnPause.setOnClickListener {
            pauseMusic()
        }
        binding.btnStop.setOnClickListener {
            stopMusic()
        }

        binding.btnBackward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.minus(5000)!!)
        }

        binding.btnForward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.plus(5000)!!)
        }

        binding.seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
        object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar : SeekBar?, progress: Int, fromUser : Boolean) {
                if (fromUser){
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }


    private fun playMusic() {
        if(mediaPlayer == null){
        //by raw
            mediaPlayer = MediaPlayer.create(this, R.raw.xamdam_sobirov_holimga_qara)
            mediaPlayer?.start()
            binding.seekBar.max = mediaPlayer?.duration!!
            handler.postDelayed(runnable, 100)
        //by Url
//            mediaPlayer = MediaPlayer()
//            mediaPlayer?.setDataSource(audioUrl)
//            mediaPlayer?.setOnPreparedListener(this)
//            mediaPlayer?.prepareAsync()
        }
    }

    private var runnable = object : Runnable{
        override fun run() {

            binding.seekBar.progress = mediaPlayer?.currentPosition!!
            handler.postDelayed(this, 100)
        }

    }

    private fun resumeMusic() {
        if (!mediaPlayer?.isPlaying!!) {
            mediaPlayer?.start()
        }
    }

    private fun pauseMusic() {
        if (mediaPlayer?.isPlaying!!) {
            mediaPlayer?.pause()
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
    }

    private fun releaseMP(){
        if(mediaPlayer != null){
            try {
                mediaPlayer?.release()
                mediaPlayer = null
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMP()
    }
}