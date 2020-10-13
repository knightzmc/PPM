package me.bristermitten.ppm.repository

import me.bristermitten.ppm.entity.spigot.SpigotPackage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SpigetPackageRepository : PackageRepository
{
	@get:GET("resources?size=10000?fields=id,tag,author,description,category")
	override val getAll: Call<Set<SpigotPackage>>

	@GET("resources/{resource}")
	override fun searchById(@Path("resource") id: Long): Call<SpigotPackage?>

	@GET("search/resources/{query}?field=name")
	override fun searchByName(@Path("query") name: String): Call<Set<SpigotPackage>>

	@GET("resources/{resource}/download")
	override fun getPackageContents(@Path("resource") id: Long): Call<ResponseBody>
}
