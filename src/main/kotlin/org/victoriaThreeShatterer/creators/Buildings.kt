package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.BuildingData
import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createBuildingsFile(
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

fun createBuildingsOnlyBarracks(
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