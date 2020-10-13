package me.bristermitten.ppm.service

import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.repository.PackageRepository
import java.io.File
import java.util.*

class DefaultPackageDownloader(
		private val repo: PackageRepository,
		private val packageDir: File
) : PackageDownloader
{
	override fun download(toDownload: Package): File
	{
		val downloadResponse = repo.getPackageContents(toDownload.id).execute()

		val fileName = UUID.randomUUID().toString()
		val response = downloadResponse.body()
				?: throw IllegalArgumentException("$toDownload did not provide a response body.")


		val file = packageDir.resolve(fileName)
		val out = file.outputStream()

		out.use {
			it.write(response.bytes())
		}
		return file
	}
}
