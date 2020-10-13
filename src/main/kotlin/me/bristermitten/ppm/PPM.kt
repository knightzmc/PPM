package me.bristermitten.ppm

import co.aikar.commands.PaperCommandManager
import com.google.inject.Guice
import dev.misfitlabs.kotlinguice4.getInstance
import me.bristermitten.ppm.command.PPMCommand
import me.bristermitten.ppm.entity.meta.PluginYMLMetadataExtractor
import me.bristermitten.ppm.inject.PPMModule
import me.bristermitten.ppm.inject.RepositoryFactory
import me.bristermitten.ppm.service.DefaultPackageDownloader
import me.bristermitten.ppm.service.PluginPackageManager
import org.bukkit.plugin.java.JavaPlugin

class PPM : JavaPlugin()
{
    override fun onEnable()
    {
        val pluginsDirectory = dataFolder.parentFile

        val repo = RepositoryFactory().createSpigetRepo()
        val injector = Guice.createInjector(PPMModule(this, repo, pluginsDirectory))

//        val metadataExtractor = PluginYMLMetadataExtractor()
        val downloader = DefaultPackageDownloader(repo ,pluginsDirectory )
//        val packageManager = PluginPackageManager(downloader)
        val manager = PaperCommandManager(this)S
        val command = PPMCommand(repo, packageManager)
        manager.registerCommand(command)

    }
}
