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
    lateinit var btn_atualizar_salario : Button
    lateinit var btn_add : Button
    var despesasAdapter : DespesasAdapter ?= null
    var bancoDeDados : DatabaseHelper ?= null
    var despesas : List<DespesaModel> = ArrayList<DespesaModel>()
    var linearlayoutManager : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var btn_sair = findViewById<Button>(R.id.buttonSair)
        btn_sair.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val cpf = intent.getStringExtra("CPF")
        println("CPF CHEGANDO NA HOME: $cpf")
        val nome = intent.getStringExtra("Nome")

        val textView = findViewById<TextView>(R.id.tituloHome).apply {
            text = "Bem vindo $nome"
        }

        btn_add = findViewById(R.id.buttonAddDespesa)
        recycler_despesa = findViewById(R.id.recyclerViewDespesas)

        //Pegar salario do usuario no banco
        bancoDeDados = DatabaseHelper(this)

        var salario = bancoDeDados!!.getUser(cpf!!).salario.toString()
        var salarioFormatado = salario.replace(".", ",0")

        val textViewSalario = findViewById<TextView>(R.id.textViewSalario)
        textViewSalario.setText("Seu salario atual é: R$$salarioFormatado")
        //

        btn_atualizar_salario = findViewById(R.id.buttonAtualizarSalario)
        btn_atualizar_salario.setOnClickListener {
            val intentAttSalario = Intent(this, AttSalario::class.java)
            intentAttSalario.putExtra("CPF", cpf)
            intentAttSalario.putExtra("Nome", nome)
            intentAttSalario.putExtra("Salario", salario)
            startActivity(intentAttSalario)
        }

        puxaLista(cpf) //Chama a funcao para mostrar as despesas na tela

        btn_add.setOnClickListener { // Click listener do botao adicionar Despesa
            val i = Intent(applicationContext, AddDespesa::class.java)
            i.putExtra("Nome", nome)
            i.putExtra("CPF", cpf)
            startActivity(i)
        }


    }

    private fun puxaLista (cpf: String?) {
        despesas = bancoDeDados!!.getAllBills(cpf!!) //Retorna todas as despesas do banco para a tela
        for (d in despesas) {
            println("${d.id_despesa}  ${d.nome_despesa}  ${d.fk_usuario}  ${d.valor} ${d.data_venc}")
        }
        despesasAdapter = DespesasAdapter(despesas, this)
        linearlayoutManager = LinearLayoutManager(this)
        recycler_despesa.layoutManager = linearlayoutManager
        recycler_despesa.adapter = despesasAdapter
        despesasAdapter?.notifyDataSetChanged()

    }

}