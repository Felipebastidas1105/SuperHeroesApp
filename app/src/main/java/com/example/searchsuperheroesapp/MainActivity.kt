package com.example.searchsuperheroesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchsuperheroesapp.DetailsSuperHeroActivity.Companion.ExtraId
import com.example.searchsuperheroesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //This is the viewBinding
    private lateinit var retrofit:Retrofit

    private lateinit var adapter:SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { //Esta funcion se activara automaticamente cuando se pulse el boton de buscar
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false //Esta funcion se activara automaticamente cuando estemos escribiendo
        })
        adapter = SuperHeroAdapter{navigateToDetails(it)}
        binding.rvSuperHeroes.setHasFixedSize(true)
        binding.rvSuperHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvSuperHeroes.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        // Lanza una corrutina ya sea en el hilo prinicipal o en un hilo secundario ya sea para peticiones, para guardar en base de datos
        // o para procesos muy largos que tardarian en progresar
        CoroutineScope(Dispatchers.IO).launch{//Todo lo que se haga entre estas llaves se procesara en un hilo secundario
            //Response es una funcion de retrofit que nos da algunas cosas extras
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperheroes(query) //Con retrofit creame esta interface (ApiService) que esta preparada
            if (myResponse.isSuccessful){
                val response: SuperHeroDataResponse? = myResponse.body() //Dentro del body es donde esta la respuesta

                if (response!= null){
                    Log.i("Bastidas", response.toString())
                    runOnUiThread{
                        adapter.updateList(response.superHeroes)
                        binding.progressBar.isVisible = false
                    }
                }else{
                    //Toast.makeText(this@MainActivity, "not found the superhero", Toast.LENGTH_SHORT).show()
                }
            }else{
                Log.i("Bastidas", "No funciona :(")
            }
        }

    }

    private fun getRetrofit():Retrofit{
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun navigateToDetails(Id:String){
        val intent = Intent(this,DetailsSuperHeroActivity::class.java)
        intent.putExtra(ExtraId,Id)
        startActivity(intent)
    }
}