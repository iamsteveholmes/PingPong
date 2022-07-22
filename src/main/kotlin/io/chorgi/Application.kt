package io.chorgi

import io.micronaut.runtime.Micronaut.build

object Application

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("io.chorgi")
        .start()
}

