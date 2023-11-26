package com.example.marcatruco.ui.pontos

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PontosViewModel : ViewModel() {
    var appIniciou = false
    var vibracaoDuracao: Long = 200
    var pontosNos = 0
    var pontosEles = 0
    var vitoriaNos = 0
    var vitoriaEles = 0
    var som = true
    var vibracao = true
    var timeNosVenceu = false
    var timeElesVenceu = false

    private val _pontosNos = MutableLiveData<Int>()
    private val _pontosEles = MutableLiveData<Int>()
    private val _txtVitNos = MutableLiveData<String>()
    private val _txtVitEles = MutableLiveData<String>()

    val txtVitEles: LiveData<String> = _txtVitEles
    val txtVitNos: LiveData<String> = _txtVitNos
    val txtPontosNos: LiveData<Int> = _pontosNos
    val txtPontosEles: LiveData<Int> = _pontosEles

    fun iniciaParametros() {
        _pontosEles.value = pontosEles
        _pontosNos.value = pontosNos
        _txtVitNos.value = "$vitoriaNos Vitórias"
        _txtVitEles.value = "$vitoriaEles Vitórias"
    }

    fun somaPontos(time: String, qtdPontos: Int, context: Context) {
        val resultadoNos = pontosNos + qtdPontos
        val resultadoEles = pontosEles + qtdPontos
        pequenaVibracao(context)
        if(time == "nos"){
            if(resultadoNos >= 12){
                vitoriaNos++
                timeNosVenceu = true
                zeraPontos()
            }
            else if(resultadoNos < 12)
            {
                pontosNos = resultadoNos
            }
        }
        else if(time == "eles"){
            if(resultadoEles >= 12){
                vitoriaEles++
                timeElesVenceu = true
                zeraPontos()
            }
            else if(resultadoNos < 12)
            {
                pontosEles = resultadoEles
            }
        }
    }
    fun subPontos(time: String, qtdPontos: Int, context: Context) {
        pequenaVibracao(context)
        if (time == "nos") {
            val resultado = pontosNos - qtdPontos
            if (resultado >= 0) {
                pontosNos = resultado
            }
        } else if (time == "eles") {
            val resultado = pontosEles - qtdPontos
            if (resultado >= 0) {
                pontosEles = resultado
            }
        }
    }

    fun zeraPontos() {
        pontosNos = 0
        pontosEles = 0
        _pontosEles.value = pontosEles
        _pontosNos.value = pontosNos
    }

    fun pequenaVibracao(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (vibracao == true) {
            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // para API 26 e superior
                    it.vibrate(
                        VibrationEffect.createOneShot(
                            vibracaoDuracao,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    // para API inferior a 26
                    it.vibrate(vibracaoDuracao)
                }
            }
        }

    }

    fun grandeVibracao(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (vibracao == true) {
            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // para API 26 e superior
                    it.vibrate(
                        VibrationEffect.createOneShot(
                            1000,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    // para API inferior a 26
                    it.vibrate(2000)
                }
            }
        }

    }

}