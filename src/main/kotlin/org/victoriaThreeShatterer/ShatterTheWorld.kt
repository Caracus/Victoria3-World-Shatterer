package org.victoriaThreeShatterer

import org.victoriaThreeShatterer.creators.*
import org.victoriaThreeShatterer.parsers.customFiles.readColorPalette
import org.victoriaThreeShatterer.parsers.gameFiles.*
import org.victoriaThreeShatterer.utils.*

// copy the buildings, pops and state file from the games history folder to the resources folder, then set the patch version
const val patchVersion = "1-9-6"

fun main() {

    println("Start parsing files")
    val colorPalette = readColorPalette("src/main/resources/colorPalette.json")
    val stateMap = readStatesFile("src/main/resources/GameFiles/$patchVersion/".plus("00_states.txt"))

    val compactPopMap = getConsolidatedPopMap(patchVersion)
    //equalized start option, un/comment the other one
    //val compactPopMap = getConsolidatedPopMapWithGenericRegionalMapping(patchVersion)

    var buildingsMap = initEmptyBuildings(stateMap)
    println("Done parsing files")

    val cultureSet = createFormables(stateMap, colorPalette)

    println("Start modifying parsed data")
    buildingsMap = setBarracksToAtLeast(5, buildingsMap)
    //buildingsMap = setConstructionSectorsToAtLeast(3, buildingsMap) //this caused issues in 1.7 as the ai dint build if it had negative balance
    println("Done modifying parsed data")

    println("Start creating new files")
    createBuildingsFile(buildingsMap)
    createPopsFile(compactPopMap)
    createStatesFile(stateMap)
    createPopulationsFiles(compactPopMap)
    createCountryDefinitionsFile(stateMap, compactPopMap, colorPalette)
    createCountriesFiles(compactPopMap)
    createAiStartingStrategies(stateMap)
    createLocalization(stateMap, cultureSet)
    println("Done creating new files")

    println("Program done")
}
