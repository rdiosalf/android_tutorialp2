package com.alten.androidtut.superheroapp

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.alten.androidtut.R
import com.alten.androidtut.databinding.ActivityDetailSuperheroBinding
import com.alten.androidtut.databinding.ActivitySuperHeroListBinding
import com.alten.androidtut.firstApp.FirstAppActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {


  companion object{
    const val EXTRA_ID = "extra_id"
  }

  private lateinit var binding: ActivityDetailSuperheroBinding
  private lateinit var retrofit: Retrofit

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
    setContentView(binding.root)
    retrofit=getRetrofit()

    val id:String =intent.getStringExtra(EXTRA_ID).orEmpty()
    getSuperHeroInformation(id)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }


  private fun getSuperHeroInformation(id:String){
    //---------------binding.progressBar.isVisible = true //false se va a gone
    CoroutineScope(Dispatchers.IO).launch {
      val superheroDetailResponse: Response<SuperHeroDetailResponse> = retrofit.create(ApiService::class.java).getSuperheroDetail(id)
      if (superheroDetailResponse.isSuccessful){
        Log.i(FirstAppActivity.TAG,"conecta y obtiene detalle por id ")
        val response:SuperHeroDetailResponse?=superheroDetailResponse.body()
        if (response!=null){
          Log.i(FirstAppActivity.TAG,"responde <todo el churro q tenga definido en el response>: ${response.toString()}")
          runOnUiThread {
            //pintar UI con los datos del superheroe
            createUIDetail(response)
          }

        }
      }else{
        Log.i(FirstAppActivity.TAG,"no funciona getSuperHeroDetail")
      }
    }
  }

  private fun getRetrofit(): Retrofit {
    val retrofit = Retrofit
      .Builder()
      .baseUrl("https://superheroapi.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    return retrofit
  }

  private fun createUIDetail(info: SuperHeroDetailResponse){
    Picasso.get().load(info.image.url).into(binding.ivSuperhero)
    binding.tvSuperheroName.text=info.name
    prepareStats(info.powerstats)
    binding.tvSuperheroRealName.text=info.biography.fullName
    binding.tvPublisher.text=info.biography.publisher
  }

  /**modifica las alturas que le voy a pasar a la vista */
  private fun prepareStats(powerstats: PowerStarsResponse){
   /* seria hacer esto para cada uno de ellos
    val params =binding.viewCombat.layoutParams //todos los parámetros asociados a la view esa
    params.height = powerstats.combat.toInt()
    binding.viewCombat.layoutParams=params*/
    updateHeight(binding.viewIntelligence,powerstats.intelligence)
    updateHeight(binding.viewStrength,powerstats.strength)
    updateHeight(binding.viewSpeed,powerstats.speed)
    updateHeight(binding.viewDurability,powerstats.durability)
    updateHeight(binding.viewPower,powerstats.power)
    updateHeight(binding.viewCombat,powerstats.combat)

  }

  private fun updateHeight(view:View,stat:String){
    Log.i(FirstAppActivity.TAG,"updateHeight para view: ${view.id} $stat")
    val params =view.layoutParams //todos los parámetros asociados a la view esa
    params.height = pixelToDp(stat.toFloat())
    view.layoutParams=params
  }

  /**func que pasa de px a dp en funcion de tamaño pantalla - displayMetrics*/
  private fun pixelToDp(pixel: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,pixel,resources.displayMetrics).roundToInt()
  }

}