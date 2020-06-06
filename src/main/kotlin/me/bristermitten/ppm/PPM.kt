package me.bristermitten.ppm

import co.aikar.commands.PaperCommandManager
import com.google.inject.Guice
import dev.misfitlabs.kotlinguice4.getInstance
import me.bristermitten.ppm.command.PPMCommand
import me.bristermitten.ppm.inject.PPMModule
import me.bristermitten.ppm.inject.RepositoryFactory
import org.bukkit.plugin.java.JavaPlugin

class PPM : JavaPlugin()
{
    override fun onEnable()
    {
        val pluginsDirectory = dataFolder.parentFile

        val repo = RepositoryFactory().createSpigetRepo()
        val injector = Guice.createInjector(PPMModule(this, repo, pluginsDirectory))

        val manager = PaperCommandManager(this)
        manager.registerCommand(injector.getInstance<PPMCommand>())

    }

    override fun onDisable()
    {
        // Plugin shutdown logic
    }
}
