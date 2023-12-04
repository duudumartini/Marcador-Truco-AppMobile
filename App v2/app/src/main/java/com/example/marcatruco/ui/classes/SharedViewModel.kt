package com.example.marcatruco.ui.classes

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {
    var listaVencedores: MutableList<VencedorClass> = mutableListOf()
    public lateinit var sistemaDeFala: TextToSpeech
    var som: Boolean = true
    var vibracao: Boolean = true
    var pontosVit = 12

    fun pequenaVibracao(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (vibracao && vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(200)
            }
        }
    }

    fun grandeVibracao(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (vibracao && vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        1000,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(2000)
            }
        }
    }

    fun fala(context: Context, fala: String) {
        sistemaDeFala = TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                if (som && !sistemaDeFala.isSpeaking) {
                    sistemaDeFala.speak(fala, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }
    }


}
