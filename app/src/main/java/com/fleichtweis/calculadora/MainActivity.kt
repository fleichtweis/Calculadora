package com.fleichtweis.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var auxPonto: Boolean = true //Auxiliar para uso do ponto, não permitir usar o ponto mais de uma vez no mesmo número.

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

        iniciarComponentes()

        btnClear.setOnClickListener {
            val display = textDisplay.text.toString()
            val tamanhoString = display.length

            if(display.length > 0){
                //Se true, não é operador, então apaga último caracter.
                //Senão apaga os três últimos, já que o operador possui um espaço em branco antes e depois do símbolo.
                if (verificaCaracter()){
                    if (display.substring(tamanhoString-1, tamanhoString) == "."){
                        //Seta para reusar o ponto em um novo número
                        auxPonto = true
                    }
                    textDisplay.text = display.substring(0, display.length - 1)
                } else{
                    textDisplay.text = display.substring(0, display.length - 3)
                }
            }
        }

        btnClearAll.setOnClickListener {
            textDisplay.text = ""

            //Seta para reusar o ponto em um novo número
            auxPonto = true
        }

        btnIgual.setOnClickListener {
            calcularExpressao()
        }


        btnAbreParenteses.isEnabled = false
        btnFechaParenteses.isEnabled = false

    }

    private fun iniciarComponentes() {
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
    }

    private fun calcularExpressao() {

        textHistorico.text = textDisplay.text

        val expressao = textDisplay.text.toString().split(" ")
        var numero = ""
        var operador = ""
        var resultado = 0.0

        for (e in expressao){
            when(e){
                "+" -> operador = "+"
                "-" -> operador = "-"
                "*" -> operador = "*"
                "/" -> operador = "/"
                else -> {
                    if (numero.isEmpty()) {
                        numero = e
                        resultado = numero.toDouble()
                    } else{
                        numero = e
                        when(operador){
                            "+" -> resultado += numero.toDouble()
                            "-" -> resultado -= numero.toDouble()
                            "*" -> resultado *= numero.toDouble()
                            "/" -> resultado /= numero.toDouble()
                        }
                        operador = ""
                    }
                }
            }
        }

        textDisplay.text = resultado.toString()
        auxPonto = false //Resposta sempre vai ter ponto, logo não permite adicionar um ponto logo em seguida.
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

            //Seta para reusar o ponto em um novo número
            auxPonto = true
        }

    }

    //Clique nos botões de ponto, abre e fecha parentesis
    fun btnAuxiliares(view: View){
        var textoAuxiliar: String = ""

        when(view.id){
            //Faz uma validação para permitir somente um ponto por número (Double)
            R.id.btn_ponto -> {
                if (auxPonto){
                    textoAuxiliar = "."
                    auxPonto = false
                } else{
                    textoAuxiliar = ""
                }
            }
            //R.id.btn_abre_parenteses -> "("
            //R.id.btn_fecha_parenteses -> ")"
            else -> ""
        }

        textDisplay.text = textDisplay.text.toString() + textoAuxiliar
    }

    //Se o caracter for diferente de um dos símbolos das operações, retorna true. Senão retorna false.
    //Verifica o penúltimo caracter, já que após colocar o símbolo de operação é adicionado um espaço em branco.
    private fun verificaCaracter(): Boolean {
        val display = textDisplay.text.toString()
        val tamanhoString = display.length
        if (tamanhoString == 0){
            return false
        } else if (tamanhoString == 1){
            val ultimoCaracter = display.substring(tamanhoString-1, tamanhoString)
            if (ultimoCaracter == "/" || ultimoCaracter == "*" || ultimoCaracter == "-" || ultimoCaracter == "+") return false
        } else{
            val ultimoCaracter = display.substring(tamanhoString-2, tamanhoString-1)
            if (ultimoCaracter == "/" || ultimoCaracter == "*" || ultimoCaracter == "-" || ultimoCaracter == "+") return false
        }

        return true
    }


}