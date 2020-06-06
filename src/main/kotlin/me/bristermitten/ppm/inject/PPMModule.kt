package me.bristermitten.ppm.inject

import dev.misfitlabs.kotlinguice4.KotlinModule
import me.bristermitten.ppm.PPM
import me.bristermitten.ppm.entity.meta.LocalMetadataExtractor
import me.bristermitten.ppm.entity.meta.PluginYMLMetadataExtractor
import me.bristermitten.ppm.repository.PackageRepository
import me.bristermitten.ppm.repository.SpigetPackageRepository
import me.bristermitten.ppm.service.DefaultPackageDownloader
import me.bristermitten.ppm.service.PackageDownloader
import me.bristermitten.ppm.service.PackageManager
import me.bristermitten.ppm.service.PluginPackageManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class PPMModule(
        private val ppm: PPM,
        private val repo: SpigetPackageRepository,
        private val packageDirectory: File
) : KotlinModule()
{
    override fun configure()
    {
        bind<PPM>().toInstance(ppm)
        bind<JavaPlugin>().to<PPM>()
        bind<File>().annotatedWith<PackageDir>().toInstance(packageDirectory)
        bind<PackageRepository>().toInstance(repo)
        bind<PackageDownloader>().to<DefaultPackageDownloader>()
        bind<PackageManager>().to<PluginPackageManager>()
        bind<LocalMetadataExtractor>().to<PluginYMLMetadataExtractor>()
    }
}
