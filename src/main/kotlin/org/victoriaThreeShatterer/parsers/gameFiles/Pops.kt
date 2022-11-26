package org.victoriaThreeShatterer.parsers.gameFiles

import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.utils.RegionalMapping
import org.victoriaThreeShatterer.utils.readFileAsText

fun getConsolidatedPopMap(patchVersion: String): MutableMap<String, List<PopulationData>> {
    var westEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/00_west_europe.txt", RegionalMapping.WEST_EUROPE)
    var southEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/01_south_europe.txt", RegionalMapping.SOUTH_EUROPE)
    var eastEuropePops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/02_east_europe.txt", RegionalMapping.EAST_EUROPE)
    var northAfricaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/03_north_africa.txt", RegionalMapping.NORTH_AFRICA)
    var subsaharanAfricaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/04_subsaharan_africa.txt", RegionalMapping.SUBSAHARAN_AFRICA)
    var northAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/05_north_america.txt", RegionalMapping.NORTH_AMERICAN)
    var centralAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/06_central_america.txt", RegionalMapping.CENTRAL_AMERICAN)
    var southAmericaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/07_south_america.txt", RegionalMapping.SOUTH_AMERICAN)
    var middleEastPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/08_middle_east.txt", RegionalMapping.MIDDLE_EAST)
    var centralAsiaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/09_central_asia.txt", RegionalMapping.CENTRAL_ASIA)
    var indiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/10_india.txt", RegionalMapping.INDIA)
    var eastAsiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/11_east_asia.txt", RegionalMapping.EAST_ASIA)
    var indonesiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/12_indonesia.txt", RegionalMapping.INDONESIA)
    var australasiaPops =
        readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/13_australasia.txt", RegionalMapping.AUSTRALASIA)
    var siberiaPops = readPopsFile("src/main/resources/GameFiles/$patchVersion/pops/14_siberia.txt", RegionalMapping.SIBERIA)

    return (
            westEuropePops + southEuropePops + eastEuropePops + northAfricaPops + subsaharanAfricaPops + northAmericaPops +
                    centralAmericaPops + southAmericaPops + middleEastPops + centralAsiaPops + indiaPops + eastAsiaPops +
                    indonesiaPops + australasiaPops + siberiaPops
            ) as MutableMap<String, List<PopulationData>>
}

fun readPopsFile(path: String, regionalMapping: RegionalMapping): MutableMap<String, List<PopulationData>> {
    val textOfFile = readFileAsText(path)
    val populationMap = mutableMapOf<String, PopulationData>()

    var superMatch = Regex("s:STATE_(.*?)^\\t{1}\\}", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE))

    var stateSet = superMatch.findAll(textOfFile).toSet()

    stateSet.forEach {
        var popBlock = it.groupValues[1]
        var stateName = Regex("(.*).={1}").find(popBlock)!!.groupValues[1]

        var createPopBlock =
            Regex("create_pop = \\{(.*?)^\\t{3}\\}", setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)).findAll(
                popBlock
            ).toSet()

        createPopBlock.forEach {
            var culture = Regex("culture ?= (.*)").find(it.groupValues[1])!!.groupValues[1]
            var amountOfPops = Regex("size ?= (.*)").find(it.groupValues[1])!!.groupValues[1]
            var religion = Regex("religion ?= (.*)").find(it.groupValues[1])?.let {
                it.groupValues!!.get(1)
            } ?: "none"

            // if an entry exists that matches state, culture and religion update it, else new
            var mapKey = "${stateName}-${culture}-${religion}"
            populationMap.get(mapKey)?.let {
                populationMap.set(
                    mapKey,
                    PopulationData(stateName, culture, amountOfPops.toInt() + it.popNumber, religion, regionalMapping)
                )
            } ?: populationMap.set(
                mapKey,
                PopulationData(stateName, culture, amountOfPops.toInt(), religion, regionalMapping)
            )
        }
    }

    //convert compound keyMap to statename only key
    var compactPopMap = mutableMapOf<String, List<PopulationData>>()
    populationMap.values.forEach {
        var mutableList = mutableListOf<PopulationData>()
        compactPopMap.get(it.state)?.let {
            mutableList.addAll(it)
        }
        mutableList.add(it)
        compactPopMap.set(it.state, mutableList)
    }
    return compactPopMap
}