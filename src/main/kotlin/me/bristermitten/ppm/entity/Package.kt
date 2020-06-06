package me.bristermitten.ppm.entity

import me.bristermitten.ppm.entity.meta.RemotePackageMetadata

interface Package
{
    val id: Long
    val name: String
    val author: String
    val description: String
    val category: String

    val metadata: RemotePackageMetadata

    fun isValid() : Boolean
}
