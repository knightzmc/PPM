package me.bristermitten.ppm.entity.meta

import me.bristermitten.ppm.entity.NormalVersion
import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.entity.UnparsableVersion
import me.bristermitten.ppm.repository.PackageRepository
import me.bristermitten.ppm.service.PackageManager
import me.bristermitten.ppm.util.distanceTo
import java.io.File

class PluginYMLMetadataExtractor(
		private val pluginYMLMetadataParser: PluginYMLMetadataParser,
		private val packageRepository: PackageRepository
) : LocalMetadataExtractor
{
	override fun extractMetadata(pluginFile: File, packageManager: PackageManager): LocalPackageMetadata
	{
		val meta = pluginYMLMetadataParser.extractMetadata(pluginFile)

		val dependencies = downloadDependencies(meta.name, meta.depend, packageManager)
		val softDependencies = downloadDependencies(meta.name, meta.softDepend, packageManager)

		val version = meta.version
		val versionData = if (!version.matches("^(\\d+\\.)?(\\d+\\.)?(\\*|\\d+)\$".toRegex()))
		{
			UnparsableVersion(version)
		} else
		{
			val splitVersion = version.split('.')
			val major = splitVersion[0].toInt()
			val minor = splitVersion[1].toInt()
			val patch = splitVersion.getOrNull(2)?.toIntOrNull() ?: 0

			NormalVersion(major, minor, patch)
		}

		return LocalPackageMetadata(dependencies, softDependencies, versionData, meta.name)
	}


	private fun downloadDependencies(source: String, dependencies: Set<String>, packageManager: PackageManager): Set<Package>
	{
		return dependencies.mapNotNull {
			val byName = packageRepository.searchByName(it).execute().body()
			
			val matchingDependency = byName?.minBy { dependency ->
				dependency.name.distanceTo(it)
			}

			if (matchingDependency == null)
			{
				println("Warning: Plugin $source references a dependency that couldn't be found - $it")
				return@mapNotNull null
			}


			packageManager.installPackage(matchingDependency)
			println("Downloaded dependency ${matchingDependency.name}")

			matchingDependency
		}.toSet()
	}
}
