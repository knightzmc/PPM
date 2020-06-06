package me.bristermitten.ppm.service

import me.bristermitten.ppm.entity.Package

interface PackageManager
{
    fun installPackage(toDownload: Package)
}
