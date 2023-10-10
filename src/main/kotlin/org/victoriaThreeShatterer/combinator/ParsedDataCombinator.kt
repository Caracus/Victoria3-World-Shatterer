package org.victoriaThreeShatterer.combinator

import org.victoriaThreeShatterer.models.CombinedStateData
import org.victoriaThreeShatterer.models.PopulationData
import org.victoriaThreeShatterer.models.State

fun combineStateData(populationMap: MutableMap<String, List<PopulationData>>, stateMap: MutableMap<String, State>): MutableMap<String, CombinedStateData>{

    var combinedStateDataList = mutableMapOf<String, CombinedStateData>()

    stateMap.values.forEach{
        combinedStateDataList.put(
            it.stateName,
            CombinedStateData(
                stateName = it.stateName,
                populationList = populationMap[it.stateName]?.toList() ?: throw Error("No pop data for ${it.stateName}"),
                it.homelands,
                it.provinces
                    .map { it.replace("\"", "") }
            )
        )
    }

    return combinedStateDataList
}
