package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.RgbColor
import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createFormables(stateMap: MutableMap<String, State>, colorPalette: List<RgbColor>) : Set<String>{

    var cultureSet: Set<String> = emptySet()

    stateMap.forEach {
        it.value.homelands.forEach{
           cultureSet =  cultureSet.plus(it)
        }
    }

    var text = ""
    cultureSet.forEach { cultureName ->
        val cleanedCultureName = cultureName.replace("cu:", "")

        text += format(0, "NAT_${cleanedCultureName.toUpperCase()} = {", 1)
        text += format(1, "use_culture_states = yes", 1)
        text += format(1, "required_states_fraction = 0.51", 1)
        text += format(1, "ai_will_do = { always = yes }", 1)
        text += format(0, "}", 1)
    }

    printFile("game/common/country_formation/", "00_formable_countries.txt", text)
    println("Created dynamic formables")

    createFormablesDefinitions(cultureSet, colorPalette)

    return cultureSet
}

fun createFormablesDefinitions(cultureSet: Set<String>, colorPalette: List<RgbColor>,){
    var text = ""

    cultureSet.forEach { cultureName ->
        val color = colorPalette[(1..colorPalette.size).random() - 1].toRange(5)

        val cleanedCultureName = cultureName.replace("cu:", "")

        text += format(0, "NAT_${cleanedCultureName.toUpperCase()} = {", 1)
        text += format(1, "color = { $color }", 1)
        text += format(1, "country_type = recognized", 1)
        text += format(1, "tier = kingdom", 1)
        text += format(1, "cultures = { $cleanedCultureName }", 1)
        text += format(0, "}", 1)
    }

    printFile("game/common/country_definitions/", "01_formable_countries.txt", text)
    println("Created dynamic formables definitions")
}
