package org.victoriaThreeShatterer.parsers.gameFiles

import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.utils.RegionalMapping
import org.victoriaThreeShatterer.utils.readFileAsText

fun getConsolidatedPopMap(patchVersion: String): MutableMap<String, List<PopulationData>> {
    val westEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/00_west_europe.txt", RegionalMapping.WEST_EUROPE)
    val southEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/01_south_europe.txt", RegionalMapping.SOUTH_EUROPE)
    val eastEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/02_east_europe.txt", RegionalMapping.EAST_EUROPE)
    val northAfricaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/03_north_africa.txt", RegionalMapping.NORTH_AFRICA)
    val subsaharanAfricaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/04_subsaharan_africa.txt", RegionalMapping.SUBSAHARAN_AFRICA)
    val northAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/05_north_america.txt", RegionalMapping.NORTH_AMERICAN)
    val centralAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/06_central_america.txt", RegionalMapping.CENTRAL_AMERICAN)
    val southAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/07_south_america.txt", RegionalMapping.SOUTH_AMERICAN)
    val middleEastPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/08_middle_east.txt", RegionalMapping.MIDDLE_EAST)
    val centralAsiaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/09_central_asia.txt", RegionalMapping.CENTRAL_ASIA)
    val indiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/10_india.txt", RegionalMapping.INDIA)
    val eastAsiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/11_east_asia.txt", RegionalMapping.EAST_ASIA)
    val indonesiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/12_indonesia.txt", RegionalMapping.INDONESIA)
    val australasiaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/13_australasia.txt", RegionalMapping.AUSTRALASIA)
    val siberiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/14_siberia.txt", RegionalMapping.SIBERIA)
    val russiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/15_russia.txt", RegionalMapping.RUSSIA)

    return (
            westEuropePops + southEuropePops + eastEuropePops + northAfricaPops + subsaharanAfricaPops + northAmericaPops +
                    centralAmericaPops + southAmericaPops + middleEastPops + centralAsiaPops + indiaPops + eastAsiaPops +
                    indonesiaPops + australasiaPops + siberiaPops + russiaPops
            ) as MutableMap<String, List<PopulationData>>
}

fun getConsolidatedPopMapWithGenericRegionalMapping(patchVersion: String): MutableMap<String, List<PopulationData>> {
    val westEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/00_west_europe.txt", RegionalMapping.GENERIC_PRESET)
    val southEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/01_south_europe.txt", RegionalMapping.GENERIC_PRESET)
    val eastEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/02_east_europe.txt", RegionalMapping.GENERIC_PRESET)
    val northAfricaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/03_north_africa.txt", RegionalMapping.GENERIC_PRESET)
    val subsaharanAfricaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/04_subsaharan_africa.txt", RegionalMapping.GENERIC_PRESET)
    val northAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/05_north_america.txt", RegionalMapping.GENERIC_PRESET)
    val centralAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/06_central_america.txt", RegionalMapping.GENERIC_PRESET)
    val southAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/07_south_america.txt", RegionalMapping.GENERIC_PRESET)
    val middleEastPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/08_middle_east.txt", RegionalMapping.GENERIC_PRESET)
    val centralAsiaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/09_central_asia.txt", RegionalMapping.GENERIC_PRESET)
    val indiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/10_india.txt", RegionalMapping.GENERIC_PRESET)
    val eastAsiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/11_east_asia.txt", RegionalMapping.GENERIC_PRESET)
    val indonesiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/12_indonesia.txt", RegionalMapping.GENERIC_PRESET)
    val australasiaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/13_australasia.txt", RegionalMapping.GENERIC_PRESET)
    val siberiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/14_siberia.txt", RegionalMapping.GENERIC_PRESET)
    val russiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/15_russia.txt", RegionalMapping.GENERIC_PRESET)

    return (
            westEuropePops + southEuropePops + eastEuropePops + northAfricaPops + subsaharanAfricaPops + northAmericaPops +
                    centralAmericaPops + southAmericaPops + middleEastPops + centralAsiaPops + indiaPops + eastAsiaPops +
                    indonesiaPops + australasiaPops + siberiaPops + russiaPops
            ) as MutableMap<String, List<PopulationData>>
}

fun readPopsFile(path: String, regionalMapping: RegionalMapping): MutableMap<String, List<PopulationData>> {
    val textOfFile = readFileAsText(path)
    val populationMap = mutableMapOf<String, PopulationData>()

    val superMatch = Regex("s:STATE_(.*?)^\\t{1}}", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE))

    val stateSet = superMatch.findAll(textOfFile).toSet()

    stateSet.forEach { matchResult ->
        val popBlock = matchResult.groupValues[1]
        //var stateName = Regex("(.*).={1}").find(popBlock)!!.groupValues[1]
        val stateName = Regex("(.*)\\s*={1}").find(popBlock)!!.groupValues[1].filter { !it.isWhitespace() }

        val createPopBlock =
            Regex("create_pop = \\{(.*?)^\\t{3}}", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)).findAll(
                popBlock
            ).toSet()

        createPopBlock.forEach {
            try {
                val culture = Regex("culture ?= ([a-z,_]*)").find(it.groupValues[1])!!.groupValues[1]
                val amountOfPops = Regex("size = (\\d{1,20})").find(it.groupValues[1])!!.groupValues[1]
                val religion = Regex("religion = ([a-z,_]*)").find(it.groupValues[1])?.groupValues?.get(1) ?: "none"

                // if an entry exists that matches state, culture and religion update it, else new
                val mapKey = "${stateName}-${culture}-${religion}"
                populationMap.get(mapKey)?.let {
                    populationMap.set(
                        mapKey,
                        PopulationData(stateName, culture, amountOfPops.toInt() + it.popNumber, religion, regionalMapping)
                    )
                } ?: populationMap.set(
                    mapKey,
                    PopulationData(stateName, culture, amountOfPops.toInt(), religion, regionalMapping)
                )
            } catch (e: Exception) {
                println("Error parsing: $stateName")
                println("Error message: ${e.message}")
            }

        }
    }

    //convert compound keyMap to statename only key
    val compactPopMap = mutableMapOf<String, List<PopulationData>>()
    populationMap.values.forEach {
        val mutableList = mutableListOf<PopulationData>()
        compactPopMap.get(it.state)?.let {
            mutableList.addAll(it)
        }
        mutableList.add(it)
        compactPopMap.set(it.state, mutableList)
    }
    return compactPopMap
}