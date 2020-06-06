package me.bristermitten.ppm.entity.meta

import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.entity.Version

data class LocalPackageMetadata(
        val dependencies: Set<Package>,
        val softDependencies: Set<Package>,
        val version: Version,
        val name: String
)


data class PluginYMLMetadata(
        var depend: Set<String> = emptySet(),
        var softDepend: Set<String> = emptySet(),
        var version: String = "",
        var name: String = ""
)
