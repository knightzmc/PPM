package me.bristermitten.ppm.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import me.bristermitten.ppm.repository.PackageRepository
import me.bristermitten.ppm.service.PackageManager
import me.bristermitten.ppm.util.distanceTo
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("ppm")
class PPMCommand(
		private val repository: PackageRepository,
		private val packageManager: PackageManager
) : BaseCommand()
{
	@Default
	fun default()
	{
		showCommandHelp()
	}

	@Subcommand("search|s")
	@Description("Search for plugins by name")
	fun search(sender: CommandSender, packageName: String)
	{
		val found = repository.searchByName(packageName).execute().body()
		if (found == null)
		{
			sender.sendMessage("${ChatColor.RED}No packages found with name $packageName")
			return
		}

		found.sortedBy {
			it.name.distanceTo(packageName)
		}.forEach {
			sender.sendMessage("${ChatColor.GREEN}${it.id}. ${it.name} - ${it.description}")
		}
	}

	@Subcommand("searchinstall|si")
	@Description("Search for plugins by name, and install the first matching one")
	fun install(sender: CommandSender, packageName: String)
	{
		val found = repository.searchByName(packageName).execute().body()
		val first = found?.minBy {
			it.name.distanceTo(packageName)
		}
		if (first == null)
		{
			sender.sendMessage("${ChatColor.RED}No package found with name $packageName")
			return
		}

		sender.sendMessage("${ChatColor.GREEN}Installing $packageName...")
		packageManager.installPackage(first)
		sender.sendMessage("${ChatColor.GREEN}Done!")
	}

	@Subcommand("install|i")
	@Description("Install a package by its SpigotMC id")
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
