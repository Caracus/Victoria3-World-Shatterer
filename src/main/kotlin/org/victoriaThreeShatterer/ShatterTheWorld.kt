package org.victoriaThreeShatterer


import org.victoriaThreeShatterer.creators.*
import org.victoriaThreeShatterer.parsers.customFiles.readColorPalette
import org.victoriaThreeShatterer.parsers.gameFiles.getConsolidatedBuildingsMap
import org.victoriaThreeShatterer.parsers.gameFiles.getConsolidatedPopMap
import org.victoriaThreeShatterer.parsers.gameFiles.readStatesFile
import org.victoriaThreeShatterer.utils.*

const val patchVersion = "1-2-4"
const val installedGameFolderPath = "C:/Games/Victoria.3.v1.2.4/game/"
const val gameFilesFolderPath = "src/main/resources/GameFiles/$patchVersion/"

fun main() {

    println("Start copying original files")
    copyOriginalFilesFromGameToGameFiles()
    println("Done copying original files")

    println("Start parsing files")
    val colorPalette = readColorPalette("src/main/resources/colorPalette.json")
    val stateMap = readStatesFile(gameFilesFolderPath.plus("00_states.txt"))
    val compactPopMap = getConsolidatedPopMap(patchVersion)
    var buildingsMap = getConsolidatedBuildingsMap(patchVersion)
    println("Done parsing files")

    println("Start modifying parsed data")
    buildingsMap = setBarracksToAtLeast(10, buildingsMap)
    println("Done modifying parsed data")

    println("Start creating new files")
    createBuildingsFile(buildingsMap)
    createBuildingsOnlyBarracks(stateMap)
    createPopsFile(compactPopMap)
    createStatesFile(stateMap)
    createPopulationsFiles(compactPopMap)
    createCountryDefinitionsFile(stateMap, compactPopMap, colorPalette, installedGameFolderPath)
    createCountriesFiles(compactPopMap)
    createLocalization(stateMap)
    println("Done creating new files")

    println("Start updating mod folder")
    updateModFolderFromTarget()
    copyFormablesToMod()
    println("Done updating mod folder")

    println("Program done")
}
