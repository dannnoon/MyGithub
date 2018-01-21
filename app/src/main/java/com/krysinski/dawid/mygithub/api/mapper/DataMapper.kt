package com.krysinski.dawid.mygithub.api.mapper

interface DataMapper<in I, out O> {

    fun apply(data: I): O
}