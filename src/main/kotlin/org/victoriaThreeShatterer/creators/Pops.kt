package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.CombinedStateData
import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createPopulationsFiles(compactPopMap: MutableMap<String, List<PopulationData>>) {
    var text = ""
    text += format(0, "POPULATION = {", 1)

    compactPopMap.keys.forEach {
        text += format(1, "c:${it} = {", 1)
        text += format(2, "${compactPopMap.get(it)!!.first().regionalMapping.wealth} = yes", 1)
        text += format(2, "${compactPopMap.get(it)!!.first().regionalMapping.literacy} = yes", 1)
        text += format(1, "}", 1)
    }

    text += format(0, "}", 0)

    printFile("game/common/history/population/", "00_population.txt", text)
}

fun createPopulationsFilesProvinceMode(combinedStateData: MutableMap<String, CombinedStateData>) {
    println("start createPopulationsFilesProvinceMod")

    var text = ""
    text += format(0, "POPULATION = {", 1)

    combinedStateData.forEach { combinedData ->
        combinedData.value.provinces.forEach {
            text += format(1, "c:${it}land = {", 1)
            text += format(0, "", 1)
            text += format(2, "${combinedData.value.populationList.first().regionalMapping.wealth} = yes", 1)
            text += format(2, "${combinedData.value.populationList.first().regionalMapping.literacy} = yes", 1)
            text += format(1, "}", 1)
        }
    }
    text += format(0, "}", 0)
    printFile("game/common/history/population/", "00_all_population.txt", text)

    println("finished createPopulationsFilesProvinceMod")
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

fun createPopsFileProvinceMode(combinedStateData: MutableMap<String, CombinedStateData>) {
    println("start createPopsFileProvinceMode")

    var text = ""
    text += format(0, "POPS = {", 1)

    combinedStateData.forEach { dataEntry ->
        println("working on ${dataEntry.key}")
        var popList = dataEntry.value.populationList
        text += format(1, "s:STATE_${dataEntry.key} = {", 1)

        dataEntry.value.provinces.forEach { province ->
            text += format(2, "region_state:${province}land = {", 1)

            //so it doesnt load 4ever
            val simpleFiedPopList = listOf(popList.first())

            simpleFiedPopList!!.forEach {
                val popFragment = it.popNumber/ dataEntry.value.provinces.size

                text += format(3, "create_pop = {", 1)
                text += format(4, "culture = ${it.culture}", 1)
                text += format(4, "size = ${popFragment}", 1)
                if (it.religion != "none") {
                    text += format(4, "religion = ${it.religion}", 1)
                }
                text += format(3, "}", 1)
            }
            text += format(2, "}", 1)
            text += format(0, "", 1)
        }

        text += format(1, "}", 1)
    }

    text += format(0, "}", 1)

    // input filenames could be used to set literacy and wealth
    printFile("game/common/history/pops/", "00_world.txt", text)

    println("finished createPopsFileProvinceMode")
}