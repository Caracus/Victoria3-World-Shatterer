package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

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