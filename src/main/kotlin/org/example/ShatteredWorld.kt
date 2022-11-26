package org.example

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.util.Comparator
import kotlin.math.log

fun main(args: Array<String>) {
    var colorPalette = readColorPalette("src/main/resources/colorPalette.json")
    var stateMap = readStatesFile("src/main/resources/realGame/00_states.txt")
    var compactPopMap = getConsolidatedPopMap()
    var buildingsMap = getConsolidatedBuildingsMap()
    //readOptionalConfigurations("src/main/resources/config.json")


    buildingsMap = setBarracksToAtLeast(10, buildingsMap)

    println("read all files")

    createBuildingsMap(buildingsMap)
    createBuildingsMapOnlyBarracks(stateMap)
    createPopsFile(compactPopMap)
    createStatesFile(stateMap)
    createPopulationsFiles(compactPopMap)
    createCountryDefinitionsFile(stateMap, compactPopMap, colorPalette)
    createCountriesFiles(compactPopMap)
    createLocalization(stateMap)

    println("done")
}

fun setBarracksToAtLeast(
    targetLevel: Int,
    buildingMap: MutableMap<String, MutableList<BuildingData>>
): MutableMap<String, MutableList<BuildingData>> {

    var updatedBuildingMap = mutableMapOf<String, MutableList<BuildingData>>()

    buildingMap.keys.forEach {
        var buildingList = buildingMap.get(it)
        var missingBarracksFlag = true

        if(!buildingList!!.isEmpty()){
            for (i in 0 until (buildingList.size)) {
                if (buildingList[i].buildingName == "building_barracks") {
                    missingBarracksFlag = false
                    if (buildingList[i].level < targetLevel) {
                        buildingList[i].level = targetLevel
                    }
                }
            }
            if (missingBarracksFlag) {
                buildingList.add(BuildingData("building_barracks", targetLevel))
            }

        } else {
            buildingList.add(BuildingData("building_barracks", targetLevel))
        }
        updatedBuildingMap.set(it, buildingList)
    }
    return buildingMap
}

fun getConsolidatedBuildingsMap(): MutableMap<String, MutableList<BuildingData>> {
    var westEuropeBuildings = readBuildingsFile("src/main/resources/realGame/buildings/00_west_europe.txt")
    var southEuropeBuildings = readBuildingsFile("src/main/resources/realGame/buildings/01_south_europe.txt")
    var eastEuropeBuildings = readBuildingsFile("src/main/resources/realGame/buildings/02_east_europe.txt")
    var northAfricaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/03_north_africa.txt")
    var subsaharanAfricaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/04_subsaharan_africa.txt")
    var northAmericaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/05_north_america.txt")
    var centralAmericaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/06_central_america.txt")
    var southAmericaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/07_south_america.txt")
    var middleEastBuildings = readBuildingsFile("src/main/resources/realGame/buildings/08_middle_east.txt")
    var centralAsiaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/09_central_asia.txt")
    var indiaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/10_india.txt")
    var eastAsiaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/11_east_asia.txt")
    var indonesiaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/12_indonesia.txt")
    var australasiaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/13_australasia.txt")
    var siberiaBuildings = readBuildingsFile("src/main/resources/realGame/buildings/14_siberia.txt")

    return (
            westEuropeBuildings + southEuropeBuildings + eastEuropeBuildings + northAfricaBuildings + subsaharanAfricaBuildings + northAmericaBuildings +
                    centralAmericaBuildings + southAmericaBuildings + middleEastBuildings + centralAsiaBuildings + indiaBuildings + eastAsiaBuildings +
                    indonesiaBuildings + australasiaBuildings + siberiaBuildings
            ) as MutableMap<String, MutableList<BuildingData>>
}

fun getConsolidatedPopMap(): MutableMap<String, List<PopulationData>> {
    var westEuropePops =
        readPopsFile("src/main/resources/realGame/pops/00_west_europe.txt", RegionalMapping.WEST_EUROPE)
    var southEuropePops =
        readPopsFile("src/main/resources/realGame/pops/01_south_europe.txt", RegionalMapping.SOUTH_EUROPE)
    var eastEuropePops =
        readPopsFile("src/main/resources/realGame/pops/02_east_europe.txt", RegionalMapping.EAST_EUROPE)
    var northAfricaPops =
        readPopsFile("src/main/resources/realGame/pops/03_north_africa.txt", RegionalMapping.NORTH_AFRICA)
    var subsaharanAfricaPops =
        readPopsFile("src/main/resources/realGame/pops/04_subsaharan_africa.txt", RegionalMapping.SUBSAHARAN_AFRICA)
    var northAmericaPops =
        readPopsFile("src/main/resources/realGame/pops/05_north_america.txt", RegionalMapping.NORTH_AMERICAN)
    var centralAmericaPops =
        readPopsFile("src/main/resources/realGame/pops/06_central_america.txt", RegionalMapping.CENTRAL_AMERICAN)
    var southAmericaPops =
        readPopsFile("src/main/resources/realGame/pops/07_south_america.txt", RegionalMapping.SOUTH_AMERICAN)
    var middleEastPops =
        readPopsFile("src/main/resources/realGame/pops/08_middle_east.txt", RegionalMapping.MIDDLE_EAST)
    var centralAsiaPops =
        readPopsFile("src/main/resources/realGame/pops/09_central_asia.txt", RegionalMapping.CENTRAL_ASIA)
    var indiaPops = readPopsFile("src/main/resources/realGame/pops/10_india.txt", RegionalMapping.INDIA)
    var eastAsiaPops = readPopsFile("src/main/resources/realGame/pops/11_east_asia.txt", RegionalMapping.EAST_ASIA)
    var indonesiaPops = readPopsFile("src/main/resources/realGame/pops/12_indonesia.txt", RegionalMapping.INDONESIA)
    var australasiaPops =
        readPopsFile("src/main/resources/realGame/pops/13_australasia.txt", RegionalMapping.AUSTRALASIA)
    var siberiaPops = readPopsFile("src/main/resources/realGame/pops/14_siberia.txt", RegionalMapping.SIBERIA)

    return (
            westEuropePops + southEuropePops + eastEuropePops + northAfricaPops + subsaharanAfricaPops + northAmericaPops +
                    centralAmericaPops + southAmericaPops + middleEastPops + centralAsiaPops + indiaPops + eastAsiaPops +
                    indonesiaPops + australasiaPops + siberiaPops
            ) as MutableMap<String, List<PopulationData>>
}

fun readOptionalConfigurations(path: String) {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()

    val jsonString: String = File(path).readText(Charsets.UTF_8)
    val configs = mapper.readValue<GlobalConfig>(jsonString, GlobalConfig::class.java)
}

fun readColorPalette(path: String): List<RgbColor> {
    val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()

    val jsonString: String = File(path).readText(Charsets.UTF_8)
    return mapper.readValue<List<RgbColor>>(jsonString)
}

fun createLocalization(
    stateMap: MutableMap<String, State>
) {

    var text = ""
    text += format(0, "l_english:", 1)

    stateMap.values.forEach {
        var namesArray = it.stateName.split("_")
        var displayName = ""
        namesArray.forEach {
            var lowercase = it.lowercase()
            displayName = displayName.plus("${lowercase[0].uppercase() + lowercase.substring(1)} ")
        }
        displayName = displayName.trim()

        text += format(0, " ${it.stateName}:0 \"${displayName}\"", 1)
        text += format(0, " ${it.stateName}_ADJ:0 \"${displayName}ian\"", 1)
    }

    printFile("game/localization/history/english/", "custom_countries_l_english.yml", text)
}

fun createBuildingsMap(
    buildingMap: MutableMap<String, MutableList<BuildingData>>
) {

    var text = ""
    text += format(0, "BUILDINGS = {", 1)


    buildingMap.keys.forEach {

        text += format(1, "s:STATE_${it} = {", 1)
        text += format(2, "region_state:${it} = {", 1)

        var buildings = buildingMap.get(it)

        buildings!!.forEach {
            text += format(3, "create_building = {", 1)
            text += format(4, "building = \"${it.buildingName}\"", 1)
            text += format(4, "level = ${it.level}", 1)
            text += format(3, "}", 1)
        }

        text += format(2, "}", 1)
        text += format(1, "}", 1)

    }
    text += format(0, "}", 1)

    printFile("game/common/history/buildings/", "00_world_buildings.txt", text)
}

fun createBuildingsMapOnlyBarracks(
    buildingMap: MutableMap<String, State>
) {

    var text = ""
    text += format(0, "BUILDINGS = {", 1)


    buildingMap.values.forEach {

        text += format(1, "s:STATE_${it.stateName} = {", 1)
        text += format(2, "region_state:${it.stateName} = {", 1)

        text += format(3, "create_building = {", 1)
        text += format(4, "building=\"building_barracks\"", 1)
        text += format(4, "level = 10", 1)
        text += format(3, "}", 1)

        text += format(2, "}", 1)
        text += format(1, "}", 1)

    }
    text += format(0, "}", 1)

    printFile("game/common/history/buildings/", "00_world_buildings.txt", text)

}


fun readBuildingsFile(path: String): MutableMap<String, MutableList<BuildingData>> {
    var buildingMap = mutableMapOf<String, MutableList<BuildingData>>()

    val textOfFile = readFileDirectlyAsText(path)

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
            var buildingLevel = Regex("level=(.*?)\\n").find(buildingText)!!.groupValues[1].toInt()

            var existingLevels = buildingsInTheStateMap.get(buildingName) ?: 0
            var totalLevels = existingLevels + buildingLevel

            buildingsInTheStateMap.set(buildingName, totalLevels)
        }

        var buildingsInTheState = mutableListOf<BuildingData>()
        buildingsInTheStateMap.keys.forEach {
            buildingsInTheState.add(BuildingData(it, buildingsInTheStateMap.get(it)!!.toInt()))
        }
        buildingMap.set(stateName, buildingsInTheState)
    }


    return buildingMap
}

fun readBuildingsFileOutdated(path: String): MutableMap<String, MutableList<BuildingData>> {
    //manually removed any 		"region_state:MEX={
    // }" lines
    val textOfFile = readFileDirectlyAsText(path)

    var removeOutermostBracketsAndCaptureContent = Regex("BUILDINGS=\\{\\n(.*)}", RegexOption.DOT_MATCHES_ALL)

    var textWithoutOutermostbrackets = removeOutermostBracketsAndCaptureContent.find(textOfFile)!!.groupValues[1]

    //(?<=s:STATE_)(.*?)=(.+?) msg  (?<=s:STATE_)(.*?)=(.*?)s:STATE lol (?<=s:STATE_)(.*?)=(.*?).+?(?=s:STATE|$)
    //the holy grail (?<=s:STATE_)(.*?)=\{(.*?).(?=s:STATE|\Z) msg

    val stateInformationSet = Regex(
        "s:STATE_(.*?)=\\{(.*?)\\}\\W*\\}\\W*\\}\\W*\\}\\W*",
        setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
    ).findAll(
        textWithoutOutermostbrackets
    ).toSet()

    var buildingMap = mutableMapOf<String, MutableList<BuildingData>>()
    stateInformationSet.forEach {
        var stateName = it.groupValues[1]
        var stateText = it.groupValues[2]

        if (stateName == "UTAH") {
            println(it)
        }

        var buildingTextSet = Regex(
            "create_building=\\{(.*?)activate",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
        ).findAll(
            stateText
        ).toSet()

        var buildingsInTheStateMap = mutableMapOf<String, Int>()
        buildingTextSet.forEach {
            var buildingText = it.groupValues[1]

            var buildingName = Regex("building=\\\"(.*?)\\\"").find(buildingText)!!.groupValues[1]
            var buildingLevel = Regex("level=(.*?)\\n").find(buildingText)!!.groupValues[1].toInt()

            var existingLevels = buildingsInTheStateMap.get(buildingName) ?: 0
            var totalLevels = existingLevels + buildingLevel

            buildingsInTheStateMap.set(buildingName, totalLevels)
        }

        var buildingsInTheState = mutableListOf<BuildingData>()
        buildingsInTheStateMap.keys.forEach {
            buildingsInTheState.add(BuildingData(it, buildingsInTheStateMap.get(it)!!.toInt()))
        }
        buildingMap.set(stateName, buildingsInTheState)
    }
    return buildingMap
}

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
        (println(it.stateName))

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

        //var color = "color = { ${(0..255).random()} ${(0..255).random()} ${(0..255).random()} }"
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

fun readStatesFile(path: String): MutableMap<String, State> {
    val textOfFile = readFileDirectlyAsText(path)

    var removeOutermostBracketsAndCaptureContent = Regex("\\{\\n(.*)\\n\\}", RegexOption.DOT_MATCHES_ALL)

    var textWithoutOutermostbrackets = removeOutermostBracketsAndCaptureContent.find(textOfFile)!!.groupValues[1]

    val split = textWithoutOutermostbrackets.split("\ts:STATE_")

    // remove empty entry
    val states = split.drop(1)

    var stateMap = mutableMapOf<String, State>()

    for (i in 0 until states.size) {

        var stateName = states[i].split(" =")[0]

        var homelandsMatchSet = Regex("add_homeland.=.(.*)").findAll(states[i]).toSet()
        var homelandList = mutableListOf<String>()

        homelandsMatchSet.forEach {
            homelandList.add(it.groupValues[1].replace("\t", "").replace("\n", ""))
        }

        var provincesMatchSet = Regex("\\{([^{}]*)\\}", RegexOption.DOT_MATCHES_ALL).findAll(states[i]).toSet()
        var provincesList = mutableListOf<String>()

        provincesMatchSet.forEach {
            if (it.groupValues[1].contains("x9A9297")) {
                println("lol")
            }

            //filter random lines breaks and replace with space for split
            var cleanedupProvincesString = it.groupValues[1].replace("\n", " ").replace("\\t+".toRegex(), "")

            cleanedupProvincesString.split(" ").forEach {
                if (!it.isEmpty())
                    provincesList.add(it.replace("\t", "").replace("\n", "").replace(" ", ""))
            }
        }

        stateMap.set(
            i.toString(), State(
                stateName,
                homelandList,
                provincesList
            )
        )
    }

    return stateMap
}

fun createStatesFile(stateMap: MutableMap<String, State>) {

    var text = ""
    text += "STATES = {\n"
    stateMap.values.forEach {
        text += format(1, "s:STATE_${it.stateName} = {", 1)
        text += format(2, "create_state = {", 1)
        text += format(3, "country = c:${it.stateName}", 1)

        var provinces = ""
        it.provinces.forEach {
            provinces += "${it} "
        }
        text += format(3, "owned_provinces = { ${provinces}}", 1)
        text += format(2, "}", 1)
        text += format(0, "", 1)

        it.homelands.forEach {
            text += format(2, "add_homeland = ${it}", 1)
        }
        text += format(1, "}", 1)
    }

    text += format(0, "}", 0)

    printFile("game/common/history/states/", "00_states.txt", text)
}

fun createPopulationsFiles(compactPopMap: MutableMap<String, List<PopulationData>>) {

    compactPopMap.keys.forEach {
        var text = ""
        text += format(0, "POPULATION = {", 1)
        text += format(1, "c:${it} = {", 1)
        text += format(0, "", 1)
        text += format(2, "${compactPopMap.get(it)!!.first().regionalMapping.wealth} = yes", 1)
        text += format(2, "${compactPopMap.get(it)!!.first().regionalMapping.literacy} = yes", 1)
        text += format(1, "}", 1)
        text += format(0, "}", 0)

        printFile("game/common/history/population/", "${it} - ${it}.txt", text)
    }

}

fun readPopsFile(path: String, regionalMapping: RegionalMapping): MutableMap<String, List<PopulationData>> {
    val textOfFile = readFileDirectlyAsText(path)
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

fun createPopsFile(compactPopMap: MutableMap<String, List<PopulationData>>) {

    var text = ""
    text += format(0, "POPS = {", 1)

    compactPopMap.keys.forEach {
        var popList = compactPopMap.get(it)
        text += format(1, "s:STATE_${it} = {", 1)
        text += format(2, "region_state:${it} = {", 1)
        popList!!.forEach {
            text += format(3, "create_pop = {", 1)
            text += format(4, "culture = ${it.culture}", 1)
            text += format(4, "size = ${it.popNumber}", 1)
            if (it.religion != "none") {
                text += format(4, "religion = ${it.religion}", 1)
            }
            text += format(3, "}", 1)
        }
        text += format(2, "}", 1)
        text += format(1, "}", 1)
    }

    text += format(0, "}", 1)

    // input filenames could be used to set literacy and wealth
    printFile("game/common/history/pops/", "00_world.txt", text)

}

fun printFile(path: String, fileName: String, content: String) {
    //val repoPath = "C:/Programming Stuff/ClausewitzParser/VictoriaThree/modFiles/"
    val repoPath = "target/generatedModFiles/"
    val savePath = repoPath.plus(path)
    val directory = File(savePath)
    if (directory.mkdirs()) {
        println("Directory created successfully")
    }

    File(savePath.plus(fileName)).writeText("\uFEFF" + content, Charsets.UTF_8)
}

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

fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)

data class State(
    val stateName: String,
    val homelands: List<String>,
    val provinces: List<String>
)

data class PopulationData(
    var state: String,
    val culture: String,
    var popNumber: Int,
    val religion: String,
    val regionalMapping: RegionalMapping
)

data class BuildingData(
    val buildingName: String,
    var level: Int,

    )

data class GlobalConfig(
    val stateConfig: StateConfigMap
)

data class StateConfigMap(
    val statesMap: Map<String, StateDetails>
)

data class StateDetails(
    val state: String,
    val rgb: String?,
    val localizationName: String?,
    val localizationAdjective: String?,
    val technologyPreset: Int?,
    val literacyPreset: Int?
)

enum class RegionalMapping(val literacy: String, val wealth: String, val technologyBase: String) {
    WEST_EUROPE(
        "effect_starting_pop_literacy_middling",
        "effect_starting_pop_wealth_medium",
        "effect_starting_technology_tier_1_tech"
    ),
    SOUTH_EUROPE(
        "effect_starting_pop_literacy_middling",
        "effect_starting_pop_wealth_medium",
        "effect_starting_technology_tier_2_tech"
    ),
    EAST_EUROPE(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_medium",
        "effect_starting_technology_tier_3_tech"
    ),
    NORTH_AFRICA(
        "effect_starting_pop_literacy_baseline",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_5_tech"
    ),
    SUBSAHARAN_AFRICA(
        "effect_starting_pop_literacy_baseline",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_6_tech"
    ),
    NORTH_AMERICAN(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_3_tech"
    ),
    CENTRAL_AMERICAN(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_4_tech"
    ),
    SOUTH_AMERICAN(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_5_tech"
    ),
    MIDDLE_EAST(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_4_tech"
    ),
    CENTRAL_ASIA(
        "effect_starting_pop_literacy_baseline",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_6_tech"
    ),
    INDIA(
        "effect_starting_pop_literacy_baseline",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_5_tech"
    ),
    EAST_ASIA(
        "effect_starting_pop_literacy_baseline",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_5_tech"
    ),
    INDONESIA(
        "effect_starting_pop_literacy_baseline",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_6_tech"
    ),
    AUSTRALASIA(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_3_tech"
    ),
    SIBERIA(
        "effect_starting_pop_literacy_very_low",
        "effect_starting_pop_wealth_low",
        "effect_starting_technology_tier_5_tech"
    )
}

data class RgbColor(
    @JsonProperty("R")
    val red: String,
    @JsonProperty("G")
    val green: String,
    @JsonProperty("B")
    val blue: String
) {
    override fun toString(): String {
        return "$red $green $blue"
    }

    fun toRange(range: Int): String {
        var rangedRed = toRgbLimit((-range..range).random() + red.toInt())
        var rangedGreen = toRgbLimit((-range..range).random() + green.toInt())
        var rangedBlue = toRgbLimit((-range..range).random() + blue.toInt())

        return "$rangedRed $rangedGreen $rangedBlue"
    }

    fun toRangeSingle(range: Int): String {
        return when ((1..3).random()) {
            1 -> "${toRgbLimit((-range..range).random() + red.toInt())} $green $blue"
            2 -> "$red ${toRgbLimit((-range..range).random() + green.toInt())} $blue"
            3 -> "$red $green ${toRgbLimit((-range..range).random() + blue.toInt())}"
            else -> throw RuntimeException("Random number not matching")
        }
    }

    private fun toRgbLimit(value: Int): Int {
        return when (value) {
            in 0..255 -> value
            in 256..Int.MAX_VALUE -> 255
            else -> 0
        }
    }
}
