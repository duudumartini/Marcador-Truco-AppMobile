package com.example.marcatruco.ui.historico

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.marcatruco.ui.classes.VencedorClass


class HistoricoViewModel : ViewModel() {
    var listaVencedores: MutableList<VencedorClass> = mutableListOf()
}

