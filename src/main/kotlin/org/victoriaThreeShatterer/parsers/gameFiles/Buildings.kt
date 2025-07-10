package org.victoriaThreeShatterer.parsers.gameFiles

import org.victoriaThreeShatterer.models.BuildingData
import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.readFileAsText
import kotlin.collections.set

fun getConsolidatedBuildingsMap(patchVersion: String): MutableMap<String, MutableList<BuildingData>> {
    var westEuropeBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/00_west_europe.txt")
    var southEuropeBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/01_south_europe.txt")
    var eastEuropeBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/02_east_europe.txt")
    var northAfricaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/03_north_africa.txt")
    var subsaharanAfricaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/04_subsaharan_africa.txt")
    var northAmericaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/05_north_america.txt")
    var centralAmericaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/06_central_america.txt")
    var southAmericaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/07_south_america.txt")
    var middleEastBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/08_middle_east.txt")
    var centralAsiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/09_central_asia.txt")
    var indiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/10_india.txt")
    var eastAsiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/11_east_asia.txt")
    var indonesiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/12_indonesia.txt")
    var australasiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/13_australasia.txt")
    var siberiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/14_siberia.txt")
    var russiaBuildings = readBuildingsFile("src/main/resources/GameFiles/$patchVersion/buildings/15_russia.txt")

    return (
            westEuropeBuildings + southEuropeBuildings + eastEuropeBuildings + northAfricaBuildings + subsaharanAfricaBuildings + northAmericaBuildings +
                    centralAmericaBuildings + southAmericaBuildings + middleEastBuildings + centralAsiaBuildings + indiaBuildings + eastAsiaBuildings +
                    indonesiaBuildings + australasiaBuildings + siberiaBuildings + russiaBuildings
            ) as MutableMap<String, MutableList<BuildingData>>
}

fun readBuildingsFile(path: String): MutableMap<String, MutableList<BuildingData>> {
    var buildingMap = mutableMapOf<String, MutableList<BuildingData>>()

    val textOfFile = readFileAsText(path)

    //get everything in between states
    val stateInformationSet = Regex(
        "(?<=s:STATE_)(.*?)=\\{(.*?).(?=s:STATE|\\Z)",
        setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
    ).findAll(
        textOfFile
    ).toSet()

    stateInformationSet.forEach {
        var stateName = it.groupValues[1]
        var createBuildingsText = it.groupValues[2]

        /** this still uses the old simple level system instead of the old more complex ownership, would have to see a bigger rework
        //get all buildings texts within a state itself
        var buildingTextSet = Regex(
            "(?<=create_building=\\{)(.*?).(?=activate)",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
        ).findAll(
            createBuildingsText
        ).toSet()

        var buildingsInTheStateMap = mutableMapOf<String, Int>()
        buildingTextSet.forEach {
            var buildingText = it.groupValues[1]

            var buildingName = Regex("building=\\\"(.*?)\\\"").find(buildingText)!!.groupValues[1]
            var buildingLevel = Regex("level=(.*?)\\n").find(buildingText)!!.groupValues[1].trim().toInt()

            var existingLevels = buildingsInTheStateMap.get(buildingName) ?: 0
            var totalLevels = existingLevels + buildingLevel

            buildingsInTheStateMap.set(buildingName, totalLevels)
        }

        var buildingsInTheState = mutableListOf<BuildingData>()
        buildingsInTheStateMap.keys.forEach {
            buildingsInTheState.add(BuildingData(it, buildingsInTheStateMap.get(it)!!.toInt()))
        }
        buildingMap.set(stateName, buildingsInTheState)
        */
        buildingMap[stateName] = mutableListOf()
    }

    return buildingMap
}

fun dropAllBuildingData(buildingMap: MutableMap<String, MutableList<BuildingData>>): MutableMap<String, MutableList<BuildingData>> {
    for (key in buildingMap.keys) {
        buildingMap[key] = mutableListOf()
    }
    return buildingMap
}

fun initEmptyBuildings(stateMap: MutableMap<String, State>): MutableMap<String, MutableList<BuildingData>> {
    val buildingMap = mutableMapOf<String, MutableList<BuildingData>>()
    stateMap.keys.forEach {
        val stateName = stateMap[it]!!.stateName
        buildingMap[stateName] = mutableListOf()
    }

    return buildingMap
}