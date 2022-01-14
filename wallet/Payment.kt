package com.example.owallet
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.util.*
class Payment : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val txtName = intent.getStringExtra("name")
        val txtSurname = intent.getStringExtra("surname")
        val txtUser = intent.getStringExtra("username")
        val nameAndsurname = txtName+txtSurname
        val encodedata = Base64.getEncoder().encodeToString(nameAndsurname.toByteArray())
        val testText = findViewById<TextView>(R.id.testText)
        testText.text = encodedata
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(testText.text.toString(), BarcodeFormat.QR_CODE, width,height)
            for(x in 0 until width) {
                for (y in 0 until height){
                    bitmap.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
        }catch(e: WriterException){
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }
        val qrCode =findViewById<ImageView>(R.id.qrCode)
        qrCode.setImageBitmap(bitmap)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener{
            var i = Intent(this, ApplicationScreen::class.java)
            i.putExtra("username",txtUser)
            startActivity(i)
        }

    }

}


