package me.bristermitten.ppm.inject

import me.bristermitten.ppm.repository.SpigetPackageRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryFactory
{

    private val retrofit by lazy {
        val factory = GsonConverterFactory.create()

        Retrofit.Builder()
                .baseUrl("https://api.spiget.org/v2/")
                .addConverterFactory(factory)
                .build()
    }

    fun createSpigetRepo(): SpigetPackageRepository
    {
        return retrofit.create(SpigetPackageRepository::class.java)
    }
}
