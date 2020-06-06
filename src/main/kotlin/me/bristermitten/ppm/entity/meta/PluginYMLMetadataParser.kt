package me.bristermitten.ppm.entity.meta

import com.google.inject.Inject
import org.bukkit.plugin.java.JavaPlugin
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor
import org.yaml.snakeyaml.representer.Representer
import java.io.File
import java.util.jar.JarFile

class PluginYMLMetadataParser @Inject constructor(
        plugin: JavaPlugin
)
{

    private val yaml = Yaml(
            CustomClassLoaderConstructor(LocalPackageMetadata::class.java, plugin.javaClass.classLoader),
            Representer().apply {
                propertyUtils.isSkipMissingProperties = true
            })

    fun extractMetadata(pluginFile: File): PluginYMLMetadata
    {
        val jar = JarFile(pluginFile)
        val pluginYML = jar.getJarEntry("plugin.yml")
                ?: throw IllegalArgumentException("File $pluginFile does not contain plugin.yml")

        val yamlData = jar.getInputStream(pluginYML).reader().readText()
        return yaml.loadAs(yamlData, PluginYMLMetadata::class.java)
    }

}
