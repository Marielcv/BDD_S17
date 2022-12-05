package com.example.bdd_s17



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegistrosActivity : AppCompatActivity() {
    private lateinit var etId: EditText
    private lateinit var etNombre: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etPass: EditText

    private lateinit var id:String

    lateinit var procesoEnCola:RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        etNombre = findViewById(R.id.et_Nombre)
        etEmail = findViewById(R.id.et_email)
        etTelefono = findViewById(R.id.et_Telefono)
        etPass = findViewById(R.id.et_Pass)
        etId = findViewById(R.id.et_Id)
        id = intent.getStringExtra("id").toString()
        etId.setText(id)
        cargarDatos()


    }

    fun limpiarCampos(){
        etNombre.setText("")
        etEmail.setText("")
        etTelefono.setText("")
        etPass.setText("")
        etId.setText("")
    }

    fun cargarDatos(){
        val url = "http://192.168.1.3/android_mysql2/consulta.php?id=$id"
        procesoEnCola = Volley.newRequestQueue(this)
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { Respuesta->
                etNombre?.setText(Respuesta.getString("nombre"))
                etEmail?.setText(Respuesta.getString("email"))
                etTelefono?.setText(Respuesta.getString("telefono"))
                etPass?.setText(Respuesta.getString("pass"))
                Toast.makeText(this,"Datos obtenidos",Toast.LENGTH_LONG).show()
            },Response.ErrorListener { error->
                Toast.makeText(this,"Error: $error",Toast.LENGTH_LONG).show()
            }
        ){}
        procesoEnCola.add(jsonObjectRequest)
    }

    fun eliminar(Vista: View){
        val url = "http://192.168.1.3/android_mysql2/borrar.php"
        procesoEnCola = Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST, url,
            Response.Listener { Respuesta->
                limpiarCampos()
                Toast.makeText(this,"El usuario a sido borrado",Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error->
                Toast.makeText(this,"Error al borrar usuario",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                parametros.put("id",etId.text.toString())
                return parametros
            }
        }
        procesoEnCola.add(resultadoPost)
    }

    fun editar(Vista: View){
        val url = "http://192.168.1.3/android_mysql2/editar.php"
        procesoEnCola = Volley.newRequestQueue(this)
        val resultadoPost = object : StringRequest(Request.Method.POST,url,
            Response.Listener { respuesta->
                Toast.makeText(this,"El usuario fue editado correctamente",Toast.LENGTH_LONG).show()
            },Response.ErrorListener { error->
                Toast.makeText(this,"Error al editar usuario",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String,String>()
                parametros.put("id",etId.text.toString())
                parametros.put("nombre",etNombre.text.toString())
                parametros.put("email",etEmail.text.toString())
                parametros.put("telefono",etTelefono.text.toString())
                parametros.put("pass",etPass.text.toString())
                return parametros
            }
        }
        procesoEnCola.add(resultadoPost)

    }

    fun regresar(Vista: View){
        var ventana = Intent(this,MainActivity::class.java)
        startActivity(ventana)
    }



}












