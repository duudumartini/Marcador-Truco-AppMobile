package com.example.marcatruco.ui.pontos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PontosViewModel : ViewModel() {
    public var pontosNos = 0
    public var pontosEles = 0
    public var vitoriaNos = 0
    public var vitoriaEles = 0

    private val _txtPontosNos = MutableLiveData<Int>()
    private val _txtPontosEles = MutableLiveData<Int>()
    private val _txtVitNos = MutableLiveData<String>()
    private val _txtVitEles = MutableLiveData<String>()

    val txtPontosNos: LiveData<Int> = _txtPontosNos
    val txtPontosEles: LiveData<Int> = _txtPontosEles
    val txtVitEles: LiveData<String> = _txtVitEles
    val txtVitNos: LiveData<String> = _txtVitNos

    fun iniciaParametros(){
        _txtPontosNos.value = 0
        _txtPontosEles.value = 0
        _txtVitNos.value =  vitoriaNos.toString() + " Vitórias"
        _txtVitEles.value = vitoriaEles.toString() + " Vitórias"
    }

    fun somaPontos(){

    }
}