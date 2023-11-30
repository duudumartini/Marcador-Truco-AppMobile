package com.example.marcatruco.ui.pontos

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.marcatruco.R
import com.example.marcatruco.databinding.FragmentPontosBinding

class PontosFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var txtNos: TextView
    private lateinit var txtEles: TextView
    private lateinit var txtPontosNos: TextView
    private lateinit var txtPontosEles: TextView
    private lateinit var txtVitNos: TextView
    private lateinit var txtVitEles: TextView
    private lateinit var btnSomaNos: ImageView
    private lateinit var btnSubNos: ImageView
    private lateinit var btnSomaEles: ImageView
    private lateinit var btnSubEles: ImageView
    private lateinit var btnTruco: ImageView
    private lateinit var btnZerar: Button

    private lateinit var pontosViewModel: PontosViewModel
    private lateinit var sistemaDeFala: TextToSpeech

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

        sistemaDeFala = TextToSpeech(requireContext(), this)
        txtNos = binding.txtNos
        txtEles = binding.txtEles
        txtPontosNos = binding.txtPontosNos
        txtPontosEles = binding.txtPontosEles
        txtVitNos = binding.txtVitNos
        txtVitEles = binding.txtVitEles
        btnSomaNos = binding.btnSomaNos
        btnSubNos = binding.btnSubNos
        btnSomaEles = binding.btnSomaEles
        btnSubEles = binding.btnSubEles
        btnTruco = binding.btnTruco
        btnZerar = binding.btnZerar

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
        ButtonListeners()
        AtualizaCorVitoria()
        FalaBoasVindas()
        pontosViewModel.obtemFragment(this)
        return root
    }

    fun FalaBoasVindas(){
        if(pontosViewModel.appIniciou == false){
            Handler().postDelayed({
                Fala("Bem vindo ao MARCA TRUCO !")
            }, 300)
            pontosViewModel.appIniciou = true;
        }
    }
    fun ButtonListeners() {
        txtNos.setOnClickListener {
            edita_nome_time(txtNos)
        }
        txtEles.setOnClickListener {
            edita_nome_time(txtEles)
        }
        btnSomaNos.setOnClickListener {
            pontosViewModel.somaPontos("nos", 1, requireContext())
            Fala("A equipe ${txtNos.text} marcou um ponto")
            Atualizacoes()
        }
        btnSomaEles.setOnClickListener {
            pontosViewModel.somaPontos("eles", 1, requireContext())
            Fala("A equipe ${txtEles.text} marcou um ponto")
            Atualizacoes()
        }
        btnSubNos.setOnClickListener {
            pontosViewModel.subPontos("nos", 1, requireContext())
            Fala("A equipe ${txtNos.text} perdeu um ponto")
            Atualizacoes()
        }
        btnSubEles.setOnClickListener {
            pontosViewModel.subPontos("eles", 1, requireContext())
            Fala("A equipe ${txtEles.text} perdeu um ponto")
            Atualizacoes()
        }
        btnZerar.setOnClickListener {
            zerar()
        }
        btnTruco.setOnClickListener {
            truco()
        }

    }
    fun Atualizacoes(){
        AtualizaCorVitoria()
        AtualizaTextoVitoria()
        AtualizaPontosTime()
        VerificaGanhador()
    }
    fun Fala(fala: String) {
        if (pontosViewModel.som == true) {
            sistemaDeFala?.speak(fala, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
    fun VerificaGanhador(){
        if(pontosViewModel.timeNosVenceu == true){
            vitoria(txtNos)
        }
        else if(pontosViewModel.timeElesVenceu == true){
            vitoria(txtEles)
        }
    }
    fun AtualizaPontosTime() {
        txtPontosNos.text = pontosViewModel.pontosNos.toString()
        txtPontosEles.text = pontosViewModel.pontosEles.toString()
        txtPontosNos.text = pontosViewModel.pontosNos.toString()
        txtPontosEles.text = pontosViewModel.pontosEles.toString()
    }
    fun AtualizaTextoVitoria() {
        txtVitNos.text = "${pontosViewModel.vitoriaNos} Vitórias"
        txtVitEles.text = "${pontosViewModel.vitoriaEles} Vitórias"
    }
    fun AtualizaCorVitoria() {
        if (pontosViewModel.vitoriaNos == pontosViewModel.vitoriaEles) {
            txtVitNos.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            txtVitEles.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else if (pontosViewModel.vitoriaNos > pontosViewModel.vitoriaEles) {
            txtVitNos.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            txtVitEles.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else if (pontosViewModel.vitoriaNos < pontosViewModel.vitoriaEles) {
            txtVitNos.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            txtVitEles.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        }
    }
    fun truco() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.truco, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        dialog.show()

        val tresButtonNos = view.findViewById<ImageView>(R.id.btn_truco_tres_nos)
        tresButtonNos.setOnClickListener {
            pontosViewModel.somaPontos("nos", 3, requireContext())
            Fala("A equipe ${txtNos.text} marcou três pontos")
            Atualizacoes()
            dialog.dismiss()
        }
        val seisButtonNos = view.findViewById<ImageView>(R.id.btn_truco_seis_nos)
        seisButtonNos.setOnClickListener {
            pontosViewModel.somaPontos("nos", 6, requireContext())
            Fala("A equipe ${txtNos.text} marcou seis pontos")
            Atualizacoes()
            dialog.dismiss()
        }
        val noveButtonNos = view.findViewById<ImageView>(R.id.btn_truco_nove_nos)
        noveButtonNos.setOnClickListener {
            pontosViewModel.somaPontos("nos", 9, requireContext())
            Fala("A equipe ${txtNos.text} marcou nove pontos")
            Atualizacoes()
            dialog.dismiss()
        }
        val dozeButtonNos = view.findViewById<ImageView>(R.id.btn_truco_doze_nos)
        dozeButtonNos.setOnClickListener {
            pontosViewModel.somaPontos("nos", 12, requireContext())
            Fala("A equipe ${txtNos.text} marcou doze pontos")
            Atualizacoes()
            dialog.dismiss()
        }

        val tresButtonEles = view.findViewById<ImageView>(R.id.btn_truco_tres_eles)
        tresButtonEles.setOnClickListener {
            pontosViewModel.somaPontos("eles", 3, requireContext())
            Fala("A equipe ${txtEles.text} marcou três pontos")
            Atualizacoes()
            dialog.dismiss()
        }
        val seisButtonEles = view.findViewById<ImageView>(R.id.btn_truco_seis_eles)
        seisButtonEles.setOnClickListener {
            pontosViewModel.somaPontos("eles", 6, requireContext())
            Fala("A equipe ${txtEles.text} marcou seis pontos")
            Atualizacoes()
            dialog.dismiss()
        }
        val noveButtonEles = view.findViewById<ImageView>(R.id.btn_truco_nove_eles)
        noveButtonEles.setOnClickListener {
            pontosViewModel.somaPontos("eles", 9, requireContext())
            Fala("A equipe ${txtEles.text} marcou nove pontos")
            Atualizacoes()
            dialog.dismiss()
        }
        val dozeButtonEles = view.findViewById<ImageView>(R.id.btn_truco_doze_eles)
        dozeButtonEles.setOnClickListener {
            pontosViewModel.somaPontos("eles", 12, requireContext())
            Fala("A equipe ${txtEles.text} marcou 12 pontos")
            Atualizacoes()
            dialog.dismiss()
        }
    }
    fun zerar() {
        pontosViewModel.pequenaVibracao(requireContext())
        Fala("Deseja ZERAR as vitórias?")
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.zerar_pontos, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        if (pontosViewModel.vitoriaEles > 0 || pontosViewModel.vitoriaNos > 0) {

            dialog.show()

            val cancelar = view.findViewById<Button>(R.id.btn_cancelarzerarvitorias)
            cancelar.setOnClickListener {
                dialog.dismiss()
            }

            val zerarVitorias = view.findViewById<Button>(R.id.btn_zerarvitorias)
            zerarVitorias.setOnClickListener {

                pontosViewModel.vitoriaEles = 0
                pontosViewModel.vitoriaNos = 0
                Fala("Vitórias ZERADAS !")
                AtualizaTextoVitoria()
                AtualizaCorVitoria()
                pontosViewModel.zeraPontos()
                dialog.dismiss()
            }
        } else {
            Fala("Não há vitórias para serem ZERADAS !")
        }
    }
    fun edita_nome_time(time: TextView){
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.nome_time, null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        dialog.show()

        val edTxt_EditaTime = view.findViewById<EditText>(R.id.edtxt_edita_time)

        edTxt_EditaTime.setText(time.text)

        val corAtual = time.currentTextColor
        edTxt_EditaTime.setTextColor(corAtual)

        val tamanhoMaximo = 20
        edTxt_EditaTime.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(tamanhoMaximo))

        val corVermelho = view.findViewById<Button>(R.id.cor_vermelho_edita_time)
        val corCinza = view.findViewById<Button>(R.id.cor_roxo_edita_time)
        val corLaranja = view.findViewById<Button>(R.id.cor_laranja_edita_time)
        val corRosa = view.findViewById<Button>(R.id.cor_rosa_edita_time)
        val corVerde= view.findViewById<Button>(R.id.cor_verde_edita_time)
        val corAmarelo = view.findViewById<Button>(R.id.cor_amarelo_edita_time)
        val corAzul = view.findViewById<Button>(R.id.cor_azul_edita_time)

        corVermelho.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
        corCinza.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
        }
        corLaranja.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
        }
        corRosa.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
        }
        corVerde.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        corAmarelo.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        }
        corAzul.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }

        val cancelar = view.findViewById<Button>(R.id.btn_cancelar_editaTime)
        cancelar.setOnClickListener { dialog.dismiss() }

        val confirma = view.findViewById<Button>(R.id.btn_confirmar_editaTime)
        confirma.setOnClickListener {
            val novoNome = edTxt_EditaTime.text.toString()
            if(time.id == R.id.txt_nos) {
                txtNos.text = novoNome
                txtNos.setTextColor(edTxt_EditaTime.currentTextColor)
            }
            else if(time.id == R.id.txt_eles) {
                txtEles.text = novoNome
                txtEles.setTextColor(edTxt_EditaTime.currentTextColor)
            }
            dialog.dismiss()
        }
    }
    fun vitoria(timeVencedor: TextView) {
        pontosViewModel.grandeVibracao(requireContext())
        Fala("A equipe ${timeVencedor.text} ganhou essa partida !")
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.vitoria, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        dialog.show()
        var nomeTime = view.findViewById<TextView>(R.id.txt_vencedor)
        nomeTime.setText(timeVencedor.text)
        val JogarNovamente = view.findViewById<Button>(R.id.btn_jogar_novamente)
        JogarNovamente.setOnClickListener {
            pontosViewModel.timeElesVenceu = false
            pontosViewModel.timeNosVenceu = false
            dialog.dismiss()
        }
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Initialization successful
        } else {
            // Initialization failed
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        sistemaDeFala.shutdown()
        _binding = null
    }
}