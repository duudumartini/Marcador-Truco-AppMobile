package com.example.marcatruco.ui.configuracoes

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.R
import com.example.marcatruco.databinding.FragmentConfiguracoesBinding
import com.example.marcatruco.ui.classes.SharedViewModel

class ConfiguracoesFragment : Fragment() {
    //private val configuracoesVieModel: ConfiguracoesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var barraPontosVit: SeekBar
    private lateinit var txtPontosVit: TextView
    private lateinit var btnSom: Switch
    private lateinit var btnVibra: Switch
    private lateinit var btnReset: Button
    private lateinit var txtSom: TextView
    private lateinit var txtVibra: TextView
    private lateinit var spinnerVozes: Spinner
    private var _binding: FragmentConfiguracoesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val configuracoesViewModel = ViewModelProvider(this).get(ConfiguracoesViewModel::class.java)
        _binding = FragmentConfiguracoesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        barraPontosVit = binding.seekPontosVit
        txtPontosVit = binding.txtPontosVit
        btnSom = binding.btnSom
        btnVibra = binding.btnVibracao
        btnReset = binding.btnReset
        txtSom = binding.txtSom
        txtVibra = binding.txtVibracao

        atualizacoes()
        buttonListeners()
        return root
    }

    private fun buttonListeners() {
        barraPontosVit.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txtPontosVit.text = "${(progress)} Pontos"
                sharedViewModel.pequenaVibracao(requireContext())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Implementação onStartTrackingTouch se necessário
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    sharedViewModel.pontosVit = it.progress
                    sharedViewModel.fala(requireContext(),"É necessário ${it.progress} pontos para vencer a partida!")
                }
            }
        })

        btnSom.setOnClickListener {
            sharedViewModel.som = btnSom.isChecked
            sharedViewModel.pequenaVibracao(requireContext())
            alteraCorSwitch()
            if(btnSom.isChecked == true){
                sharedViewModel.fala(requireContext(),"Som ativado!")
                txtSom.text = "LIGADO"
            }
            else{
                sharedViewModel.fala(requireContext(),"Som desligado!")
                txtSom.text = "DESLIGADO"
            }
        }
        btnVibra.setOnClickListener {
            sharedViewModel.vibracao = btnVibra.isChecked
            sharedViewModel.pequenaVibracao(requireContext())
            alteraCorSwitch()
            if(btnVibra.isChecked == true){
                sharedViewModel.fala(requireContext(),"Modo de vibração ativado!")
                txtVibra.text = "LIGADO"
            }
            else{
                sharedViewModel.fala(requireContext(),"Modo de vibração desligado!")
                txtVibra.text = "DESLIGADO"
            }
        }

        btnReset.setOnClickListener {
            sharedViewModel.fala(requireContext(),"Configurações restauradas !")
            sharedViewModel.grandeVibracao(requireContext())
            sharedViewModel.som = true
            sharedViewModel.vibracao = true
            barraPontosVit.progress = 12
            btnVibra.isChecked = true
            btnSom.isChecked = true
        }
    }

    private fun atualizacoes() {
        txtPontosVit.text = "${sharedViewModel.pontosVit} Pontos"
        btnSom.isChecked = sharedViewModel.som
        btnVibra.isChecked = sharedViewModel.vibracao
        alteraCorSwitch()
    }

    private fun alteraCorSwitch() {
        if (btnVibra.isChecked) {
            // Change the thumb (circle) color when the Switch is checked
            btnVibra.thumbTintList = ColorStateList.valueOf(Color.RED)

            // Change the track (background) color when the Switch is checked
            btnVibra.trackTintList = ColorStateList.valueOf(Color.RED)
        } else {
            // Reset the colors when the Switch is not checked (optional)
            val defaultColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.cinza1))
            val defaultColor1 = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.cinza2))
            btnVibra.thumbTintList = defaultColor
            btnVibra.trackTintList = defaultColor1
        }
        if (btnSom.isChecked) {
            // Change the thumb (circle) color when the Switch is checked
            btnSom.thumbTintList = ColorStateList.valueOf(Color.RED)

            // Change the track (background) color when the Switch is checked
            btnSom.trackTintList = ColorStateList.valueOf(Color.RED)
        } else {
            // Reset the colors when the Switch is not checked (optional)
            val defaultColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.cinza1))
            val defaultColor1 = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.cinza2))
            btnSom.thumbTintList = defaultColor
            btnSom.trackTintList = defaultColor1
        }
    }

    override fun onDestroyView() {
        sharedViewModel.sistemaDeFala.shutdown()
        atualizacoes()
        super.onDestroyView()
        _binding = null
    }


}