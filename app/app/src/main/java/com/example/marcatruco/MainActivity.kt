package com.example.marcatruco

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
import android.text.InputFilter
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.graphics.Color


class MainActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pontos, R.id.nav_cartas, R.id.nav_config
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun MostraEditaTexto(textView: TextView) {
        val editText = EditText(this)
        editText.setText(textView.text)
        val maxCharacter = 10
        val inputFilter = InputFilter.LengthFilter(maxCharacter)
        editText.filters = arrayOf(inputFilter)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Editar Texto")
            .setView(editText)
            .setPositiveButton("OK") { dialog, _ ->
                textView.text = editText.text
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        alertDialog.setOnShowListener {
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveButton.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            negativeButton.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }

        alertDialog.window?.setBackgroundDrawableResource(R.drawable.pop_up_personalizado)

        alertDialog.show()
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
            }
            if (pontos.id == R.id.txt_pontos_eles) {
                vitoriasEles++
                txtVitEles.text = vitoriasEles.toString() + " Vitórias"
                ZeraPontuacao()
                AlteraCorVitoria()
                MostraVitoria(txtEles)
            }
        }
    }

    fun SubPonto(pontos: TextView, QtdPontos: Int) {
        val pontosAtuais = pontos.text.toString().toIntOrNull() ?: 0
        if (pontosAtuais > 0) {
            val novosPontos = pontosAtuais - QtdPontos
            pontos.text = novosPontos.toString()
        }
    }

    fun ConfimaZeraVitorias() {

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

}