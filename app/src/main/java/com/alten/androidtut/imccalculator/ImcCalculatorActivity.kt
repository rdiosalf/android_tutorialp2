package com.alten.androidtut.imccalculator

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alten.androidtut.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import android.util.Log
import com.alten.androidtut.firstApp.FirstAppActivity.Companion.TAG

class ImcCalculatorActivity : AppCompatActivity() {

  private lateinit var viewMale: CardView
  private lateinit var viewFemale: CardView
  private lateinit var tvHeight: TextView
  private lateinit var rsHeight: RangeSlider
  private lateinit var btnSubstractWeight: FloatingActionButton
  private lateinit var btnPlusWeight: FloatingActionButton
  private lateinit var tvWeight: TextView
  private lateinit var btnSubstractAge: FloatingActionButton
  private lateinit var btnPlusAge: FloatingActionButton
  private lateinit var tvAge: TextView
  private lateinit var btnCalculate: Button


  private var isMaleSelected: Boolean = true //x def  hombre esta pulsado
  private var isFemaleSelected: Boolean = false //x def  hombre esta pulsado

  private var currentWeight: Int = 60 //peso x defecto
  private var currentAge: Int = 18 //edad x defecto
  private var currentHeight: Int = 120

  companion object{
    const val IMC_KEY="IMC_RESULT"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_imc_calculator)
    initComponents()
    initListeners()
    initUI()
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

  }


  /**
   * inicializa los view encontrando el id
   */
  private fun initComponents() {
    viewMale = findViewById(R.id.viewMale)
    viewFemale = findViewById(R.id.viewFemale)
    tvHeight = findViewById(R.id.tvHeight)
    rsHeight = findViewById(R.id.rsHeight)

    btnSubstractWeight = findViewById(R.id.btnSubstractWeight)
    btnPlusWeight = findViewById(R.id.btnPlusWeight)
    tvWeight = findViewById(R.id.tvWeight)

    btnSubstractAge = findViewById(R.id.btnSubstractAge)
    btnPlusAge = findViewById(R.id.btnPlusAge)
    tvAge = findViewById(R.id.tvAge)

    btnCalculate = findViewById(R.id.btnCalculate)

  }


  /**
   * inicializa los listeners de clicar
   * para los view de antes y hará la accion que sea cuando se clique
   * acción 1 - cambia de seleccionado ano seleccionado y viceversa
   * acción 2- cambia el color
   */
  private fun initListeners() {
    viewMale.setOnClickListener({
      changeGender()
      setGenderColor()
    })
    viewFemale.setOnClickListener({
      changeGender()
      setGenderColor()
    })

    //el slider tiene movimiento y a medida q muevo tiene que mostrarme el valor arriba que quiero poner
    //o sea si muevo un poco que ponga 126...140....
    rsHeight.addOnChangeListener({ _, value, _ ->
      val decimformat = DecimalFormat("#.##")
      currentHeight = decimformat.format(value).toInt()
      tvHeight.text = "$currentHeight cm"
    })


    //listener de los dos botones + - del peso
    btnPlusWeight.setOnClickListener({
      currentWeight += 1 //va sumando en la variable cada vez q se pulse el botón ojo que no es la variable que pinta
      setWeight()
    })
    btnSubstractWeight.setOnClickListener({
      currentWeight -= 1
      setWeight()
    })

    //listener de los botones de la edad
    btnPlusAge.setOnClickListener({
      currentAge += 1 //va sumando en la variable cada vez q se pulse el botón ojo que no es la variable que pinta
      setAge()
    })
    btnSubstractAge.setOnClickListener({
      currentAge -= 1
      setAge()
    })


    //listener del boton calcular
    btnCalculate.setOnClickListener({
      val result=calculateIMC()
      navigateToResult(result)
    })

  }


  private fun navigateToResult(result: Double){
    val intent= Intent(this, ResultIMCActivity::class.java)
    //le paso a la sig vista el valor con un putextra
   // intent.putExtra("IMC_RESULT",result) para evitar hardcoding y hacerla accesible a todo la tengo arriba como companion object
    intent.putExtra(IMC_KEY,result)
    startActivity(intent)
  }

  //funcion que le pasa a la variable a mostrar el nuevo peso
  private fun setWeight() {
    tvWeight.text = currentWeight.toString()
  }


  private fun setAge() {
    tvAge.text = currentAge.toString()
  }


  private fun calculateIMC(): Double {
    val df:DecimalFormat=DecimalFormat("#.##")
    val currentHeightInMeters:Double=currentHeight.toDouble()/100 //para iMC necesito
    val imc = (currentWeight / (currentHeightInMeters*currentHeightInMeters))
    val imcResult =df.format(imc).replace(",", ".").toDouble()
    Log.i(TAG, "el IMC es ${imcResult}")
    return imcResult
  }

  /**inicializa la ui con unos colores
   * si no se inicializa no se ve la primera vez
   * */
  private fun initUI() {
    setGenderColor()
    setWeight()//la primera vez muestra el de x defecto
    setAge()
  }

  private fun changeGender() {
    isMaleSelected = !isMaleSelected
    isFemaleSelected = !isFemaleSelected

  }

  /**
   * cuando clique en la card cambiaré el color para ver que está clicado
   */
  private fun setGenderColor() {
    viewMale.setCardBackgroundColor(getBackGroundColor(isMaleSelected))
    viewFemale.setCardBackgroundColor(getBackGroundColor(isFemaleSelected))
  }


  private fun getBackGroundColor(isSelectedComponent: Boolean): Int {
    //asigno el color si está o no selecconado
    val colorReference = if (isSelectedComponent) {
      R.color.background_component_selected
    } else {
      R.color.background_component
    }
    //devuelvo el color
    return ContextCompat.getColor(this, colorReference)
  }

}