package com.example.financas_android_project.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financas_android_project.AddDespesa
import com.example.financas_android_project.R
import com.example.financas_android_project.model.DespesaModel
import org.w3c.dom.Text

class DespesasAdapter(despesas: List<DespesaModel>, internal var context: Context) :
    RecyclerView.Adapter<DespesasAdapter.DespesaViewHolder>() {

    internal var despesas: List<DespesaModel> = ArrayList()
    init {
        this.despesas = despesas
    }

        inner class DespesaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var nome_despesa: TextView = view.findViewById(R.id.textViewNomeDespesa)
            var data_venc: TextView = view.findViewById(R.id.textViewDataVenc)
            var valor: TextView = view.findViewById(R.id.textViewValorDespesa)
            var btn_edit: Button = view.findViewById(R.id.buttonEditDespesa)
            var btn_delete: Button = view.findViewById(R.id.buttonDeleteDespesa)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespesaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_despesa, parent, false)
        return DespesaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DespesaViewHolder, position: Int) {
        val despesa = despesas[position]
        holder.nome_despesa.text = despesa.nome_despesa
        holder.data_venc.text = despesa.data_venc
        holder.valor.text = despesa.valor.toString()

        holder.btn_edit.setOnClickListener {
            val i = Intent(context, AddDespesa::class.java)
            i.putExtra("Modo", "Editar")
            i.putExtra("Id", despesa.id_despesa)
            context.startActivity(i)

        }
    }

    override fun getItemCount(): Int {
        return despesas.size
    }

}