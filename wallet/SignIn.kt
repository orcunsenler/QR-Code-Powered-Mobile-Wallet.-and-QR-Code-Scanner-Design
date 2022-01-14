package com.example.owallet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*

class SignIn : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val btnsignin = findViewById<Button>(R.id.btnSignIn)
        val txtName = findViewById<EditText>(R.id.txtSignInName)
        val txtSurname = findViewById<EditText>(R.id.txtSignInSurname)
        val txtUsername = findViewById<EditText>(R.id.txtSignInUsername)
        val txtpassword = findViewById<EditText>(R.id.txtSignInPassword)
        val cash = 100

        btnsignin.setOnClickListener{
            val namesurname:String = txtName.text.toString()+txtSurname.text.toString()
            val encrypted = Base64.getEncoder().encodeToString(namesurname.toByteArray())
            val url = "http://192.168.43.215/wallet/register.php?name="+txtName.text.toString()+"&surname="+
                    txtSurname.text.toString()+"&username="+txtUsername.text.toString()+"&password="+txtpassword.text.toString()+
                    "&amount="+cash.toString()+"&encrypted="+encrypted.toString()
            val rq:RequestQueue=Volley.newRequestQueue(this)
            val sq = StringRequest(Request.Method.GET,url, { response ->
                if(response.equals("0"))
                    Toast.makeText(this,"Name Already Exists", Toast.LENGTH_LONG).show()
                else if(response.equals("1")){
                    Toast.makeText(this, "User Created", Toast.LENGTH_LONG).show()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
            }, { error ->
                Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
            })
            rq.add(sq)
        }
    }
}








