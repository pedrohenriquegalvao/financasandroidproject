package com.example.financas_android_project

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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


    }


}