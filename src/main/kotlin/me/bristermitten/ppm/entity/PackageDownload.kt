package me.bristermitten.ppm.entity

import okhttp3.ResponseBody

data class PackageDownload(
        val fileName: String,
        val data: ResponseBody
)
