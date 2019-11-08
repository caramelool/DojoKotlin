package com.lcmobile.kotlinbestpractices.test

data class Person @JvmOverloads constructor(val name: String, val lastName: String = "") {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun create(name: String, lastName: String = "") = Person(name, lastName)
    }
}