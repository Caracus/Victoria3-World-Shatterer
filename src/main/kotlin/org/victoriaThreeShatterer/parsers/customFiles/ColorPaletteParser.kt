package org.victoriaThreeShatterer.parsers.customFiles

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.victoriaThreeShatterer.models.RgbColor
import java.io.File

fun readColorPalette(path: String): List<RgbColor> {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()

    val jsonString: String = File(path).readText(Charsets.UTF_8)
    return mapper.readValue(jsonString)
}