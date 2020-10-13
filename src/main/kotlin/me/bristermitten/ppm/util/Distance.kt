package me.bristermitten.ppm.util

import org.apache.commons.text.similarity.LevenshteinDistance

fun String.distanceTo(other: String): Int
{
	return LevenshteinDistance.getDefaultInstance().apply(this, other)
}
