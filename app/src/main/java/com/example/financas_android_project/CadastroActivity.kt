package com.example.financas_android_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financas_android_project.database.DatabaseHelper
import com.example.financas_android_project.model.DespesaModel
import com.example.financas_android_project.model.UsuarioModel

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_activity)

        val editTextNomeCadastro = findViewById<EditText>(R.id.editTextNomeCadastro)
        val editTextNascCadastro = findViewById<EditText>(R.id.editTextNascCadastro)
        val editTextCPFCadastro = findViewById<EditText>(R.id.editTextCPFCadastro)
        val editTextSalarioCadastro = findViewById<EditText>(R.id.editTextSalarioCadastro)
        val buttonCadastrar = findViewById<Button>(R.id.buttonCadastrar)
        val textViewGoToLogin = findViewById<TextView>(R.id.textViewGoToLogin)

        val bancoDeDados = DatabaseHelper(this)

        buttonCadastrar.setOnClickListener {
            val nome = editTextNomeCadastro.text.toString()
            val data_nasc = editTextNascCadastro.text.toString()
            var cpf = editTextCPFCadastro.text.toString()
            val salario = editTextSalarioCadastro.text.toString()
            var success: Boolean
            val usuario = UsuarioModel()

            if (nome != "" && data_nasc != "" && cpf != "" && salario != "") {
                usuario.nome = nome
                usuario.data_nasc = data_nasc
                // "Máscara CPF"
                if (cpf.contains(".") or cpf.contains("-"))
                    cpf = cpf.replace(".", "")
                cpf = cpf.replace("-", "")
                usuario.cpf = cpf
                //
                usuario.salario = salario.toFloat()
                success = bancoDeDados.addUser(usuario) as Boolean
                if (success) {
                    val i = Intent(applicationContext, MainActivity::class.java)
                    bancoDeDados.getAllUsers()
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Alguma coisa deu errado!", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                Toast.makeText(applicationContext, "Preencha todos os campos para continuar.", Toast.LENGTH_LONG)
                    .show()

            }

        }

        textViewGoToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}