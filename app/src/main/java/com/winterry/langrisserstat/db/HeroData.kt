package com.winterry.langrisserstat.db

object HeroData {

    private val HEROES_MAP = mapOf(
        101 to "매튜",
        102 to "아멜다",
        103 to "그레니어",
        104 to "아론",
        105 to "안나",
        106 to "디오스",
        107 to "키스",
        108 to "제시카",
        109 to "스코트",
        110 to "로우가",
        111 to "루인",
        112 to "레아드",
        113 to "피엘",
        114 to "레티시아",
        115 to "레스터",
        116 to "소피아",
        117 to "페라키아",
        118 to "프레아",
        119 to "발가스",
        120 to "파나",
        121 to "나암",
        122 to "크리스",
        123 to "키리카제",
        124 to "실버울프",
        125 to "소니아",
        126 to "에마링크",
        127 to "이멜다",
        128 to "리파니",
        129 to "에그베르트",
        130 to "헤인",
        131 to "란스",
        132 to "보젤",
        133 to "레딘",
        134 to "레온",
        135 to "베른하르트",
        136 to "라나",
        137 to "엘윈",
        138 to "쉐리",
        139 to "리아나",
        140 to "알테뮬러",
        141 to "루나",
        142 to "디하르트",
        143 to "티아리스",
        144 to "쥬그라",
        145 to "젤다",
        146 to "제리올&레이라",
        147 to "셀파닐",
        148 to "안젤리나",
        149 to "란포드",
        150 to "리스틸",
        151 to "에스텔",
        152 to "요슈아",
        153 to "클로제",
        154 to "레온하르트",
        155 to "렌",
        156 to "올리비에",
        157 to "란디우스",
        158 to "레이첼",
        159 to "윌러",
        160 to "기자로프",
        161 to "세레나",
        162 to "시그마",
        163 to "람다",
        164 to "신구지 사쿠라",
        165 to "칸자키 스미레",
        166 to "아이리스",
        167 to "안젤리카",
        168 to "클라렛",
        169 to "엘라스타",
        170 to "오메가",
        171 to "유리아",
        172 to "우라메시 유스케",
        173 to "쿠라마",
        174 to "쿠와바라 카즈마",
        175 to "히에이",
        176 to "도구로 형제",
        177 to "레인폴스",
        178 to "베티",
        179 to "알프레드",
        180 to "환생 제시카",
        181 to "에밀리아",
        182 to "비라쥬",
        183 to "미지의 기사",
        184 to "아카야",
        185 to "브렌다",
        186 to "올리버",
        187 to "디드리트",
        188 to "아슈람",
        189 to "판",
        190 to "필로테스",
        191 to "아레스",
        192 to "마이야",
        193 to "일루시아",
        194 to "실린카",
        195 to "리코리스",
        196 to "레나타",
        197 to "린",
        198 to "아리안로드",
        199 to "알티나",
        200 to "로자리아",
        201 to "노에미",
        202 to "헬레나",
        203 to "세계수의 현자",
        204 to "플로렌티아",
        205 to "츠바메",
        206 to "아인즈 울 고운",
        207 to "알베도",
        208 to "샤르티아",
        209 to "멜파니",
        210 to "로젠실",
        211 to "클로테르",
        212 to "탄생의 빛",
        213 to "마리엘",
        214 to "힐다",
        215 to "베르너",
        216 to "이쿠사베 와타루",
        217 to "시노비베 히미코",
        218 to "츠루기베 시바라쿠",
        219 to "사신 크루거",
        220 to "빈센트",
        221 to "토와",
        222 to "팟시르",
        223 to "크림조의 왕",
        224 to "화이트 시시",
        225 to "사나다 료",
        226 to "카유라",
        227 to "하시바 토마",
        228 to "오토크라트 4세",
        229 to "루크레치아",
        230 to "입실론",
        231 to "뮤",
        232 to "크리스티아네",
        233 to "수제트",
        234 to "초월자",
        235 to "알파",
        236 to "베르너 폰 에길",
        237 to "마리안델",
        238 to "웨탐",
        239 to "엘마",
        240 to "켈티스",
        241 to "로비나 대제",
        242 to "방주의 성녀",
        243 to "구스타프",
        244 to "미셸",
        245 to "리사",
        246 to "아이언 챈슬러",
        247 to "크림조의 집정관",
        248 to "셀베리아",
        249 to "알리시아",
        250 to "이사라",
        251 to "아즈사",
        252 to "오보로",
        253 to "카구야",
        254 to "캐롤리안",
        255 to "아사멜",
        256 to "로스탐",
        257 to "방랑투사",
        258 to "줄리안",
        259 to "리키",
        260 to "마크렌",
        261 to "사카타 긴토키",
        262 to "카구라",
        263 to "시무라 신파치",
        264 to "아단켈모",
        265 to "빛의 수호사도",
        266 to "빛과 그림자 검의 영혼",
        267 to "껍질 소녀",
        268 to "리오벡",
        269 to "시엔",
        270 to "폴리알",
        271 to "류그너",
        272 to "그렌실",
        273 to "요아&코니",
        274 to "아니에스",
        275 to "일레인",
        276 to "반",
        277 to "각성자",

        801 to "SP 매튜",
        802 to "SP 그레니어",
        803 to "SP 루인",
        804 to "SP 프레아",
        805 to "SP 나암",
        806 to "SP 헤인",
        807 to "SP 레딘",
        808 to "SP 레온",
        809 to "SP 라나",
        810 to "SP 엘윈",
        811 to "SP 쉐리",
        812 to "SP 알테뮬러",
        813 to "SP 디하르트",
        814 to "SP 시그마"
    )

    private val HEROES: List<Hero> by lazy {
        HEROES_MAP.map {
            Hero(it.key, it.value)
        }.toList()
    }

    fun getHeroesMap(): Map<Int, String> {
        return HEROES_MAP
    }

    fun getHeroes(): List<Hero> {
        return HEROES
    }

    data class Hero(
        val id: Int,
        val name: String,
        var selected: Boolean = false,
    )
}