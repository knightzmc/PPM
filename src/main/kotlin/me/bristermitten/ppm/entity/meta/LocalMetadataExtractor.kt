package me.bristermitten.ppm.entity.meta

import me.bristermitten.ppm.service.PackageManager
import java.io.File

interface LocalMetadataExtractor
{
    fun extractMetadata(pluginFile: File, packageManager: PackageManager): LocalPackageMetadata
}
