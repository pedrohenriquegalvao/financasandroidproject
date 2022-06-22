package com.example.financas_android_project

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financas_android_project.database.DatabaseHelper

class AttSalario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_att_salario)

        val nome = intent.getStringExtra("Nome")
        val cpf = intent.getStringExtra("CPF")
        val salario = intent.getStringExtra("Salario")

        val editTextAttSalario = findViewById<EditText>(R.id.editTextAttSalario)
        editTextAttSalario.setText(salario)

        val btn_att_salario = findViewById<Button>(R.id.buttonAttSalario)
        btn_att_salario.setOnClickListener {
            val bancoDeDados = DatabaseHelper(this)
            val usuario = bancoDeDados.getUser(cpf!!)
            usuario.salario = editTextAttSalario.text.toString().toFloat()
            val success = bancoDeDados.updateUser(usuario) as Boolean
            if (success) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("CPF", cpf)
                intent.putExtra("Nome", nome)
                startActivity(intent)
            } else {
                Toast.makeText(this,"Algo deu errado ao atualizar o salário.", Toast.LENGTH_LONG).show()
                finish()
            }
        }

    }
}