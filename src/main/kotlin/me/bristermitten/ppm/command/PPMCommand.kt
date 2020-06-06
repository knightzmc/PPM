package me.bristermitten.ppm.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import com.google.inject.Inject
import me.bristermitten.ppm.repository.PackageRepository
import me.bristermitten.ppm.service.PackageManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("ppm")
class PPMCommand @Inject constructor(
        private val repository: PackageRepository,
        private val packageManager: PackageManager
) : BaseCommand()
{
    @Subcommand("si|searchinstall")
    fun install(sender: CommandSender, packageName: String)
    {
        val found = repository.searchByName(packageName).execute().body()
        val first = found?.firstOrNull()
        if (first == null)
        {
            sender.sendMessage("${ChatColor.RED}No package found with name $packageName")
            return
        }

        sender.sendMessage("${ChatColor.GREEN}Installing $packageName...")
        packageManager.installPackage(first)
        sender.sendMessage("${ChatColor.GREEN}Done!")
    }

    @Subcommand("i|install")
    fun install(sender: CommandSender, packageId: Long)
    {
        val found = repository.searchById(packageId).execute().body()
        if (found == null)
        {
            sender.sendMessage("${ChatColor.RED}No package found with ID $packageId")
            return
        }

        sender.sendMessage("${ChatColor.GREEN}Installing ${found.name}...")
        packageManager.installPackage(found)
        sender.sendMessage("${ChatColor.GREEN}Done!")
    }
}
