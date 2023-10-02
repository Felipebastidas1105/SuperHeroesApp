package com.example.searchsuperheroesapp

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
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
import kotlin.math.roundToInt

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
        prepareStats(superHero.powerStats)
        binding.tvsuperHeroRealName.text = superHero.biography.fullName
        binding.tvPublisher.text = superHero.biography.publisher
    }

    private fun prepareStats(powerStats: PowerStatsResponse) {
        updateHeight(binding.viewCombat, powerStats.combat)
        updateHeight(binding.viewDurability, powerStats.durability)
        updateHeight(binding.viewSpeed, powerStats.speed)
        updateHeight(binding.viewStrength, powerStats.strength)
        updateHeight(binding.viewIntelligence, powerStats.intelligence)
        updateHeight(binding.viewPower, powerStats.power)
    }
    private fun updateHeight(view: View, height: String) {
        val params = view.layoutParams
        params.height = pixelToDp(height.toFloat())
        view.layoutParams = params
    }

    private fun pixelToDp(pixel: Float):Int {
       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, resources.displayMetrics).roundToInt()
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}