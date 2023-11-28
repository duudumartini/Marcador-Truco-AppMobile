package com.example.marcatruco.ui.historico

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.ui.classes.VencedorClass
import com.example.marcatruco.ui.pontos.PontosViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoricoViewModel : ViewModel() {
    private val _listaVencedoresHistorico = mutableListOf<VencedorClass>()
    fun adicionarVencedorHistorico(pontosNos: Int, pontosEles: Int) {
        val novoVencedor = VencedorClass().apply {
            nos = pontosNos
            eles = pontosEles
            if(pontosNos > pontosEles){
                vencedor = "Nós"
            }
            else{
                vencedor = "Eles"
            }

            hora = obterHoraAtual()
        }
        _listaVencedoresHistorico.add(novoVencedor)
        Log.d("MeuErro", _listaVencedoresHistorico.size.toString())
        Log.d("MeuErro", _listaVencedoresHistorico[(_listaVencedoresHistorico.size)-1].hora)
    }

    private fun obterHoraAtual(): String {
        val formato = DateTimeFormatter.ofPattern("HH:mm:ss")
        return LocalDateTime.now().format(formato)
    }
}
