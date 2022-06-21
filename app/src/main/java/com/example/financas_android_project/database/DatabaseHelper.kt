package com.example.financas_android_project.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.financas_android_project.model.DespesaModel
import com.example.financas_android_project.model.UsuarioModel
import kotlin.reflect.typeOf

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_NAME = "financas"
        private val DB_VERSION = 1

        //TABELA USUARIO
        private val TABLE_NAME = "usuario"
        private val NOME_USUARIO = "nomeusuario"
        private val DATA_NASC = "datanasc"
        private val CPF = "cpf"
        private val SALARIO = "salario"

        //TABELA DESPESA
        private val TABLE_DESPESA_NAME = "despesa"
        private val ID_DESPESA = "id_despesa"
        private val FK_USUARIO = "fk_usuario"
        private val NOME_DESPESA = "nomedespesa"
        private val DATA_VENC = "datavenc"
        private val VALOR = "valor"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($CPF TEXT PRIMARY KEY, $NOME_USUARIO TEXT, " +
                "$DATA_NASC TEXT, $SALARIO NUMERIC);"

        val CREATE_TABLE_DESPESA = "CREATE TABLE $TABLE_DESPESA_NAME ($ID_DESPESA INTEGER PRIMARY KEY," +
                " $FK_USUARIO TEXT, $NOME_DESPESA TEXT, $DATA_VENC TEXT, $VALOR NUMERIC," +
                " FOREIGN KEY ("+FK_USUARIO+") REFERENCES $TABLE_NAME("+CPF+"));"

        p0?.execSQL(CREATE_TABLE)
        p0?.execSQL(CREATE_TABLE_DESPESA)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        val DROP_TABLE_DESPESA = "DROP TABLE IF EXISTS $TABLE_DESPESA_NAME"
        p0?.execSQL(DROP_TABLE_DESPESA)
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    // ------ OPERACOES SOBRE USUARIOS --------

    // SELECT DE TODOS OS USUARIOS
    fun getAllUsers(): List<UsuarioModel> {
        val listaUsuarios = ArrayList<UsuarioModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    val usuarios = UsuarioModel()
                    usuarios.cpf = cursor.getString(cursor.getColumnIndexOrThrow(CPF))
                    usuarios.nome = cursor.getString(cursor.getColumnIndexOrThrow(NOME_USUARIO))
                    usuarios.data_nasc = cursor.getString(cursor.getColumnIndexOrThrow(DATA_NASC))
                    usuarios.salario = cursor.getFloat(cursor.getColumnIndexOrThrow(SALARIO))
                    listaUsuarios.add(usuarios)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return listaUsuarios
    }

    // INSERT USUARIO
    fun addUser(usuarios : UsuarioModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CPF, usuarios.cpf)
        values.put(NOME_USUARIO, usuarios.nome)
        values.put(DATA_NASC, usuarios.data_nasc)
        values.put(SALARIO, usuarios.salario)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    // SELECT DE UM USUARIO ESPECIFICO PELO ID
    fun getUser(_CPF: String) : UsuarioModel {
        val usuarios = UsuarioModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE CPF = $_CPF"
        println(selectQuery)
        val cursor = db.rawQuery(selectQuery, null, )
        if (cursor.count > 0) {
            cursor?.moveToFirst()
            usuarios.cpf = cursor.getString(cursor.getColumnIndexOrThrow(CPF))
            usuarios.nome = cursor.getString(cursor.getColumnIndexOrThrow(NOME_USUARIO))
            usuarios.data_nasc = cursor.getString(cursor.getColumnIndexOrThrow(DATA_NASC))
            usuarios.salario = cursor.getFloat(cursor.getColumnIndexOrThrow(SALARIO))
            cursor.close()
        } else {
            println("Nada no banco com esse CPF.")
        }
        return usuarios
    }

    // DELETE DE UM USUARIO ESPECIFICO PELO ID
    fun deleteUser(CPF: String): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, CPF+"=?", arrayOf(CPF.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    // UPDATE DE UM USUARIO
    fun updateUser(usuarios: UsuarioModel) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CPF, usuarios.cpf)
        values.put(NOME_USUARIO, usuarios.nome)
        values.put(DATA_NASC, usuarios.data_nasc)
        values.put(SALARIO, usuarios.salario)
        val _success = db.update(TABLE_NAME, values, CPF+"=?", arrayOf(usuarios.cpf.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }



    // -------- OPERACOES SOBRE DESPESAS ----------

    // SELECT DE TODAS AS DESPESAS
    fun getAllBills(_CPF: String): List<DespesaModel> {
        val listaDespesas = ArrayList<DespesaModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_DESPESA_NAME WHERE FK_USUARIO = $_CPF"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    val despesas = DespesaModel()
                    despesas.id_despesa = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ID_DESPESA)))
                    despesas.nome_despesa = cursor.getString(cursor.getColumnIndexOrThrow(NOME_DESPESA))
                    despesas.fk_usuario = cursor.getString(cursor.getColumnIndexOrThrow(FK_USUARIO))
                    despesas.data_venc = cursor.getString(cursor.getColumnIndexOrThrow(DATA_VENC))
                    despesas.valor = cursor.getFloat(cursor.getColumnIndexOrThrow(VALOR))
                    listaDespesas.add(despesas)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return listaDespesas
    }

    // INSERT DESPESA
    fun addDespesa(despesas : DespesaModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FK_USUARIO, despesas.fk_usuario)
        values.put(NOME_DESPESA, despesas.nome_despesa)
        values.put(DATA_VENC, despesas.data_venc)
        values.put(VALOR, despesas.valor)
        val _success = db.insert(TABLE_DESPESA_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    // SELECT DE DESPESAS ESPECIFICAS DE UM USUARIO
    fun getDespesas(_id: Int) : DespesaModel {
        val despesas = DespesaModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_DESPESA_NAME WHERE $ID_DESPESA = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        if (cursor.count > 0) {
            despesas.id_despesa = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ID_DESPESA)))
            despesas.nome_despesa = cursor.getString(cursor.getColumnIndexOrThrow(NOME_DESPESA))
            despesas.fk_usuario = cursor.getString(cursor.getColumnIndexOrThrow(FK_USUARIO))
            despesas.data_venc = cursor.getString(cursor.getColumnIndexOrThrow(DATA_VENC))
            despesas.valor = cursor.getFloat(cursor.getColumnIndexOrThrow(VALOR))
        }
        cursor.close()
        return despesas
    }

    // DELETE DE UMA DESPESA ESPECIFICA PELO ID
    fun deleteDespesa(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_DESPESA_NAME, ID_DESPESA+"=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    // UPDATE DE UMA DESPESA
    fun updateDespesa(despesas: DespesaModel) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FK_USUARIO, despesas.fk_usuario)
        values.put(NOME_DESPESA, despesas.nome_despesa)
        values.put(DATA_VENC, despesas.data_venc)
        values.put(VALOR, despesas.valor)
        val _success = db.update(TABLE_DESPESA_NAME, values, ID_DESPESA+"=?", arrayOf(despesas.id_despesa.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}