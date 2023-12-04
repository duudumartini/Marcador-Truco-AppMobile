package com.example.marcatruco.ui.pontos

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marcatruco.ui.classes.SharedViewModel
import com.example.marcatruco.ui.classes.VencedorClass
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PontosViewModel : ViewModel() {
    private lateinit var sharedViewModel: SharedViewModel
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
    var pontosVit = 12

    private val _listaVencedoresLiveData = MutableLiveData<MutableList<VencedorClass>>()
    val listaVencedoresLiveData: LiveData<MutableList<VencedorClass>> = _listaVencedoresLiveData
    var listaVencedores: MutableList<VencedorClass> = mutableListOf()

    lateinit var _fragment: Fragment

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
        //sharedViewModel.pequenaVibracao(context)
        if (time == "nos") {
            if (resultadoNos >= pontosVit) {
                vitoriaNos++
                timeNosVenceu = true
                adicionaVencedorALista(pontosVit, pontosEles)
                zeraPontos()
            } else if (resultadoNos < pontosVit) {
                pontosNos = resultadoNos
            }
        } else if (time == "eles") {
            if (resultadoEles >= pontosVit) {
                vitoriaEles++
                adicionaVencedorALista(pontosNos, pontosVit)
                timeElesVenceu = true
                zeraPontos()
            } else if (resultadoNos < pontosVit) {
                pontosEles = resultadoEles
            }
        }
    }

    fun subPontos(time: String, qtdPontos: Int, context: Context) {
        //sharedViewModel.pequenaVibracao(context)
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
        listaVencedores.clear()
    }



    fun obtemFragment(fragment: Fragment) {
        _fragment = fragment
    }


    fun adicionaVencedorALista(pontosNos: Int, pontosEles: Int) {
        val novoVencedor = VencedorClass().apply {
            nos = pontosNos
            eles = pontosEles
            vencedor = if (pontosNos > pontosEles) "Nós" else "Eles"
            hora = obterHoraAtual()
        }
        listaVencedores.add(novoVencedor)
        _listaVencedoresLiveData.value = listaVencedores.toMutableList()
    }


    fun obterHoraAtual(): String {
        val formato = DateTimeFormatter.ofPattern("HH:mm:ss")
        return LocalDateTime.now().format(formato)
    }
}
