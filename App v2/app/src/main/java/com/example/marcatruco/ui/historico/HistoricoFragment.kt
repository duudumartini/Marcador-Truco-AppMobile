package com.example.marcatruco.ui.historico

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.marcatruco.R
import com.example.marcatruco.databinding.FragmentHistoricoBinding
import com.example.marcatruco.ui.classes.SharedViewModel
import com.example.marcatruco.ui.classes.VencedorClass
import com.example.marcatruco.ui.pontos.PontosFragment
import com.example.marcatruco.ui.pontos.PontosViewModel


class HistoricoFragment : Fragment() {
    private lateinit var tabela: TableLayout
    private lateinit var btnLimpaHistorico: Button
    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!
    private val historicoViewModel: HistoricoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tabela = binding.tabelaHistorico
        btnLimpaHistorico = binding.btnLimpaHistorico
        atualizacoes()
        ButtonListeners()
        return root
    }

    override fun onDestroyView() {
        sharedViewModel.sistemaDeFala.shutdown()
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        atualizarTabela(sharedViewModel.listaVencedores)
        super.onStop()
    }
    private fun atualizacoes(){
        atualizarTabela(sharedViewModel.listaVencedores)
    }

    private fun ButtonListeners(){
        btnLimpaHistorico.setOnClickListener {
            limpaHistorico()
            sharedViewModel.grandeVibracao(requireContext())
        }
    }

    private fun atualizarTabela(listaVencedores: List<VencedorClass>) {
        tabela.removeAllViews()

        for (i in listaVencedores.indices) {
            val novoVencedor = listaVencedores[i]

            val corFundo = if (i % 2 == 0) R.color.bktabela1 else R.color.bktabela2

            val nos = criarTextView(novoVencedor.nos.toString(), corFundo)
            val eles = criarTextView(novoVencedor.eles.toString(), corFundo)
            val _vencedor = criarTextView(novoVencedor.vencedor.toString(), corFundo)
            val hora = criarTextView(novoVencedor.hora.toString(), corFundo)

            val row = TableRow(requireContext())
            row.setBackgroundResource(corFundo)
            row.addView(nos)
            row.addView(eles)
            row.addView(_vencedor)
            row.addView(hora)
            tabela.addView(row)
        }
    }

    private fun criarTextView(texto: String, corFundo: Int): TextView {
        val textView = TextView(requireContext())
        textView.text = texto
        textView.textSize = 18f
        textView.typeface = resources.getFont(R.font.anton_regular)
        textView.layoutParams = TableRow.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        )
        textView.gravity = Gravity.CENTER
        textView.setPadding(8, 8, 8, 8)
        return textView
    }

    fun limpaHistorico(){
        sharedViewModel.listaVencedores.clear()
        atualizarTabela(sharedViewModel.listaVencedores
        )
    }
}
