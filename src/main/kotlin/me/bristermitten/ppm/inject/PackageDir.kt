package me.bristermitten.ppm.inject

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Qualifier
annotation class PackageDir
