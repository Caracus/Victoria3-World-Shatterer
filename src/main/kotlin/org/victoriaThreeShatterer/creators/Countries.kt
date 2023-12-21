package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.CombinedStateData
import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.models.RgbColor
import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.*
import java.io.File

fun createCountriesFiles(compactPopMap: MutableMap<String, List<PopulationData>>) {

    compactPopMap.keys.forEach {
        var text = ""
        text += format(0, "COUNTRIES = {", 1)
        text += format(1, "c:${it} = {", 1)
        text += format(1, "${compactPopMap.get(it)!!.first().regionalMapping.technologyBase} = yes", 1)

        text += format(1, "activate_law = law_type:law_monarchy", 1)
        text += format(1, "activate_law = law_type:law_autocracy", 1)
        text += format(1, "activate_law = law_type:law_freedom_of_conscience", 1)
        text += format(1, "activate_law = law_type:law_no_womens_rights", 1)
        text += format(1, "activate_law = law_type:law_land_based_taxation", 1)
        text += format(1, "activate_law = law_type:law_appointed_bureaucrats", 1)
        text += format(1, "activate_law = law_type:law_national_supremacy", 1)
        text += format(1, "activate_law = law_type:law_mercantilism", 1)
        text += format(1, "activate_law = law_type:law_tenant_farmers", 1)
        text += format(1, "activate_law = law_type:law_interventionism", 1)
        text += format(1, "activate_law = law_type:law_professional_army", 1)
        text += format(1, "activate_law = law_type:law_censorship", 1)
        text += format(1, "activate_law = law_type:law_migration_controls", 1)
        text += format(1, "}", 1)
        text += format(0, "}", 1)

        printFile("game/common/history/countries/", "${it} - ${it}.txt", text)
    }
}

fun createCountriesFilesProvincesMode(combinedStateData: MutableMap<String, CombinedStateData>) {
    println("start createCountriesFilesProvincesMode")

    combinedStateData.forEach { combinedStateData ->

        combinedStateData.value.provinces.forEach {
            var text = ""
            text += format(0, "COUNTRIES = {", 1)
            text += format(1, "c:${it}land = {", 1)
            text += format(1, "${combinedStateData.value.populationList.first().regionalMapping.technologyBase} = yes", 1)
            text += format(1, "}", 1)
            text += format(0, "}", 1)

            printFile("game/common/history/countries/", "${it}land - ${it}land.txt", text)
        }
    }

    println("finished createCountriesFilesProvincesMode")
}

fun createCountryDefinitionsFile(
    stateMap: MutableMap<String, State>,
    compactPopMap: MutableMap<String, List<PopulationData>>,
    colorPalette: List<RgbColor>,
    pathToVictoria3GameFolder: String
) {

    var text = ""
    stateMap.values.forEach {
        val cultureNumberMap = mutableMapOf<String, Int>()
        var totalPops = 0
        compactPopMap.get(it.stateName)!!.forEach {
            val existingPops = cultureNumberMap.get(it.culture) ?: 0
            cultureNumberMap.set(it.culture, it.popNumber + existingPops)
            totalPops += it.popNumber
        }

        val sortedMap = cultureNumberMap.toList()
            .sortedByDescending { (_, value) -> value }
            .toMap()

        val color = colorPalette[(1..colorPalette.size).random() - 1].toRange(5)

        text += format(0, "${it.stateName} = {", 1)
        text += format(1, "color = { $color }", 1)
        text += format(1, "country_type = recognized", 1)
        text += format(1, "tier = principality", 1)
        text += format(1, "cultures = { ", 0)
        text += format(0, sortedMap.keys.first(), 0)

        if (sortedMap.size > 1) {
            val secondCulture = sortedMap.keys.elementAt(1)
            val ratio = sortedMap.getValue(secondCulture).toDouble() / totalPops
            if (ratio > 0.3) {
                text += format(0, " ${secondCulture}", 0)
            }
        }
        text += format(0, " }", 1)
        text += format(1, "capital = STATE_${it.stateName}", 1)

        text += format(0, "}", 1)
    }

    //add all possible countries to the end of file, so they are formable
    /**
    File(pathToVictoria3GameFolder.plus("common/country_definitions/")).walk().forEach {
        if (it.isFile){
            val textOfFile = it.readText(Charsets.UTF_8)
            text += textOfFile
        }
    }
    */

    printFile("game/common/country_definitions/", "00_countries.txt", text)
}

fun createCountryDefinitionsFileProvinceMode(
    combinedStateData: MutableMap<String, CombinedStateData>,
    colorPalette: List<RgbColor>,
    pathToVictoria3GameFolder: String
) {
    println("start createCountryDefinitionsFileProvinceMode")

    var text = ""
    combinedStateData.values.forEach {
        val cultureNumberMap = mutableMapOf<String, Int>()
        var totalPops = 0
        it.populationList.forEach {
            val existingPops = cultureNumberMap.get(it.culture) ?: 0
            cultureNumberMap.set(it.culture, it.popNumber + existingPops)
            totalPops += it.popNumber
        }

        val sortedMap = cultureNumberMap.toList()
            .sortedByDescending { (_, value) -> value }
            .toMap()

        it.provinces.forEach{ province ->
            val color = colorPalette[(1..colorPalette.size).random() - 1].toRange(5)

            text += format(0, "${province}land = {", 1)
            text += format(1, "color = { $color }", 1)
            text += format(1, "country_type = recognized", 1)
            text += format(1, "tier = principality", 1)
            text += format(1, "cultures = { ", 0)
            text += format(0, sortedMap.keys.first(), 0)

            if (sortedMap.size > 1) {
                val secondCulture = sortedMap.keys.elementAt(1)
                val ratio = sortedMap.getValue(secondCulture).toDouble() / totalPops
                if (ratio > 0.3) {
                    text += format(0, " ${secondCulture}", 0)
                }
            }
            text += format(0, " }", 1)
            text += format(1, "capital = STATE_${it.stateName}", 1)

            text += format(0, "}", 1)
        }
    }

    printFile("game/common/country_definitions/", "00_countries.txt", text)

    println("finished createCountryDefinitionsFileProvinceMode")
}