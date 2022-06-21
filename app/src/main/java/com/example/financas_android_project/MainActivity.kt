package com.example.financas_android_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.financas_android_project.database.DatabaseHelper

const val EXTRA_MESSAGE = "com.example.financas-android-project"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textViewGoCadastro = findViewById<TextView>(R.id.textViewGoCadastro)
        var bancoDeDados = DatabaseHelper(this)

        textViewGoCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
            for (u in bancoDeDados.getAllUsers()) {
                println("${u.id_usuario.toString()}  ${u.nome }  ${u.data_nasc}  ${u.salario.toString()}")
            }
        }
    }
    fun logar(view: View) {
        val nome = findViewById<EditText>(R.id.editTextTextNomeLogin)
        val mensagem = nome.text.toString()

        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, mensagem)
        }
        startActivity(intent)
    }



    fun goToCadastro() {

    }

}
