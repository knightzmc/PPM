package me.bristermitten.ppm.entity.meta

import com.google.inject.Inject
import me.bristermitten.ppm.entity.NormalVersion
import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.entity.UnparsableVersion
import me.bristermitten.ppm.repository.PackageRepository
import me.bristermitten.ppm.service.PackageManager
import java.io.File

class PluginYMLMetadataExtractor @Inject constructor(
        private val pluginYMLMetadataParser: PluginYMLMetadataParser,
        private val packageRepository: PackageRepository,
        private val packageManager: PackageManager
) : LocalMetadataExtractor
{
    override fun extractMetadata(pluginFile: File): LocalPackageMetadata
    {
        val meta = pluginYMLMetadataParser.extractMetadata(pluginFile)

        val dependencies = downloadDependencies(meta.name, meta.depend)
        val softDependencies = downloadDependencies(meta.name, meta.softDepend)

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


    private fun downloadDependencies(source: String, dependencies: Set<String>): Set<Package>
    {
        return dependencies.mapNotNull {
            val byName = packageRepository.searchByName(it).execute().body()
            if (byName.isNullOrEmpty())
            {
                println("Warning: Plugin $source references a dependency that couldn't be found - $it")
                return@mapNotNull null
            }

            val matchingDependency = byName.first()
            packageManager.installPackage(matchingDependency)
            println("Downloaded dependency ${matchingDependency.name}")

            matchingDependency
        }.toSet()
    }
}
