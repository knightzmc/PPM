package me.bristermitten.ppm.entity

sealed class Version
{
    abstract val versionString: String

    abstract fun isNewerThan(other: Version): Boolean
}

data class UnparsableVersion(override val versionString: String) : Version()
{
    override fun isNewerThan(other: Version) = false
}

data class NormalVersion(val major: Int, val minor: Int, val patch: Int) : Version()
{
    override val versionString: String = "${major}.${minor}.${patch}"

    override fun isNewerThan(other: Version): Boolean
    {
        if (other is NormalVersion)
        {
            return when
            {
                major > other.major -> true
                minor > other.minor -> true
                patch > other.patch -> true
                else -> false
            }
        }
        return false //TODO
    }
}

data class SpigotResourceVersion(val id: Long) : Version()
{
    override val versionString: String = id.toString()

    override fun isNewerThan(other: Version): Boolean
    {
        if (other !is SpigotResourceVersion) return false

        return this.id > other.id
    }
}
