package com.example.marcatruco.ui.historico

import android.graphics.Color
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
import androidx.core.content.ContextCompat
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
        verificaHistoricoLimpo()
        //tabela.removeAllViews()
        val listaInvertida = listaVencedores.reversed()

        for (i in listaInvertida.indices) {
            val novoVencedor = listaInvertida[i]

            val corFundo = if (i % 2 == 0) R.color.bktabela1 else R.color.bktabela2
            val corTexto = if (i % 2 == 0) R.color.txttabela1 else R.color.txttabela2

            val nos = criarTextView(novoVencedor.nos.toString(), corFundo, corTexto)
            val eles = criarTextView(novoVencedor.eles.toString(), corFundo, corTexto)
            val _vencedor = criarTextView(novoVencedor.vencedor.toString(), corFundo, corTexto)
            val hora = criarTextView(novoVencedor.hora.toString(), corFundo, corTexto)

            val row = TableRow(requireContext())
            row.setBackgroundResource(corFundo)
            row.addView(nos)
            row.addView(eles)
            row.addView(_vencedor)
            row.addView(hora)
            tabela.addView(row)
        }
    }

    private fun criarTextView(texto: String, corFundo: Int, corTexto: Int): TextView {
        val textView = TextView(requireContext())
        textView.text = texto
        textView.setTextColor(ContextCompat.getColor(requireContext(), corTexto))
        textView.textSize = 18f
        textView.typeface = resources.getFont(R.font.anton_regular)
        textView.gravity = Gravity.CENTER
        textView.layoutParams = TableRow.LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
            1f
        )
        textView.gravity = Gravity.CENTER
        textView.setPadding(8, 8, 8, 8)
        return textView
    }


    fun limpaHistorico(){
        tabela.removeAllViews()
        sharedViewModel.listaVencedores.clear()
        atualizarTabela(sharedViewModel.listaVencedores)
    }

    fun verificaHistoricoLimpo(){
        if (sharedViewModel.listaVencedores.size == 0) {
            val textView = TextView(requireContext()).apply {
                id = R.id.tabela_nos_historico
                text = "Não há histórico de partidas"
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                textSize = 18f
                typeface = resources.getFont(R.font.anton_regular)
                gravity = Gravity.CENTER
                layoutParams = TableRow.LayoutParams(
                    0, 150
                )
                setPadding(8, 8, 8, 8)
                setBackgroundColor(Color.parseColor("#400000")) // Define a cor de fundo
            }

            // Adicione o TextView a algum layout, por exemplo, um TableLayout chamado tabela
            tabela.addView(textView)
        }


    }
    override fun onPause() {
        super.onPause()
        sharedViewModel.salvarListaVencedores(requireContext())
    }
}
