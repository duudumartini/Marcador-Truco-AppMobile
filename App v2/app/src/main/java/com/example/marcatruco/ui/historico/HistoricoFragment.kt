package com.example.marcatruco.ui.historico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.databinding.FragmentHistoricoBinding
import com.example.marcatruco.ui.classes.VencedorClass
import com.example.marcatruco.ui.pontos.PontosViewModel


class HistoricoFragment : Fragment() {

    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pontosViewModel = ViewModelProvider(this).get(PontosViewModel::class.java)
        val historicoViewModel = ViewModelProvider(this).get(HistoricoViewModel::class.java)

        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        val root: View = binding.root


/*


        historicoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
*/      val vencedor1 = VencedorClass().apply {
            nos = 10
            eles = 12
            vencedor = "Eles"
            hora = "10:00 AM"
        }
        //historicoViewModel.adicionarVencedorHistorico(vencedor1)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
