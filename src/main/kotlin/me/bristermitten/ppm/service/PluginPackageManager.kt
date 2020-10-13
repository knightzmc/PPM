package me.bristermitten.ppm.service

import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.entity.meta.LocalMetadataExtractor
import org.bukkit.Bukkit

class PluginPackageManager(
        private val packageDownloader: PackageDownloader,
        private val metadataExtractor: LocalMetadataExtractor
) : PackageManager
{
	override fun installPackage(toDownload: Package)
	{
		val downloadedPackage = packageDownloader.download(toDownload)
		val metadata = metadataExtractor.extractMetadata(downloadedPackage, this)

		val renamedFile = downloadedPackage.parentFile.resolve("${metadata.name}-${metadata.version.versionString}.jar")
		downloadedPackage.copyTo(renamedFile, true)
		downloadedPackage.delete()


		val pm = Bukkit.getPluginManager()

		val plugin = pm.getPlugin(metadata.name) ?: pm.loadPlugin(renamedFile) ?: return
		plugin.onLoad()
		pm.enablePlugin(plugin)
	}
}
