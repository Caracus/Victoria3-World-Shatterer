package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createLocalization(
    stateMap: MutableMap<String, State>,
    cultureSet: Set<String>
) {

    var text = ""
    text += format(0, "l_english:", 1)

    stateMap.values.forEach {
        var namesArray = it.stateName.split("_")
        var displayName = ""
        namesArray.forEach {
            var lowercase = it.lowercase()
            displayName = displayName.plus("${lowercase[0].uppercase() + lowercase.substring(1)} ")
        }
        displayName = displayName.trim()

        text += format(0, " ${it.stateName}:0 \"${displayName}\"", 1)
        text += format(0, " ${it.stateName}_ADJ:0 \"${displayName}ian\"", 1)
    }

    cultureSet.forEach { cultureName ->
        text += format(0, "NAT_${cultureName.toUpperCase()}:0 \"\$$cultureName\$ National State\"", 1)
        text += format(0, "NAT_${cultureName.toUpperCase()}_ADJ:0 \"\$$cultureName\$\"", 1)
    }

    printFile("game/localization/history/english/", "custom_countries_l_english.yml", text)
}