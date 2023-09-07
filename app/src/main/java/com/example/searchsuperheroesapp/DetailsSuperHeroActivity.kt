package com.example.searchsuperheroesapp

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.searchsuperheroesapp.databinding.ActivityDetailsSuperHeroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.zip.Inflater

class DetailsSuperHeroActivity : AppCompatActivity() {

    companion object{
        const val ExtraId = "ExtraId"
    }

    private lateinit var binding: ActivityDetailsSuperHeroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: String = intent.getStringExtra(ExtraId).orEmpty()
        getSuperHeroInformation(id)
    }

    private fun getSuperHeroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch{//Todo lo que se haga entre estas llaves se procesara en un hilo secundario
            val superHeroDetail = getRetrofit().create(ApiService::class.java).getSuperHeroDetails(id)
            if (superHeroDetail.body() !=  null){
                runOnUiThread{createUI(superHeroDetail.body()!!)}
            }
        }
    }

    private fun createUI(superHero: SuperHeroDetailsResponse) {
        Picasso.get().load(superHero.superHeroImg.url).into(binding.ivSuperHero)
        binding.tvsuperHeroName.text = superHero.name
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}