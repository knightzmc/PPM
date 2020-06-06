package me.bristermitten.ppm.entity.meta

import me.bristermitten.ppm.entity.NormalVersion
import me.bristermitten.ppm.entity.Version

open class RemotePackageMetadata(
        val rating: Rating,
        val versions: List<Version>
)
