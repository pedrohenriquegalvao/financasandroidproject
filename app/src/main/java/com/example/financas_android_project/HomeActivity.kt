package com.example.financas_android_project

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financas_android_project.adapter.DespesasAdapter
import com.example.financas_android_project.database.DatabaseHelper
import com.example.financas_android_project.model.DespesaModel

class HomeActivity : AppCompatActivity() {

    lateinit var recycler_despesa : RecyclerView
    lateinit var btn_add : Button
    var despesasAdapter : DespesasAdapter ?= null
    var bancoDeDados : DatabaseHelper ?= null
    var despesas : List<DespesaModel> = ArrayList<DespesaModel>()
    var linearlayoutManager : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val textView = findViewById<TextView>(R.id.tituloHome).apply {
            text = "Bem vindo $message"

        }
        recycler_despesa = findViewById(R.id.recyclerViewDespesas)
        btn_add = findViewById(R.id.buttonAddDespesa)

        bancoDeDados = DatabaseHelper(this)

        puxaLista() //Chama a funcao para mostrar as despesas na tela

        btn_add.setOnClickListener { // Click listener do botao adicionar Despesa
            val i = Intent(applicationContext, AddDespesa::class.java)
            startActivity(i)
        }
    }

    private fun puxaLista () {
        despesas = bancoDeDados!!.getAllBills() //Retorna todas as despesas do banco para a tela
        despesasAdapter = DespesasAdapter(despesas, applicationContext)
        linearlayoutManager = LinearLayoutManager(applicationContext)
        recycler_despesa.layoutManager = linearlayoutManager
        recycler_despesa.adapter = despesasAdapter
        despesasAdapter?.notifyDataSetChanged()

    }

}