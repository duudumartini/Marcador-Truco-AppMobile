package com.example.marcatruco.ui.historico

import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.ui.classes.VencedorClass
import com.example.marcatruco.ui.pontos.PontosViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoricoViewModel : ViewModel() {
    public var _listaVencedoresHistorico = mutableListOf<VencedorClass>()


    private val _listaVencedores = MutableLiveData<List<VencedorClass>>()

    val listaVencedores: LiveData<List<VencedorClass>>
        get() = _listaVencedores


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
        _listaVencedores.value = _listaVencedoresHistorico.toList()
       // _listaVencedores.value = updatedList.toList()


        Log.e("MeuErro ViewModel",_listaVencedoresHistorico.size.toString())
    }

    private fun obterHoraAtual(): String {
        val formato = DateTimeFormatter.ofPattern("HH:mm:ss")
        return LocalDateTime.now().format(formato)
    }
}
