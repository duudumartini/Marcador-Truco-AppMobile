package com.example.marcatruco.ui.pontos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.databinding.FragmentPontosBinding
import org.w3c.dom.Text

class PontosFragment : Fragment() {
    private lateinit var txtPontosNos: TextView
    private lateinit var txtPontosEles: TextView
    private lateinit var txtVitNos: TextView
    private lateinit var txtVitEles: TextView
    private lateinit var btnSomaNos: ImageView
    private lateinit var btnSubNos: ImageView

    private lateinit var pontosViewModel: PontosViewModel

    private var _binding: FragmentPontosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pontosViewModel = ViewModelProvider(this).get(PontosViewModel::class.java)
        _binding = FragmentPontosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtNos: TextView = binding.txtNos
        txtPontosNos = binding.txtPontosNos
        txtPontosEles = binding.txtPontosEles
        txtVitNos = binding.txtVitNos
        txtVitEles = binding.txtVitEles
        btnSomaNos = binding.btnSomaNos
        btnSubNos = binding.btnSubNos

        pontosViewModel.txtPontosNos.observe(viewLifecycleOwner) {
            txtPontosNos.text = it.toString()
        }

        pontosViewModel.txtPontosEles.observe(viewLifecycleOwner) {
            txtPontosEles.text = it.toString()
        }

        pontosViewModel.txtVitNos.observe(viewLifecycleOwner) {
            txtVitNos.text = it
        }

        pontosViewModel.txtVitEles.observe(viewLifecycleOwner) {
            txtVitEles.text = it
        }


        pontosViewModel.iniciaParametros()
        ButtonListeners ()
        return root
    }
     fun ButtonListeners (){
         btnSomaNos.setOnClickListener{
             pontosViewModel.somaPontos()
         }
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}