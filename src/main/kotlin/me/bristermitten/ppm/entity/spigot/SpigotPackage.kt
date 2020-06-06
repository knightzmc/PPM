package me.bristermitten.ppm.entity.spigot

import com.google.gson.annotations.SerializedName
import me.bristermitten.ppm.entity.Package
import me.bristermitten.ppm.entity.NormalVersion
import me.bristermitten.ppm.entity.SpigotResourceVersion
import me.bristermitten.ppm.entity.Version
import me.bristermitten.ppm.entity.meta.Rating
import me.bristermitten.ppm.entity.meta.RemotePackageMetadata

data class SpigotPackage(
        override val id: Long = -1,
        override val name: String = "",
        @SerializedName("author")
        private val spigotAuthor: SpigotAuthor = SpigotAuthor(),
        @SerializedName("tag")
        override val description: String = "",
        @SerializedName("category")
        val spigotCategory: SpigotCategory = SpigotCategory(),
        val rating: Rating = Rating(),
        val versions: List<SpigotResourceVersion> = emptyList()
) : Package
{

    @Transient
    override val author: String = spigotAuthor.id.toString()

    @Transient
    override val category: String = spigotCategory.id.toString()

    override val metadata: RemotePackageMetadata = RemotePackageMetadata(rating, versions)

    data class SpigotAuthor(val id: Long = -1)

    data class SpigotCategory(val id: Long = -1)

    override fun isValid(): Boolean
    {
        return id != -1L && spigotAuthor.id != -1L && spigotCategory.id != -1L
    }

}
