package com.alten.androidtut.superheroapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.alten.androidtut.R
import com.alten.androidtut.databinding.ActivitySuperHeroListBinding
import com.alten.androidtut.firstApp.FirstAppActivity
import com.alten.androidtut.superheroapp.DetailSuperheroActivity.Companion.EXTRA_ID
import com.alten.androidtut.todoapp.CategoriesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroListActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySuperHeroListBinding
  private lateinit var retrofit: Retrofit
  private lateinit var adapter: SuperHeroAdapter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivitySuperHeroListBinding.inflate(layoutInflater)
    setContentView(binding.root)
    retrofit=getRetrofit()

    /**con binding ya llego a cualquier cosa del xml
    binding.svSearchView
    binding.rvSuperHero*/
    initUI()

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }

  private fun initUI() {
    binding.svSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        searchByName(query.orEmpty())
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        //no nos interesa la dejo implementada
        return false
      }

    })
    //creacion del adapter
      adapter = SuperHeroAdapter(){
        superheroId -> navigateToDetail(superheroId)
      }
      binding.rvSuperHero.setHasFixedSize(true)
      binding.rvSuperHero.layoutManager = LinearLayoutManager(this)
      binding.rvSuperHero.adapter = adapter
    }

  private fun searchByName(query: String) {
    binding.progressBar.isVisible = true //false se va a gone
    CoroutineScope(Dispatchers.IO).launch {
      val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperheroes(query)
      if (myResponse.isSuccessful){
        Log.i(FirstAppActivity.TAG,"conecta y busca ")
        val response:SuperHeroDataResponse?=myResponse.body()
        if (response!=null){
          Log.i(FirstAppActivity.TAG,"responde <todo el churro q tenga definido en el response>: ${response.toString()}")
          runOnUiThread {
            binding.progressBar.isVisible = false
            adapter.updateList(response.superheros)
          }

        }
      }else{
        Log.i(FirstAppActivity.TAG,"no funciona")
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


  private fun navigateToDetail(id:String){
    val intent = Intent(this, DetailSuperheroActivity::class.java)
    intent.putExtra(EXTRA_ID, id)
    startActivity(intent)
  }
}