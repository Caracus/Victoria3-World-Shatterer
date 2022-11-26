package org.victoriaThreeShatterer.parsers.customFiles

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.victoriaThreeShatterer.models.GlobalConfig
import java.io.File

// original plan was to preserve preset colors, flags and the likes if present as a combination of parsing vanilla and custom content
fun readOptionalConfigurations(path: String) {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()

    val jsonString: String = File(path).readText(Charsets.UTF_8)
    val configs = mapper.readValue<GlobalConfig>(jsonString, GlobalConfig::class.java)
}