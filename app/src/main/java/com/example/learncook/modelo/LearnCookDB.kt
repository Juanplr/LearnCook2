package com.example.learncook.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.learncook.poko.Ingrediente
import com.example.learncook.poko.Receta
import com.example.learncook.poko.RecetaDatos
import com.example.learncook.poko.Usuario

class LearnCookDB(contexto: Context): SQLiteOpenHelper(contexto,NOMBRE_DB,null,VERSION_DB){
    companion object {
        private const val  NOMBRE_DB = "learnCook.bd"
        //tabla usuario
        private const val NOMBRE_TABLA_USUARIO ="usuario"
        private const val COL_ID_USUARIO = "id"
        private const val COL_CORREO = "correo"
        private const val COL_CONTRASENA = "contrasena"
        private const val COL_NOMBRE_USUARIO = "nombreUsuario"
        //tabla Receta
        private const val NOMBRE_TABLA_RECETA ="receta"
        private const val COL_ID_RECETA = "id"
        private const val COL_USUARIO_ID = "usuarioId"
        private const val COL_NOMBRE_RECETA = "nombreReceta"
        private const val COL_TIEMPO = "tiempo"
        private const val COL_PRESUPUESTO = "presupuesto"
        private const val COL_PREPARACION = "preparacion"
        //tabla ingrediente
        private const val NOMBRE_TABLA_INGREDIENTE ="ingrediente"
        private const val COL_ID_INGREDIENTE = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_PRECIO = "precio"
        //tabla RecetaIngredientes
        private const val NOMBRE_TABLA_RECETAINGREDIENTES ="recetaIngredientes"
        private const val COL_ID_RECETAINGREDIENTES = "id"
        private const val COL_RECETA_ID = "recetaId"
        private const val COL_INGREDIENTE_ID = "ingredienteId"
        private const val COL_CANTIDAD = "cantidad"
        //tabla seguidor
        private const val NOMBRE_TABLA_SEGUIDOR ="seguidor"
        private const val COL_ID_SEGUIDOR = "id"
        private const val COL_USUARIO_ID_SEGUIDOR = "usuarioId"
        private const val COL_SEGUIDO_ID = "seguidoId"
        //tabla calificaciones
        private const val NOMBRE_TABLA_CALIFICACIONES ="calificaciones"
        private const val COL_ID_CALIFICACIONES = "id"
        private const val COL_USUARIO_ID_CALIFICACION = "usuarioId"
        private const val COL_RECETA_ID_CALIFICACION = "recetaId"
        private const val COL_PUNTUACION = "puntuacion"
        private const val COL_COMENTARIO = "comentario"
        private const val  VERSION_DB = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USUARIO = ("CREATE TABLE $NOMBRE_TABLA_USUARIO (" +
                "$COL_ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_CORREO TEXT NOT NULL UNIQUE, " +
                "$COL_CONTRASENA TEXT NOT NULL, " +
                "$COL_NOMBRE_USUARIO TEXT NOT NULL UNIQUE)")

        val CREATE_TABLE_RECETA = ("CREATE TABLE $NOMBRE_TABLA_RECETA (" +
                "$COL_ID_RECETA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USUARIO_ID INTEGER NOT NULL, " +
                "$COL_TIEMPO TEXT NOT NULL, " +
                "$COL_PRESUPUESTO REAL, " +
                "$COL_PREPARACION TEXT NOT NULL, " +
                "FOREIGN KEY ($COL_USUARIO_ID) REFERENCES $NOMBRE_TABLA_USUARIO($COL_ID_USUARIO))")

        val CREATE_TABLE_INGREDIENTE = ("CREATE TABLE $NOMBRE_TABLA_INGREDIENTE (" +
                "$COL_ID_INGREDIENTE INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NOMBRE TEXT NOT NULL UNIQUE, " +
                "$COL_PRECIO REAL NOT NULL)")

        val CREATE_TABLE_RECETAINGREDIENTES = ("CREATE TABLE $NOMBRE_TABLA_RECETAINGREDIENTES (" +
                "$COL_ID_RECETAINGREDIENTES INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_RECETA_ID INTEGER NOT NULL, " +
                "$COL_INGREDIENTE_ID INTEGER NOT NULL, " +
                "$COL_CANTIDAD REAL NOT NULL, " +
                "FOREIGN KEY ($COL_RECETA_ID) REFERENCES $NOMBRE_TABLA_RECETA($COL_ID_RECETA), " +
                "FOREIGN KEY ($COL_INGREDIENTE_ID) REFERENCES $NOMBRE_TABLA_INGREDIENTE($COL_ID_INGREDIENTE), " +
                "UNIQUE ($COL_RECETA_ID, $COL_INGREDIENTE_ID))")

        val CREATE_TABLE_SEGUIDOR = ("CREATE TABLE $NOMBRE_TABLA_SEGUIDOR (" +
                "$COL_ID_SEGUIDOR INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USUARIO_ID_SEGUIDOR INTEGER NOT NULL, " +
                "$COL_SEGUIDO_ID INTEGER NOT NULL, " +
                "FOREIGN KEY ($COL_USUARIO_ID_SEGUIDOR) REFERENCES $NOMBRE_TABLA_USUARIO($COL_ID_USUARIO), " +
                "FOREIGN KEY ($COL_SEGUIDO_ID) REFERENCES $NOMBRE_TABLA_USUARIO($COL_ID_USUARIO), " +
                "UNIQUE ($COL_USUARIO_ID_SEGUIDOR, $COL_SEGUIDO_ID))")

        val CREATE_TABLE_CALIFICACIONES = ("CREATE TABLE $NOMBRE_TABLA_CALIFICACIONES (" +
                "$COL_ID_CALIFICACIONES INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USUARIO_ID_CALIFICACION INTEGER NOT NULL, " +
                "$COL_RECETA_ID_CALIFICACION INTEGER NOT NULL, " +
                "$COL_PUNTUACION INTEGER NOT NULL CHECK ($COL_PUNTUACION BETWEEN 1 AND 5), " +
                "$COL_COMENTARIO TEXT, " +
                "FOREIGN KEY ($COL_USUARIO_ID_CALIFICACION) REFERENCES $NOMBRE_TABLA_USUARIO($COL_ID_USUARIO), " +
                "FOREIGN KEY ($COL_RECETA_ID_CALIFICACION) REFERENCES $NOMBRE_TABLA_RECETA($COL_ID_RECETA), " +
                "UNIQUE ($COL_USUARIO_ID_CALIFICACION, $COL_RECETA_ID_CALIFICACION))")

        db!!.execSQL(CREATE_TABLE_USUARIO)
        db.execSQL(CREATE_TABLE_RECETA)
        db.execSQL(CREATE_TABLE_INGREDIENTE)
        db.execSQL(CREATE_TABLE_RECETAINGREDIENTES)
        db.execSQL(CREATE_TABLE_SEGUIDOR)
        db.execSQL(CREATE_TABLE_CALIFICACIONES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $NOMBRE_TABLA_RECETA ADD COLUMN $COL_NOMBRE_RECETA TEXT")

            val ingredientesEsenciales = listOf(
                Pair("Sal", 10.0),
                Pair("Pimienta", 7.0),
                Pair("Aceite de oliva", 13.0),
                Pair("Ajo", 15.5),
                Pair("Cebolla", 17.2),
                Pair("Tomate", 22.5),
                Pair("Papa", 30.6),
                Pair("Zanahoria", 25.0),
                Pair("Leche", 31.5),
                Pair("Huevos", 2.5),
                Pair("Harina", 25.2),
                Pair("Arroz", 19.9),
                Pair("Pasta", 11.0),
                Pair("Queso", 42.0),
                Pair("Pollo", 60.0),
                Pair("Carne de res", 80.0),
                Pair("Pescado", 21.0),
                Pair("Limón", 16.0),
                Pair("Cilantro", 11.2),
                Pair("Mantequilla", 27.5)
            )

            for (ingrediente in ingredientesEsenciales) {
                val contentValues = ContentValues().apply {
                    put(COL_NOMBRE, ingrediente.first)
                    put(COL_PRECIO, ingrediente.second)
                }
                db?.insert(NOMBRE_TABLA_INGREDIENTE, null, contentValues)
            }
        }
    }

    fun agregarUsuario(usuario:Usuario): Long {
        val db =writableDatabase
        val valoresInsert = ContentValues()
        valoresInsert.put(COL_CORREO, usuario.correo)
        valoresInsert.put(COL_CONTRASENA,usuario.contrasena)
        valoresInsert.put(COL_NOMBRE_USUARIO, usuario.nombreUsuario)
        val filas = db.insert(NOMBRE_TABLA_USUARIO,null, valoresInsert)
        db.close()
        return filas
    }

    fun usuarioRegistrado(usuario: Usuario):Boolean{
        val db = readableDatabase
        val columnas = arrayOf(COL_ID_USUARIO)
        val filtro = "$COL_CORREO = ? AND $COL_CONTRASENA = ?"
        val clausua = arrayOf(usuario.correo, usuario.contrasena)
        val cursor = db.query(
            NOMBRE_TABLA_USUARIO,
            columnas,
            filtro,
            clausua,
            null,
            null,
            null
        )
        val registrado = cursor.count > 0
        cursor.close()
        db.close()
        return registrado
    }
    fun usuarioEnBase(correo: String):Boolean{
        val db = readableDatabase
        val columnas = arrayOf(COL_ID_USUARIO)
        val filtro = "$COL_CORREO = ? "
        val clausua = arrayOf(correo)
        val cursor = db.query(
            NOMBRE_TABLA_USUARIO,
            columnas,
            filtro,
            clausua,
            null,
            null,
            null
        )
        val registrado = cursor.count > 0
        cursor.close()
        db.close()
        return registrado
    }
    fun traerUsuario(user: Usuario): Usuario?{
        val db = readableDatabase
        val columnas = arrayOf(COL_ID_USUARIO, COL_CORREO, COL_CONTRASENA, COL_NOMBRE_USUARIO)
        val selection = "$COL_CORREO = ?"
        val selectionArgs = arrayOf(user.correo)
        val cursor = db.query(
            NOMBRE_TABLA_USUARIO,
            columnas,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_USUARIO))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow(COL_CORREO))
            val contrasena = cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTRASENA))
            val nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE_USUARIO))
            usuario = Usuario(id, correo, contrasena, nombreUsuario)
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun isCorreo(correo: String): Boolean {
        val db = readableDatabase
        val columnas = arrayOf(COL_ID_USUARIO)
        val filtro = "$COL_CORREO = ? "
        val clausua = arrayOf(correo)
        val cursor = db.query(
            NOMBRE_TABLA_USUARIO,
            columnas,
            filtro,
            clausua,
            null,
            null,
            null
        )
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }
    fun actualizarContrasena(correo: String, contrasena: String):Int{
        val db = writableDatabase
        val valoresUpdate = ContentValues().apply {
            put(COL_CONTRASENA,contrasena)
        }
        val filasA = db.update(NOMBRE_TABLA_USUARIO,valoresUpdate,"$COL_CORREO = ? ", arrayOf(correo))
        db.close()
        return filasA
    }

    fun usuarioNombreRegistrado(nombreUsuario: String): Boolean {
        val db = readableDatabase
        val columnas = arrayOf(COL_ID_USUARIO)
        val filtro = "$COL_NOMBRE_USUARIO = ? "
        val clausua = arrayOf(nombreUsuario)
        val cursor = db.query(
            NOMBRE_TABLA_USUARIO,
            columnas,
            filtro,
            clausua,
            null,
            null,
            null
        )
        val registrado = cursor.count > 0
        cursor.close()
        db.close()
        return registrado
    }
    @SuppressLint("Range")
    fun traerIngredientes(): List<Ingrediente>{
        val misIngredientes = mutableListOf<Ingrediente>()
        val db = readableDatabase
        val resultadoConsulta: Cursor? = db.query(
            NOMBRE_TABLA_INGREDIENTE, null, null,
            null, null, null, null)
        if(resultadoConsulta!=null){
            while (resultadoConsulta.moveToNext()){
                val id = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_INGREDIENTE))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val precio = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_PRECIO))
                val ingrediente = Ingrediente(id, nombre, precio)
                misIngredientes.add(ingrediente)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misIngredientes
    }

    fun agregarReceta(receta: Receta):Long {
        val db =writableDatabase
        val valoresInsert = ContentValues()
        valoresInsert.put(COL_USUARIO_ID, receta.idUsuario)
        valoresInsert.put(COL_NOMBRE_RECETA, receta.nombreReceta)
        valoresInsert.put(COL_TIEMPO,receta.tiempo)
        valoresInsert.put(COL_PRESUPUESTO, receta.presupuseto)
        valoresInsert.put(COL_PREPARACION, receta.preparacion)
        val filas = db.insert(NOMBRE_TABLA_RECETA,null, valoresInsert)
        db.close()
        return filas
    }

    fun traerUltimoIdDeReceta(): Int {
        var ultimoId = -1
        val db = readableDatabase
        val query = "SELECT MAX($COL_ID_RECETA) FROM $NOMBRE_TABLA_RECETA"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            ultimoId = cursor.getInt(0)
        }

        cursor.close()
        db.close()

        return ultimoId
    }
    fun agregarIngredientes(id: Int, lista: List<Int>): Int {
        val db = writableDatabase
        var resultado: Int = -1

        try {
            db.beginTransaction()

            for (ids in lista) {
                val contentValues = ContentValues().apply {
                    put(COL_RECETA_ID, id)
                    put(COL_INGREDIENTE_ID, ids)
                    put(COL_CANTIDAD, 1)
                    // Aquí puedes agregar más campos si es necesario, como la cantidad
                }
                val insertResult = db.insert(NOMBRE_TABLA_RECETAINGREDIENTES, null, contentValues)
                if (insertResult == -1L) {
                    throw SQLException("Error al insertar ingredientes")
                }
            }

            db.setTransactionSuccessful()
            resultado = 1
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
            db.close()
        }

        return resultado
    }
    fun obtenerRecetasDatosPorUsuario(idUsuario: Int): List<RecetaDatos> {
        val recetasDatos = mutableListOf<RecetaDatos>()
        val db = readableDatabase

        val query = """
        SELECT 
            r.$COL_ID_RECETA, 
            u.$COL_NOMBRE_USUARIO, 
            r.$COL_NOMBRE_RECETA, 
            r.$COL_TIEMPO, 
            r.$COL_PRESUPUESTO, 
            r.$COL_PREPARACION,
            i.$COL_ID_INGREDIENTE,
            i.$COL_NOMBRE,
            i.$COL_PRECIO
        FROM $NOMBRE_TABLA_RECETA r
        INNER JOIN $NOMBRE_TABLA_USUARIO u ON r.$COL_USUARIO_ID = u.$COL_ID_USUARIO
        INNER JOIN $NOMBRE_TABLA_RECETAINGREDIENTES ri ON r.$COL_ID_RECETA = ri.$COL_RECETA_ID
        INNER JOIN $NOMBRE_TABLA_INGREDIENTE i ON ri.$COL_INGREDIENTE_ID = i.$COL_ID_INGREDIENTE
        WHERE r.$COL_USUARIO_ID = ?
    """.trimIndent()

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(idUsuario.toString()))

            var currentRecetaId = -1
            var currentReceta: RecetaDatos? = null

            while (cursor.moveToNext()) {
                val idReceta = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_RECETA))

                // Si es una nueva receta, crea una instancia de RecetaDatos
                if (idReceta != currentRecetaId) {
                    // Guarda la receta actual si ya existe
                    if (currentReceta != null) {
                        recetasDatos.add(currentReceta)
                    }

                    currentRecetaId = idReceta
                    val nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE_USUARIO))
                    val nombreReceta = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE_RECETA))
                    val tiempo = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIEMPO))
                    val presupuesto = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRESUPUESTO))
                    val preparacion = cursor.getString(cursor.getColumnIndexOrThrow(COL_PREPARACION))

                    // Crea una nueva instancia de RecetaDatos
                    currentReceta = RecetaDatos(
                        idReceta,
                        nombreUsuario,
                        nombreReceta,
                        tiempo,
                        presupuesto,
                        preparacion,
                        mutableListOf()
                    )
                }

                // Agrega el ingrediente a la lista de ingredientes de la receta actual
                if (currentReceta != null) {
                    val idIngrediente = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_INGREDIENTE))
                    val nombreIngrediente = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
                    val precioIngrediente = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRECIO))
                    val ingrediente = Ingrediente(idIngrediente, nombreIngrediente, precioIngrediente)

                    currentReceta.ingredientes.add(ingrediente)
                }
            }

            // Agrega la última receta obtenida del cursor
            if (currentReceta != null) {
                recetasDatos.add(currentReceta)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }

        return recetasDatos
    }

}