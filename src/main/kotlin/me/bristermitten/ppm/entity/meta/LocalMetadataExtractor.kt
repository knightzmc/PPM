package me.bristermitten.ppm.entity.meta

import java.io.File

interface LocalMetadataExtractor
{
    fun extractMetadata(pluginFile: File): LocalPackageMetadata
}
