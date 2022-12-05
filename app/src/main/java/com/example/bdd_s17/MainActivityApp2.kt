package com.example.bdd_s17



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var etId: EditText?=null
    var etNombre: EditText?=null
    var etEmail: EditText?=null
    var etTelefono: EditText?=null
    var etPass: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNombre = findViewById(R.id.et_Nombre)
        etEmail = findViewById(R.id.et_Email)
        etTelefono = findViewById(R.id.et_Telefono)
        etPass = findViewById(R.id.et_Pass)
        etId = findViewById(R.id.et_Id)
    }

    fun Guardar(Vista:View){
        val url = "http://192.168.1.3/android_mysql2/insertar.php"
        val procesoEnCola = Volley.newRequestQueue(this)
        var resultadoPost = object: StringRequest(Request.Method.POST,url,
            Response.Listener<String> { respuesta ->
                Toast.makeText(this,"Usuario insertado exitosamente", Toast.LENGTH_LONG).show()
                etNombre?.setText("")
                etEmail?.setText("")
                etTelefono?.setText("")
                etPass?.setText("")
            }, Response.ErrorListener { error->
                Toast.makeText(this,"Error $error",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametro = HashMap<String,String>()
                parametro.put("nombre", etNombre?.text.toString())
                parametro.put("email",etEmail?.text.toString())
                parametro.put("telefono",etTelefono?.text.toString())
                parametro.put("pass", etPass?.text.toString())
                return parametro
            }
        }
        procesoEnCola.add(resultadoPost)
    }

    fun siguiente(Vista: View){
        var ventana:Intent = Intent(this,RegistrosActivity::class.java)
        ventana.putExtra("id",etId?.text.toString())
        startActivity(ventana)
    }


}
