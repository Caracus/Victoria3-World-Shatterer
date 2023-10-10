package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createStatesFile(stateMap: MutableMap<String, State>) {

    var text = ""
    text += "STATES = {\n"
    stateMap.values.forEach {
        text += format(1, "s:STATE_${it.stateName} = {", 1)
        text += format(2, "create_state = {", 1)
        text += format(3, "country = c:${it.stateName}", 1)

        var provinces = ""
        it.provinces.forEach {
            provinces += "${it} "
        }
        text += format(3, "owned_provinces = { ${provinces}}", 1)
        text += format(2, "}", 1)
        text += format(0, "", 1)

        it.homelands.forEach {
            text += format(2, "add_homeland = ${it}", 1)
        }
        text += format(1, "}", 1)
    }

    text += format(0, "}", 0)

    printFile("game/common/history/states/", "00_states.txt", text)
}

fun createStatesFileProvinceMode(stateMap: MutableMap<String, State>) {

    println("Start createStatesFileProvinceMode")

    var text = ""
    text += "STATES = {\n"
    stateMap.values.forEach {
        text += format(1, "s:STATE_${it.stateName} = {", 1)

        it.provinces.forEach {
            text += format(2, "create_state = {", 1)
            text += format(3, "country = c:${it}land", 1)

            text += format(3, "owned_provinces = { ${it}}", 1)
            text += format(2, "}", 1)
            text += format(0, "", 1)
        }

        text += format(0, "", 1)
        it.homelands.forEach {
            text += format(2, "add_homeland = ${it}", 1)
        }
        text += format(1, "}", 1)
    }

    text += format(0, "}", 0)

    printFile("game/common/history/states/", "00_states.txt", text)

    println("Finished createStatesFileProvinceMode")
}