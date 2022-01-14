package com.example.owallet
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnMainSignIn = findViewById<Button>(R.id.btnMainSignIn)
        btnMainSignIn.setOnClickListener{
            val signin = Intent(this,SignIn::class.java)
            startActivity(signin)
        }
        val btnMainLogin = findViewById<Button>(R.id.btnMainLogin)
        val txtMainusername = findViewById<EditText>(R.id.txtLoginUsername)
        val txtMainpassword = findViewById<EditText>(R.id.txtLoginPassword)
        btnMainLogin.setOnClickListener{
            var url = "http://192.168.43.215/wallet/login.php?username="+txtMainusername.text.toString()+
                    "&password="+ txtMainpassword.text.toString()
            var rq: RequestQueue = Volley.newRequestQueue(this)
            var sq = StringRequest(Request.Method.GET,url, { response ->
                if(response.equals("1"))
                    Toast.makeText(this,"Connection Failed", Toast.LENGTH_LONG).show()
                else if(response.equals("2"))
                    Toast.makeText(this,"Name Check Query Failed", Toast.LENGTH_LONG).show()
                else if(response.equals("5"))
                    Toast.makeText(this,"Either no user with name, or more than one", Toast.LENGTH_LONG).show()
                else if(response.equals("6"))
                    Toast.makeText(this,"Incorrect Password", Toast.LENGTH_LONG).show()
                else if(response.equals("0")) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                    var i = Intent(this, ApplicationScreen::class.java)
                    i.putExtra("username", txtMainusername.text.toString())
                    startActivity(i)
                }
            }, { error ->
                Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
            })
            rq.add(sq)

        }
    }
}