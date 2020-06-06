package me.bristermitten.ppm.service

import me.bristermitten.ppm.entity.Package
import java.io.File

interface PackageDownloader
{
    fun download(toDownload: Package) : File
}
