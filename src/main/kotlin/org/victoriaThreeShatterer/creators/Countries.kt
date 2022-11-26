package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.models.RgbColor
import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createCountriesFiles(compactPopMap: MutableMap<String, List<PopulationData>>) {

    compactPopMap.keys.forEach {
        var text = ""
        text += format(0, "COUNTRIES = {", 1)
        text += format(1, "c:${it} = {", 1)
        text += format(1, "${compactPopMap.get(it)!!.first().regionalMapping.technologyBase} = yes", 1)
        text += format(1, "}", 1)
        text += format(0, "}", 1)

        printFile("game/common/history/countries/", "${it} - ${it}.txt", text)
    }
}

fun createCountryDefinitionsFile(
    stateMap: MutableMap<String, State>,
    compactPopMap: MutableMap<String, List<PopulationData>>,
    colorPalette: List<RgbColor>
) {

    var text = ""
    stateMap.values.forEach {
        var cultureNumberMap = mutableMapOf<String, Int>()
        var totalPops = 0
        compactPopMap.get(it.stateName)!!.forEach {
            var existingPops = cultureNumberMap.get(it.culture) ?: 0
            cultureNumberMap.set(it.culture, it.popNumber + existingPops)
            totalPops += it.popNumber
        }

        var sortedMap = cultureNumberMap.toList()
            .sortedByDescending { (key, value) -> value }
            .toMap()

        var color = colorPalette[(1..colorPalette.size).random() - 1].toRange(5)

        text += format(0, "${it.stateName} = {", 1)
        text += format(1, "color = { $color }", 1)
        text += format(1, "country_type = recognized", 1)
        text += format(1, "tier = principality", 1)
        text += format(1, "cultures = { ", 0)
        text += format(0, "${sortedMap.keys.first()}", 0)

        if (sortedMap.size > 1) {
            var secondCulture = sortedMap.keys.elementAt(1)
            var ratio = sortedMap.getValue(secondCulture).toDouble() / totalPops
            if (ratio > 0.3) {
                text += format(0, " ${secondCulture}", 0)
            }
        }
        text += format(0, " }", 1)
        text += format(1, "capital = STATE_${it.stateName}", 1)

        text += format(0, "}", 1)
    }
    printFile("game/common/country_definitions/", "00_countries.txt", text)
}