package com.example.financas_android_project

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.financas_android_project.database.DatabaseHelper

const val EXTRA_MESSAGE = "com.example.financas-android-project"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editTextCPFLogin = findViewById<EditText>(R.id.editTextTextCPFLogin)
        var editTextNomeLogin = findViewById<EditText>(R.id.editTextTextNomeLogin)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewGoToCadastro = findViewById<TextView>(R.id.textViewGoToCadastro)
        val bancoDeDados = DatabaseHelper(this)

        buttonLogin.setOnClickListener {
            var nome = editTextNomeLogin.text.toString()
            //Mascara CPF
            var cpf = editTextCPFLogin.text.toString()
            if (cpf.contains(".") or cpf.contains("-"))
                cpf = cpf.replace(".", "")
                cpf = cpf.replace("-", "")
            //
            var success: Boolean
            if (nome != "" && cpf != "") {
                val usuario = bancoDeDados.getUser(cpf)

                if (usuario.cpf == "") { //Caso nao ache nenhum registro no banco, todos os atributos de usuario estarão vazios.
                    Toast.makeText(applicationContext, "Não há nenhum usuário cadastrado com esse CPF!", Toast.LENGTH_LONG).show()
                } else {
                    cpf = usuario.cpf
                    nome = usuario.nome
                    val intent = Intent(this, HomeActivity::class.java).apply {
                        putExtra("CPF", cpf)
                        putExtra("Nome", nome)
                    }
                    startActivity(intent)
                }

            } else {
                Toast.makeText(applicationContext, "Preencha todos os campos para continuar.", Toast.LENGTH_SHORT).show()
            }

        }

        textViewGoToCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
            for (u in bancoDeDados.getAllUsers()) {
                println("${u.cpf}  ${u.nome}  ${u.data_nasc}  ${u.salario}")
            }
        }
    }

}
