package com.fleichtweis.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //private var display: String = ""

    private lateinit var textDisplay: TextView
    private lateinit var textHistorico: TextView

    private lateinit var btnClearAll: Button
    private lateinit var btnClear: Button
    private lateinit var btnIgual: Button
    private lateinit var btnAbreParenteses: Button
    private lateinit var btnFechaParenteses: Button
    private lateinit var btnPonto: Button

    // Botões Operações
    private lateinit var btnDividir: Button
    private lateinit var btnMultiplicar: Button
    private lateinit var btnSubtrair: Button
    private lateinit var btnAdicionar: Button

    // Botões Numéricos
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textDisplay = findViewById(R.id.text_display)
        textHistorico = findViewById(R.id.text_historico)

        btnClearAll = findViewById(R.id.btn_clear_all)
        btnClear = findViewById(R.id.btn_clear)
        btnIgual = findViewById(R.id.btn_igual)
        btnAbreParenteses = findViewById(R.id.btn_abre_parenteses)
        btnFechaParenteses = findViewById(R.id.btn_fecha_parenteses)
        btnPonto = findViewById(R.id.btn_ponto)

        // Botões Operações
        btnDividir = findViewById(R.id.btn_dividir)
        btnMultiplicar = findViewById(R.id.btn_multiplicar)
        btnSubtrair = findViewById(R.id.btn_subtrair)
        btnAdicionar = findViewById(R.id.btn_adicionar)

        // Botões Numéricos
        btn0 = findViewById(R.id.btn_0)
        btn1 = findViewById(R.id.btn_1)
        btn2 = findViewById(R.id.btn_2)
        btn3 = findViewById(R.id.btn_3)
        btn4 = findViewById(R.id.btn_4)
        btn5 = findViewById(R.id.btn_5)
        btn6 = findViewById(R.id.btn_6)
        btn7 = findViewById(R.id.btn_7)
        btn8 = findViewById(R.id.btn_8)
        btn9 = findViewById(R.id.btn_9)


        btnClear.setOnClickListener {
            val display = textDisplay.text.toString()

            if(display.length > 0){
                //Se true, não é operador, então apaga último caracter.
                //Senão apaga os três últimos, já que o operador possui um espaço em branco antes e depois do símbolo.
                if (verificaCaracter()){
                    textDisplay.text = display.substring(0, display.length - 1)
                } else{
                    textDisplay.text = display.substring(0, display.length - 3)
                }
            }
        }

        btnClearAll.setOnClickListener {
            textDisplay.text = ""
        }

        btnIgual.setOnClickListener {

        }



    }



    //Clique em um dos botões numéricos
    fun btnNumerico(view: View){
        var textoBotao: String = ""

        textoBotao = when(view.id){
            R.id.btn_0 -> "0"
            R.id.btn_1 -> "1"
            R.id.btn_2 -> "2"
            R.id.btn_3 -> "3"
            R.id.btn_4 -> "4"
            R.id.btn_5 -> "5"
            R.id.btn_6 -> "6"
            R.id.btn_7 -> "7"
            R.id.btn_8 -> "8"
            R.id.btn_9 -> "9"
            else -> ""
        }

        val display = textDisplay.text.toString() + textoBotao

        textDisplay.text = display
    }

    //Clique em um dos botões de operação
    fun btnOperacao(view: View){
        var textoOperacao: String = ""

        textoOperacao = when(view.id){
            R.id.btn_dividir -> "/"
            R.id.btn_multiplicar -> "*"
            R.id.btn_subtrair -> "-"
            R.id.btn_adicionar -> "+"
            else -> ""
        }

        //Evitar de colocar dois símbolos de operações.
        //Podemos fazer ainda é trocar o operador, caso o último seja trocado.
        if (verificaCaracter()){
            val display = textDisplay.text.toString() + " $textoOperacao "
            textDisplay.text = display
        }

    }

    //Clique nos botões de ponto, abre e fecha parentesis
    fun btnAuxiliares(view: View){
        var textoAuxiliar: String = ""
        val display = textDisplay.text

        textoAuxiliar = when(view.id){
            R.id.btn_ponto -> "."
            R.id.btn_abre_parenteses -> "("
            R.id.btn_fecha_parenteses -> ")"
            else -> ""
        }

        //Valida o caracter ponto
        // ##################
        //ARRUMAR PARA ACEITAR MAIS PONTOS, EM OUTROS NÚMEROS
        // #################
        if(textoAuxiliar == "." && display.contains('.')) textoAuxiliar = ""

        textDisplay.text = textDisplay.text.toString() + textoAuxiliar
    }

    //Se o caracter for diferente de um dos símbolos das operações, retorna true. Senão retorna false.
    //
    //Verifica o penúltimo caracter, já que após colocar o símbolo de operação é adicionado um espaço em branco.
    private fun verificaCaracter(): Boolean {
        val display = textDisplay.text.toString()
        val tamanhoString = display.length
        val ultimoCaracter = display.substring(tamanhoString-2, tamanhoString-1)
        if (ultimoCaracter == "/" || ultimoCaracter == "*" || ultimoCaracter == "-" || ultimoCaracter == "+") return false

        return true
    }


}