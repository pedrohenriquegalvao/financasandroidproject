package com.example.financas_android_project

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financas_android_project.database.DatabaseHelper
import com.example.financas_android_project.model.DespesaModel

class AddDespesa : AppCompatActivity() {

    lateinit var textViewTituloTelaAdd : TextView
    lateinit var btn_salvar : Button
    lateinit var editTextNomeDespesa: EditText
    lateinit var editTextValorDespesa: EditText
    lateinit var editTextDataVencDespesa: EditText
    var bancoDeDados : DatabaseHelper ?= null
    var modoEditar : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_despesa)

        val cpf = intent.getStringExtra("CPF")
        val nome = intent.getStringExtra("Nome")


        println("CPF ENTRANDO NO ADICIONAR/EDITAR $cpf")

        textViewTituloTelaAdd = findViewById(R.id.textViewTituloTelaAdd)
        btn_salvar = findViewById(R.id.btn_salvar)
        editTextNomeDespesa = findViewById(R.id.editTextNomeDespesa)
        editTextValorDespesa = findViewById(R.id.editTextValorDespesa)
        editTextDataVencDespesa = findViewById(R.id.editTextDataVencDespesa)

        bancoDeDados = DatabaseHelper(this)


        if (intent != null && intent.getStringExtra("Modo") == "Editar") {
            //Fazer update da despesa
            modoEditar = true
            textViewTituloTelaAdd.setText("Editar despesa")
            btn_salvar.text = "Atualizar"
            val despesas : DespesaModel = bancoDeDados!!.getDespesas(intent.getIntExtra("Id", 0))
            editTextNomeDespesa.setText(despesas.nome_despesa)
            editTextValorDespesa.setText(despesas.valor.toString())
            editTextDataVencDespesa.setText(despesas.data_venc)

        } else {
            //Adicionar uma despesa
            modoEditar = false
            textViewTituloTelaAdd.setText("Adicionar despesa")
            btn_salvar.text = "Adicionar"

        }

        btn_salvar.setOnClickListener {
            var success: Boolean
            var despesas = DespesaModel()
            if (modoEditar) {
            //Editar
                despesas.id_despesa = intent.getIntExtra("Id", 0)
                despesas.nome_despesa = editTextNomeDespesa.text.toString()
                despesas.fk_usuario = cpf!!
                despesas.valor = editTextValorDespesa.text.toString().toFloat()
                despesas.data_venc = editTextDataVencDespesa.text.toString()
                success = bancoDeDados?.updateDespesa(despesas) as Boolean
            } else {
            //Adicionar
                despesas.nome_despesa = editTextNomeDespesa.text.toString()
                despesas.fk_usuario = intent.getStringExtra("CPF").toString()
                despesas.valor = editTextValorDespesa.text.toString().toFloat()
                despesas.data_venc = editTextDataVencDespesa.text.toString()
                success = bancoDeDados?.addDespesa(despesas) as Boolean
            }

            if (success) {
                val i = Intent(applicationContext, HomeActivity::class.java)
                i.putExtra("Nome", nome)
                i.putExtra("CPF", cpf)
                println("CPF VOLTANDO DO EDITAR: $cpf")
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Alguma coisa deu errado!", Toast.LENGTH_LONG).show()
            }
        }



    }
}