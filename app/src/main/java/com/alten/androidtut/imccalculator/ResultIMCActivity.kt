package com.alten.androidtut.imccalculator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alten.androidtut.R
import com.alten.androidtut.firstApp.FirstAppActivity.Companion.TAG
import com.alten.androidtut.imccalculator.ImcCalculatorActivity.Companion.IMC_KEY


private lateinit var tvResult: TextView
private lateinit var tvIMC: TextView
private lateinit var tvDescription: TextView
private lateinit var btnRecalculate: Button


class ResultIMCActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_result_imcactivity)
    //llamada al intent de la propia pantalla y si tiene algun extra - por eso lo de ? - dame algun string que tenga como key
    // el identificdor IMC_RESULT y si no la hay que devuelva un string -1.0
   // val result=intent.extras?.getString("IMC_RESULT") ?: "-1.0" ---- IMC_RESULT lo voy a definir como un companion object
    val result: Double= (intent.extras?.getDouble(IMC_KEY) ?: "-1.0") as Double
    Log.i("###", "result de la otra pantalla me viene con $result")


    initComponents()
    initUI(result)
    initListeners()

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }


  private fun initComponents(){
    tvResult=findViewById(R.id.tvResult)
    tvDescription=findViewById(R.id.tvDescription)
    tvIMC=findViewById(R.id.tvIMC)
    btnRecalculate=findViewById(R.id.btnReCalculate)
  }


  private fun initUI(result: Double){
    tvIMC.text=result.toString()

    when(result){
      in 0.00..18.50 -> {
        Log.i(TAG,"BAJO PESO ")
        tvResult.text=getString(R.string.title_low_weight)
        //establecer color al texto
        tvResult.setTextColor(ContextCompat.getColor(this,R.color.low_weight))
        tvDescription.text=getString(R.string.description_low_weight)
      }
      in 18.51 ..24.99 ->{
        Log.i(TAG,"PESO NORMAL")
        tvResult.text=getString(R.string.title_normal_weight)
        tvDescription.text=getString(R.string.description_normal_weight)

      }
      in 25.00 ..29.99->{
        Log.i(TAG,"SOBREPESO")
        tvResult.text=getString(R.string.title_over_weight)
        tvResult.setTextColor(ContextCompat.getColor(this,R.color.over_weight))
        tvDescription.text=getString(R.string.description_over_weight)

      }
      in 30.00..99.00->{
        Log.i(TAG,"OBESIDAD")
        tvResult.text=getString(R.string.title_obesity_weight)
        tvResult.setTextColor(ContextCompat.getColor(this,R.color.obesity_weight))
        tvDescription.text=getString(R.string.description_obesity_weight)

      }
      else->{
        Log.i(TAG,"ERROR")
        tvIMC.text=getString(R.string.error)
        tvResult.text=getString(R.string.error)
        tvDescription.text=getString(R.string.error)
      }

    }

  }

  private fun initListeners(){
    btnRecalculate.setOnClickListener {
      onBackPressed()
    }

  }


}