package org.victoriaThreeShatterer.utils

import java.io.File

//even tho the game engine does not need formatting it is here to prevent eye cancer
fun format(indentations: Int, text: String, linebreaks: Int): String {
    var txt = ""
    for (i in 0 until indentations) {
        txt = txt.plus("\t")
    }
    txt = txt.plus(text)
    for (i in 0 until linebreaks) {
        txt = txt.plus("\n")
    }
    return txt
}

//prints the created file-s in the target folder
fun printFile(path: String, fileName: String, content: String) {
    val repoPath = "target/generatedModFiles/"
    val savePath = repoPath.plus(path)
    val directory = File(savePath)
    if (directory.mkdirs()) {
        println("Created new folder structure under target")
    }

    //utf with bom
    File(savePath.plus(fileName)).writeText("\uFEFF" + content, Charsets.UTF_8)
}

fun readFileAsText(fileName: String): String = removeComments(
    File(fileName).readText(Charsets.UTF_8)
)

fun updateModFolderFromTarget() {

    //delete countries and population folder (these have files for each country)
    val historyCountries = File("mod/Shattered World - Every state a country/common/history/countries")
    val historyPopulation = File("mod/Shattered World - Every state a country/common/history/population")
    historyCountries.deleteRecursively()
    historyPopulation.deleteRecursively()

    //copy all files from target to mod
    val targetCommon = File("target/generatedModFiles/game/common")
    val modCommon = File("mod/Shattered World - Every state a country/common")
    val targetLocalization = File("target/generatedModFiles/game/localization")
    val modLocalization = File("mod/Shattered World - Every state a country/localization")
    targetCommon.copyRecursively(modCommon,true)
    targetLocalization.copyRecursively(modLocalization,true)

}

fun copyFormablesToMod(gamePath: String){
    val gameCountryFormation = File(gamePath.plus("common/country_formation"))
    val modCountryFormation = File("mod/Shattered World - Every state a country/common/country_formation")
    gameCountryFormation.copyRecursively(modCountryFormation, true)
}

/**
fun copyOriginalFilesFromGameToGameFiles (gamePath: String){
    //Buildings
    val gameBuildings = File(gamePath.plus("common/history/buildings"))
    val gameFilesBuildings = File(gameFilesFolderPath.plus("buildings"))
    gameFilesBuildings.deleteRecursively()
    gameBuildings.copyRecursively(gameFilesBuildings, true)

    //Pops
    val gamePops = File(gamePath.plus("common/history/pops"))
    val gameFilesPops = File(gameFilesFolderPath.plus("pops"))
    gameFilesPops.deleteRecursively()
    gamePops.copyRecursively(gameFilesPops, true)

    //States
    val gameStates = File(gamePath.plus("common/history/states/00_states.txt"))
    val gameFilesStates = File(gameFilesFolderPath.plus("00_states.txt"))
    gameStates.copyTo(gameFilesStates, true)
}
*/

fun removeComments(string: String): String {
    val regex = Regex("\\s*#.*?\n")
    return string.replace(regex, "\n")
}