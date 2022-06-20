package com.example.financas_android_project.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.financas_android_project.AddDespesa
import com.example.financas_android_project.HomeActivity
import com.example.financas_android_project.R
import com.example.financas_android_project.database.DatabaseHelper
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
        val view = LayoutInflater.from(context).inflate(R.layout.card, parent, false)
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
            println("/DespesasAdapter/ id_despesa: " + despesa.id_despesa)
            println("nome_despesa: " + despesa.nome_despesa)
            println("valor: " + despesa.valor)
            this.context.startActivity(i)
        }

        holder.btn_delete.setOnClickListener {
            var bancoDeDados: DatabaseHelper? = null
            bancoDeDados = DatabaseHelper(context)

            val dialog = AlertDialog.Builder(context).setTitle("Info").setMessage("Tem certeza que deseja deletar?")
                .setPositiveButton("SIM") { dialog, i ->
                    val success = bancoDeDados.deleteDespesa(despesa.id_despesa) as Boolean
                    if (success) {
                        val i2 = Intent(context, HomeActivity::class.java)
                        i2.putExtra("IdUsuario", despesa.fk_usuario)
                        context.startActivity(i2)
                        dialog.dismiss()
                    }
                }.setNegativeButton("NÃO") { dialog, _ ->
                    dialog.dismiss()
                }

            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return despesas.size
    }

}