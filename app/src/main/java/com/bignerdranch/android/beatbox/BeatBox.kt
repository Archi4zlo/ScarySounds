package com.bignerdranch.android.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val max_sounds = 5
class BeatBox(private val assets: AssetManager) {

    private var rateSound = 1.0f
    val sounds: List<Sound>
    private val soundPool =  SoundPool.Builder()
            .setMaxStreams(max_sounds)
            .build()
    init {
        sounds = loadSounds()
    }
    fun play(sound: Sound){
        sound.soundId?.let {
            soundPool.play(it,1.0f,1.0f,1,0,rateSound)
        }
    }
    fun release(){
        soundPool.release()
    }

    //Получение списка активов
    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>
        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }

        //Создание объектов Sound
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException){
                Log.e(TAG,"Cound not load sound $filename", ioe)
            }
        }
        return sounds
    }
    fun setSpeed(speedSound: Int){
        if (speedSound==0){
            rateSound =0.1f
        }
            rateSound = speedSound.toFloat()/50

    }
    private fun load(sound: Sound){
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd,1)
        sound.soundId = soundId
    }




}