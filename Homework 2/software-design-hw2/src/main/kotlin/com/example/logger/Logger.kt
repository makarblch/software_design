package com.example.logger

import java.io.File

object Logger {
    var file = File("logs.txt")

    init{
        file.createNewFile()
    }

    fun log(text : String) {
        file.appendText("$text\n")
    }
}