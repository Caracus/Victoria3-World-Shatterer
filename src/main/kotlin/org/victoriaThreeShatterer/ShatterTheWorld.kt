package org.victoriaThreeShatterer


import org.victoriaThreeShatterer.configuration.getGeneralConfiguration
import org.victoriaThreeShatterer.creators.*
import org.victoriaThreeShatterer.parsers.customFiles.readColorPalette
import org.victoriaThreeShatterer.parsers.gameFiles.getConsolidatedBuildingsMap
import org.victoriaThreeShatterer.parsers.gameFiles.getConsolidatedPopMap
import org.victoriaThreeShatterer.parsers.gameFiles.readStatesFile
import org.victoriaThreeShatterer.utils.*

const val patchVersion = "1-3-6"
const val gameFilesFolderPath = "src/main/resources/GameFiles/$patchVersion/"

fun main() {

    //set up the general configuration, create a copy of the resources/configuration/configurationSkeleton.json as that one will be ignored via gitignore
    val generalConfiguration = getGeneralConfiguration()
    val gamePath = generalConfiguration.victoriaThreeFolderPath

    //be careful with this as you might have to manually adjust bad formatting or comments that brick the parser in these files, so consider turning this off after you got the initial files
    println("Start copying original files")
    copyOriginalFilesFromGameToGameFiles(gamePath)
    println("Done copying original files")

    println("Start parsing files")
    val colorPalette = readColorPalette("src/main/resources/colorPalette.json")
    val stateMap = readStatesFile(gameFilesFolderPath.plus("00_states.txt"))
    val compactPopMap = getConsolidatedPopMap(patchVersion)
    var buildingsMap = getConsolidatedBuildingsMap(patchVersion)
    println("Done parsing files")

    val cultureSet = createFormables(stateMap, colorPalette)

    println("Start modifying parsed data")
    buildingsMap = setBarracksToAtLeast(25, buildingsMap)
    buildingsMap = setConstructionSectorsToAtLeast(3, buildingsMap)
    println("Done modifying parsed data")

    println("Start creating new files")
    createBuildingsFile(buildingsMap)
    //createBuildingsOnlyBarracks(stateMap) only enable this if you want a blank building map
    createPopsFile(compactPopMap)
    createStatesFile(stateMap)
    createPopulationsFiles(compactPopMap)
    createCountryDefinitionsFile(stateMap, compactPopMap, colorPalette, gamePath)
    createCountriesFiles(compactPopMap)
    createAiStartingStrategies(stateMap)
    createLocalization(stateMap, cultureSet)
    println("Done creating new files")

    //no longer needed, add dynamic formables instead
    //println("Start updating mod folder")
    //updateModFolderFromTarget()
    //copyFormablesToMod(gamePath)
    //println("Done updating mod folder")

    println("Program done")
}
