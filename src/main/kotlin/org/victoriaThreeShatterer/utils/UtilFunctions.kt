package org.victoriaThreeShatterer.utils

import java.io.File

//even tho the game engine does not need formatting it is here to prevent eye cancer
fun format(indentations: Int, text: String, linebreaks: Int): String {
    var txt = ""
    for (i in 0 until indentations) {
        txt = txt.plus("\t")
    }
    txt = txt.plus(text)
    for (i in 0 until linebreaks) {
        txt = txt.plus("\n")
    }
    return txt
}

//prints the created file-s in the target folder
fun printFile(path: String, fileName: String, content: String) {
    val repoPath = "target/generatedModFiles/"
    val savePath = repoPath.plus(path)
    val directory = File(savePath)
    if (directory.mkdirs()) {
        println("Created new folder structure under target")
    }

    //utf with bom
    File(savePath.plus(fileName)).writeText("\uFEFF" + content, Charsets.UTF_8)
}

fun readFileAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)