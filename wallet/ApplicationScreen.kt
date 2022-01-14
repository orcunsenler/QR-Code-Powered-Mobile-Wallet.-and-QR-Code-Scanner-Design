package com.example.owallet
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.http.GET
class ApplicationScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_screen)
        val txtLoginUser = intent.getStringExtra("username")
        val txtAppUser = findViewById<TextView>(R.id.txtAppUser)
        val txtBalance = findViewById<TextView>(R.id.txtBalance)
        val txtName = findViewById<TextView>(R.id.txtName)
        val txtSurname = findViewById<TextView>(R.id.txtSurname)
        var password : String
        var url= "http://192.168.43.215/wallet/readCash.php?username="+txtLoginUser
        var rq:RequestQueue=Volley.newRequestQueue(this)
        var jar=JsonObjectRequest(Request.Method.GET,url,null, { response ->
            txtAppUser.text = "User: " + response.getString("username")
            txtName.text = response.getString("name")
            txtSurname.text = response.getString("surname")
            password =response.getString("password")
            txtBalance.text = "Balance: " + response.getInt("amount").toString()
        }, { error ->
            Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
        })
        rq.add(jar)
        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnPayment = findViewById<Button>(R.id.btnPayment)
        btnExit.setOnClickListener{
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        btnPayment.setOnClickListener{
            var i = Intent(this, Payment::class.java)
            i.putExtra("username",txtLoginUser)
            i.putExtra("name", txtName.text)
            i.putExtra("surname", txtSurname.text)
            startActivity(i)
        }
    }

}



