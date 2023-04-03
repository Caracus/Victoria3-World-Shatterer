package org.victoriaThreeShatterer.configuration

import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.victoriaThreeShatterer.configuration.models.GeneralConfiguration
import java.io.File

fun getGeneralConfiguration(): GeneralConfiguration {
    val mapper = jsonMapper {
        addModule(kotlinModule())
    }
    val text = File("src/main/resources/configuration/configuration.json").readText(Charsets.UTF_8)

    return mapper.readValue(text)

}