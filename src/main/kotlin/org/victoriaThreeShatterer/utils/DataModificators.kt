package org.victoriaThreeShatterer.utils

import org.victoriaThreeShatterer.models.BuildingData

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