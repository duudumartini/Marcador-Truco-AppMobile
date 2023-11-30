package com.example.marcatruco.ui.historico

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.databinding.FragmentHistoricoBinding
import com.example.marcatruco.ui.classes.VencedorClass


class HistoricoFragment : Fragment() {
    private lateinit var tabela: TableRow
    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historicoViewModel = ViewModelProvider(this).get(HistoricoViewModel::class.java)

        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tabela = binding.tabelaHistorico

        historicoViewModel.listaVencedores.observe(viewLifecycleOwner, Observer { listaVencedores ->
            atualizarTabela(listaVencedores)
        })




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun atualizarTabela(listaVencedores: List<VencedorClass>) {
       // tabela.removeAllViews()
        Log.e("MeuErro Fragment",listaVencedores.size.toString())
        for (novoVencedor in listaVencedores) {
            Log.e("MeuErro","Vencedor")
            val nos = TextView(requireContext())
            val eles = TextView(requireContext())
            val _vencedor = TextView(requireContext())
            val hora = TextView(requireContext())

            nos.text = novoVencedor.nos.toString()
            nos.layoutParams = TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
            nos.gravity = Gravity.CENTER
            nos.setPadding(8, 8, 8, 8)

            eles.text = novoVencedor.eles.toString()
            eles.layoutParams = TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
            eles.gravity = Gravity.CENTER
            eles.setPadding(8, 8, 8, 8)

            _vencedor.text = novoVencedor.vencedor.toString()
            _vencedor.layoutParams = TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
            _vencedor.gravity = Gravity.CENTER
            _vencedor.setPadding(8, 8, 8, 8)

            hora.text = novoVencedor.hora.toString()
            hora.layoutParams = TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
            hora.gravity = Gravity.CENTER
            hora.setPadding(8, 8, 8, 8)

            tabela.addView(nos)
            tabela.addView(eles)
            tabela.addView(_vencedor)
            tabela.addView(hora)
        }
    }
}
