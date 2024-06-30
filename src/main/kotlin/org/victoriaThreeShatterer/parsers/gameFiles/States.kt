package org.victoriaThreeShatterer.parsers.gameFiles

import org.victoriaThreeShatterer.models.State
import org.victoriaThreeShatterer.utils.readFileAsText

fun readStatesFile(path: String): MutableMap<String, State> {
    val textOfFile = readFileAsText(path)

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
            //filter random lines breaks and replace with space for split
            var cleanedupProvincesString = it.groupValues[1].replace("\n", " ").replace("\\t+".toRegex(), " ")

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