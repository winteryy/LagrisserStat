package com.winterry.langrisserstat.db

object MapData {

    private val MAPS = mapOf(
        11 to "분쟁의 평원",
        12 to "눈덮인 벌판",
        13 to "눈물의 산길",
        14 to "하늘정원",
        15 to "사막의 눈",
        16 to "트윈 브릿지의 파도",
        17 to "빛이 비치는 동굴",
        18 to "비경의 계류",
        19 to "깎아지른 듯한 절벽",
        20 to "서풍초소"
    )

    fun getMaps(): Map<Int, String> {
        return MAPS
    }
}