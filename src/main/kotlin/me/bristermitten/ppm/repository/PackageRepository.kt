package me.bristermitten.ppm.repository

import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.entity.PackageDownload
import me.bristermitten.ppm.entity.meta.LocalPackageMetadata
import okhttp3.ResponseBody
import retrofit2.Call

interface PackageRepository
{
    val getAll: Call<out Set<Package>>

    fun searchById(id: Long): Call<out Package?>

    fun searchByName(name: String): Call<out Set<Package>>

    fun getMetadata(packageID: Long) : Call<LocalPackageMetadata>

    fun getPackageContents(id: Long) : Call<ResponseBody>
}
