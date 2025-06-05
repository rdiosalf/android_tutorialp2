package com.alten.androidtut.firstApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alten.androidtut.R

class FirstAppActivity : AppCompatActivity() {

  companion object{
    const val TAG="###"
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_first_app)
    val btnStart=findViewById<AppCompatButton>(R.id.btnStart)
    val editTextName=findViewById<AppCompatEditText>(R.id.etName)
    btnStart.setOnClickListener{
      val name =editTextName.text.toString()
      if(name.isNotEmpty()){
        Log.i(TAG,"Botón pulsado ${editTextName.text.toString()}")
        //lanzamiento de intent para poder navegar a segunda pantalla
        val intent=Intent(this,ResultActivity::class.java )
        intent.putExtra("EXTRA_NAME",name)
        startActivity(intent)
      }

    }


    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }
}