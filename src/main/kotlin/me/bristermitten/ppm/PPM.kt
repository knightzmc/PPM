package me.bristermitten.ppm

import co.aikar.commands.PaperCommandManager
import me.bristermitten.ppm.command.PPMCommand
import me.bristermitten.ppm.entity.meta.PluginYMLMetadataExtractor
import me.bristermitten.ppm.entity.meta.PluginYMLMetadataParser
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

		val parser = PluginYMLMetadataParser(this)
		val downloader = DefaultPackageDownloader(repo, pluginsDirectory)
		val metadataExtractor = PluginYMLMetadataExtractor(parser, repo)
		val packageManager = PluginPackageManager(downloader, metadataExtractor)



		val manager = PaperCommandManager(this)
		manager.enableUnstableAPI("help")
		val command = PPMCommand(repo, packageManager)
		manager.registerCommand(command)

	}
}
