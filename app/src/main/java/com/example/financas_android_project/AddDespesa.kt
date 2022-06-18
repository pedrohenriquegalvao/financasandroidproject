package com.example.financas_android_project

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financas_android_project.database.DatabaseHelper
import com.example.financas_android_project.model.DespesaModel

class AddDespesa : AppCompatActivity() {

    lateinit var btn_salvar : Button
    lateinit var editTextNomeDespesa: EditText
    lateinit var editTextValorDespesa: EditText
    var bancoDeDados : DatabaseHelper ?= null
    var modoEditar : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_despesa)

        btn_salvar = findViewById(R.id.btn_salvar)
        editTextNomeDespesa = findViewById(R.id.editTextNomeDespesa)
        editTextValorDespesa = findViewById(R.id.editTextValorDespesa)

        bancoDeDados = DatabaseHelper(this)


        if (intent != null && intent.getStringExtra("Modo") == "Editar") {
            //Fazer update da despesa
            modoEditar = true
            btn_salvar.text = "Atualizar"
            val despesas : DespesaModel = bancoDeDados!!.getDespesas(intent.getIntExtra("Id", 0))
            editTextNomeDespesa.setText(despesas.nome_despesa)
            editTextValorDespesa.setText(despesas.valor.toString())


        } else {
            //Adicionar uma despesa
            modoEditar = false
            btn_salvar.text = "Adicionar"

        }

        btn_salvar.setOnClickListener {
            var success : Boolean = false
            var despesas : DespesaModel = DespesaModel()
            if (modoEditar) {
            //Editar
                despesas.id_despesa = intent.getIntExtra("Id", 0)
                despesas.nome_despesa = editTextNomeDespesa.text.toString()
                despesas.valor = editTextValorDespesa.text.toString().toFloat()

                success = bancoDeDados?.updateDespesa(despesas) as Boolean
            } else {
            //Adicionar
                despesas.nome_despesa = editTextNomeDespesa.text.toString()
                despesas.valor = editTextValorDespesa.text.toString().toFloat()

                success = bancoDeDados?.addDespesa(despesas) as Boolean
            }

            if (success) {
                val i = Intent(applicationContext, HomeActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Alguma coisa deu errado!", Toast.LENGTH_LONG).show()
            }
        }



    }
}