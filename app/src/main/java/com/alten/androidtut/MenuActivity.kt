package com.alten.androidtut

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alten.androidtut.firstApp.FirstAppActivity
import com.alten.androidtut.imccalculator.ImcCalculatorActivity
import com.alten.androidtut.todoapp.TodoActivity

class MenuActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_menu)

    val btnSaludApp = findViewById<Button>(R.id.btnSaludApp)
    btnSaludApp.setOnClickListener { navigateToSaludApp() }

    val btnIMCApp = findViewById<Button>(R.id.btnIMCApp)
    btnIMCApp.setOnClickListener { navigateToIMCApp() }

    val btnTodoApp = findViewById<Button>(R.id.btnTodoApp)
    btnTodoApp.setOnClickListener { navigateToTodoApp() }


    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }

  private fun navigateToSaludApp() {
    val intent = Intent(this, FirstAppActivity::class.java)
    startActivity(intent)
  }

  private fun navigateToIMCApp() {
    val intent = Intent(this, ImcCalculatorActivity::class.java)
    startActivity(intent)
  }

  private fun navigateToTodoApp() {
    val intent = Intent(this, TodoActivity::class.java)
    startActivity(intent)
  }


}