package org.victoriaThreeShatterer.creators

import org.victoriaThreeShatterer.models.RgbColor
import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.format
import org.victoriaThreeShatterer.utils.printFile

fun createAiStartingStrategies(stateMap: MutableMap<String, State>){

    var text = ""
    text += format(0, "AI = {", 1)

    stateMap.forEach { state ->
        text += format(1, "c:${state.value.stateName} = {", 1)
        text += format(2, "set_strategy = ai_strategy_industrial_expansion", 1)
        text += format(2, "set_strategy = ai_strategy_territorial_expansion ", 1)
        text += format(2, "set_strategy = ai_strategy_nationalist_agenda", 1)
        text += format(1, "}", 1)

    }
    text += format(0, "}", 1)
    printFile("game/common/history/ai/", "00_strategy.txt", text)
    println("Created starting Ai strategies")
}
