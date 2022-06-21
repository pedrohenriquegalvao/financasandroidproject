package com.example.financas_android_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        val bancoDeDados = DatabaseHelper(this)


        buttonCadastrar.setOnClickListener {
            /*var success: Boolean
            var despesas = DespesaModel()
            despesas.nome_despesa = editTextNomeDespesa.text.toString()
            despesas.valor = editTextValorDespesa.text.toString().toFloat()
            success = bancoDeDados?.addDespesa(despesas) as Boolean
            if (success) {
                val i = Intent(applicationContext, HomeActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Alguma coisa deu errado!", Toast.LENGTH_LONG)
                    .show()
            }*/
            var success: Boolean
            var usuario = UsuarioModel()
            usuario.nome = editTextNomeCadastro.text.toString()
            usuario.data_nasc = editTextNascCadastro.text.toString()
            usuario.cpf = editTextCPFCadastro.text.toString()
            usuario.salario = editTextSalarioCadastro.text.toString().toFloat()

            success = bancoDeDados.addUser(usuario) as Boolean
            if (success) {
                val i = Intent(applicationContext, HomeActivity::class.java)

                bancoDeDados.getAllUsers()
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Alguma coisa deu errado!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}