package com.example.marcatruco

import android.content.Context
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.marcatruco.databinding.ActivityMainBinding
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.text.InputFilter
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var sistemaDeFala: TextToSpeech

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var txtVitNos: TextView
    private lateinit var txtVitEles: TextView

    private lateinit var txtPontosNos: TextView
    private lateinit var txtPontosEles: TextView

    private lateinit var txtNos: TextView
    private lateinit var txtEles: TextView

    var vitoriasNos = 0
    var vitoriasEles = 0

    var som = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pontos, R.id.nav_cartas, R.id.nav_config
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        sistemaDeFala = TextToSpeech(this, this)

        txtNos = findViewById<TextView>(R.id.txt_nos)
        txtEles = findViewById<TextView>(R.id.txt_eles)

        val btnSomaNos = findViewById<ImageView>(R.id.btn_soma_nos)
        val btnSomaEles = findViewById<ImageView>(R.id.btn_soma_eles)
        val btnSubNos = findViewById<ImageView>(R.id.btn_sub_nos)
        val btnSubEles = findViewById<ImageView>(R.id.btn_sub_eles)

        txtPontosNos = findViewById<TextView>(R.id.txt_pontos_nos)
        txtPontosEles = findViewById<TextView>(R.id.txt_pontos_eles)

        val btnTruco = findViewById<ImageView>(R.id.btn_truco)

        txtVitEles = findViewById<TextView>(R.id.txt_vit_eles)
        txtVitNos = findViewById<TextView>(R.id.txt_vit_nos)

        val btnZerar = findViewById<Button>(R.id.btn_zerar)

        txtNos.setOnClickListener {
            MostraEditaTexto(txtNos)
        }

        txtEles.setOnClickListener {
            MostraEditaTexto(txtEles)
        }

        btnSomaNos.setOnClickListener {
            SomaPonto(txtPontosNos, 1)
        }

        btnSomaEles.setOnClickListener {
            SomaPonto(txtPontosEles, 1)
        }

        btnSubNos.setOnClickListener {
            SubPonto(txtPontosNos, 1)
        }

        btnSubEles.setOnClickListener {
            SubPonto(txtPontosEles, 1)
        }

        btnZerar.setOnClickListener {
            ConfimaZeraVitorias()
        }

        btnTruco.setOnClickListener {
            truco()
        }
        ZeraVitorias(txtVitNos, txtVitEles)
        ZeraPontuacao()

        Handler().postDelayed({
            Fala("Bem vindo ao Marca Truco!")
        }, 300)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        sistemaDeFala?.stop()
        sistemaDeFala?.shutdown()
    }

    fun MostraEditaTexto(nomeDoTime: TextView) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.edita_times, null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        dialog.show()

        val edTxt_EditaTime = view.findViewById<EditText>(R.id.edtxt_edita_time)
        edTxt_EditaTime.setText(nomeDoTime.text)

        val corAtual = nomeDoTime.currentTextColor
        edTxt_EditaTime.setTextColor(corAtual)

        val tamanhoMaximo = 20
        edTxt_EditaTime.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(tamanhoMaximo))

        val corVermelho = view.findViewById<Button>(R.id.cor_vermelho_edita_time)
        val corCinza = view.findViewById<Button>(R.id.cor_cinza_edita_time)
        val corLaranja = view.findViewById<Button>(R.id.cor_laranja_edita_time)
        val corRosa = view.findViewById<Button>(R.id.cor_rosa_edita_time)
        val corVerde= view.findViewById<Button>(R.id.cor_verde_edita_time)
        val corAmarelo = view.findViewById<Button>(R.id.cor_amarelo_edita_time)
        val corAzul = view.findViewById<Button>(R.id.cor_azul_edita_time)

        corVermelho.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        corCinza.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.Cinza))
        }
        corLaranja.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.Laranja))
        }
        corRosa.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.Rosa))
        }
        corVerde.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.Verde))
        }
        corAmarelo.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.Amarelo))
        }
        corAzul.setOnClickListener {
            edTxt_EditaTime.setTextColor(ContextCompat.getColor(this, R.color.Azul))
        }

        val cancelar = view.findViewById<Button>(R.id.btn_cancelar_editaTime)
        cancelar.setOnClickListener { dialog.dismiss() }

        val confirma = view.findViewById<Button>(R.id.btn_confirmar_editaTime)
        confirma.setOnClickListener {
            val novoNome = edTxt_EditaTime.text.toString()
            if(nomeDoTime.id == R.id.txt_nos) {
                txtNos.text = novoNome
                txtNos.setTextColor(edTxt_EditaTime.currentTextColor)
            }
            else if(nomeDoTime.id == R.id.txt_eles) {
                txtEles.text = novoNome
                txtEles.setTextColor(edTxt_EditaTime.currentTextColor)
            }
            dialog.dismiss()
        }


    }




    fun ZeraPontuacao() {
        txtPontosNos.text = "0";
        txtPontosEles.text = "0";
    }

    fun ZeraVitorias(vitNos: TextView, vitEles: TextView) {
        vitNos.text = "0 Vitórias"
        vitEles.text = "0 Vitórias"
        vitoriasNos = 0
        vitoriasEles = 0
        AlteraCorVitoria()
    }

    fun SomaPonto(pontos: TextView, QtdPontos: Int) {
        val pontosAtuais = pontos.text.toString().toIntOrNull() ?: 0
        val novosPontos = pontosAtuais + QtdPontos
        if (novosPontos < 12) {
            pontos.text = novosPontos.toString()
        } else {
            if (pontos.id == R.id.txt_pontos_nos) {
                vitoriasNos++
                txtVitNos.text = vitoriasNos.toString() + " Vitórias"
                ZeraPontuacao()
                AlteraCorVitoria()
                MostraVitoria(txtNos)
                Fala("A equipe" + txtNos.text + "ganhou essa partida !")
            }
            if (pontos.id == R.id.txt_pontos_eles) {
                vitoriasEles++
                txtVitEles.text = vitoriasEles.toString() + " Vitórias"
                ZeraPontuacao()
                AlteraCorVitoria()
                MostraVitoria(txtEles)
                Fala("A equipe" + txtEles.text + "ganhou essa partida !")
            }
        }
        if (pontos.id == R.id.txt_pontos_nos && novosPontos < 12) {
            var equipe = txtNos.text
            if(QtdPontos == 1){
                Fala("A equipe" + equipe + "ganhou um ponto")
            }
            else if(QtdPontos == 3){
                Fala("A equipe" + equipe + "ganhou três pontos")
            }
            else if(QtdPontos == 6){
                Fala("A equipe" + equipe + "ganhou seis pontos")
            }
            else if(QtdPontos == 9){
                Fala("A equipe" + equipe + "ganhou nove pontos")
            }
        }
        if (pontos.id == R.id.txt_pontos_eles && novosPontos < 12) {
            var equipe = txtEles.text
            if(QtdPontos == 1){
                Fala("A equipe" + equipe + "ganhou um ponto")
            }
            else if(QtdPontos == 3){
                Fala("A equipe" + equipe + "ganhou três pontos")
            }
            else if(QtdPontos == 6){
                Fala("A equipe" + equipe + "ganhou seis pontos")
            }
            else if(QtdPontos == 9){
                Fala("A equipe" + equipe + "ganhou nove pontos")
            }
        }
    }

    fun SubPonto(pontos: TextView, QtdPontos: Int) {
        val pontosAtuais = pontos.text.toString().toIntOrNull() ?: 0
        if (pontosAtuais > 0) {
            val novosPontos = pontosAtuais - QtdPontos
            pontos.text = novosPontos.toString()
            if (pontos.id == R.id.txt_pontos_nos) {
                var equipe = txtNos.text
                Fala("A equipe" + equipe + "perdeu um ponto !")
            }
            else if (pontos.id == R.id.txt_pontos_eles) {
                var equipe = txtEles.text
                Fala("A equipe" + equipe + "perdeu um ponto !")
            }
        }
    }

    fun ConfimaZeraVitorias() {
        Fala("Deseja ZERAR as vitórias?")
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.zerar_pontos, null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        dialog.show()

        val cancelar = view.findViewById<Button>(R.id.btn_cancelarzerarvitorias)
        cancelar.setOnClickListener {
            dialog.dismiss()
        }

        val zerarVitorias = view.findViewById<Button>(R.id.btn_zerarvitorias)
        zerarVitorias.setOnClickListener {
            ZeraVitorias(txtVitNos, txtVitEles)
            Fala("Vitórias ZERADAS")
            dialog.dismiss()
        }
    }

    fun AlteraCorVitoria() {
        val CorVitoria = ContextCompat.getColor(this, R.color.CorVitoria)
        val CorBranca = ContextCompat.getColor(this, R.color.white)
        if (vitoriasNos === vitoriasEles) {
            txtVitNos.setTextColor(CorBranca)
            txtVitEles.setTextColor(CorBranca)
        } else if (vitoriasNos > vitoriasEles) {
            txtVitNos.setTextColor(CorVitoria)
            txtVitEles.setTextColor(CorBranca)
        } else {
            txtVitEles.setTextColor(CorVitoria)
            txtVitNos.setTextColor(CorBranca)
        }
    }

    fun truco(){
        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.truco_seis_nove_doze, null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        dialog.show()

        val tresButtonNos = view.findViewById<ImageView>(R.id.btn_truco_tres_nos)
        tresButtonNos.setOnClickListener {
            SomaPonto(txtPontosNos,3)
            dialog.dismiss()
        }
        val seisButtonNos = view.findViewById<ImageView>(R.id.btn_truco_seis_nos)
        seisButtonNos.setOnClickListener {
            SomaPonto(txtPontosNos,6)
            dialog.dismiss()
        }
        val noveButtonNos = view.findViewById<ImageView>(R.id.btn_truco_nove_nos)
        noveButtonNos.setOnClickListener {
            SomaPonto(txtPontosNos,9)
            dialog.dismiss()
        }
        val dozeButtonNos = view.findViewById<ImageView>(R.id.btn_truco_doze_nos)
        dozeButtonNos.setOnClickListener {
            SomaPonto(txtPontosNos,12)
            dialog.dismiss()
        }

        val tresButtonEles = view.findViewById<ImageView>(R.id.btn_truco_tres_eles)
        tresButtonEles.setOnClickListener {
            SomaPonto(txtPontosEles,3)
            dialog.dismiss()
        }
        val seisButtonEles = view.findViewById<ImageView>(R.id.btn_truco_seis_eles)
        seisButtonEles.setOnClickListener {
            SomaPonto(txtPontosEles,6)
            dialog.dismiss()
        }
        val noveButtonEles = view.findViewById<ImageView>(R.id.btn_truco_nove_eles)
        noveButtonEles.setOnClickListener {
            SomaPonto(txtPontosEles,9)
            dialog.dismiss()
        }
        val dozeButtonEles = view.findViewById<ImageView>(R.id.btn_truco_doze_eles)
        dozeButtonEles.setOnClickListener {
            SomaPonto(txtPontosEles,12)
            dialog.dismiss()
        }
    }

    fun MostraVitoria(vencedor: TextView) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.imagem_vitoria, null)
        var txtVencedor = view.findViewById<TextView>(R.id.txt_vencedor)
        var vencedortxt = vencedor.text
        txtVencedor.text = vencedortxt
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#90000000")))
        dialog.show()

        val okButton = view.findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun Fala(fala: String){
        if(som == true){
            sistemaDeFala?.speak(fala, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onInit(status: Int) {
        Log.d("TextToSpeech", "onInit status: $status")
        if (status == TextToSpeech.SUCCESS) {
            // Definir o idioma para o Text-to-Speech (opcional)
            val locale = Locale.getDefault()
            val result = sistemaDeFala?.setLanguage(locale)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Se o idioma não estiver disponível ou não suportado, trate conforme necessário
                Toast.makeText(this, "Idioma não suportado para sistema de fala", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Se a inicialização falhar, trate conforme necessário
            Toast.makeText(this, "Erro na inicialização do sistema de fala", Toast.LENGTH_SHORT).show()
        }
    }

}