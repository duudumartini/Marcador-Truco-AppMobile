package com.app.marcatruco.ui.classes

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class SharedViewModel : ViewModel() {
    private val PREFS_NAME = "MyPrefsFile" // Nome do arquivo de preferências
    private val LISTA_VENCEDORES_KEY = "lista_vencedores"
    var listaVencedores: MutableList<VencedorClass> = mutableListOf()
    public lateinit var sistemaDeFala: TextToSpeech
    var som: Boolean = true
    var vibracao: Boolean = true
    var pontosVit = 12
    var partidasParaAnuncio = 6
    var partidaAtualAnuncio = 0

    fun salvarListaVencedores(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        val editor: SharedPreferences.Editor = prefs.edit()

        val gson = Gson()
        val json = gson.toJson(listaVencedores)

        editor.putString(LISTA_VENCEDORES_KEY, json)
        editor.apply()

        Log.e("Testes", "Salvou")
    }

    fun carregarListaVencedores(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        val gson = Gson()
        val json = prefs.getString(LISTA_VENCEDORES_KEY, null)

        val type = object : TypeToken<MutableList<VencedorClass>>() {}.type
        listaVencedores = gson.fromJson(json, type) ?: mutableListOf()
        Log.e("Testes", "Carregou")
    }
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
