package org.victoriaThreeShatterer.models

import org.victoriaThreeShatterer.utils.RegionalMapping

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
    var level: Int
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

data class CombinedStateData(
    val stateName: String,
    val populationList: List<PopulationData>,
    val homelands: List<String>,
    val provinces: List<String>
)