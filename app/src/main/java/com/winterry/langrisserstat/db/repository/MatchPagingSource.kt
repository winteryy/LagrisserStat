package com.winterry.langrisserstat.db.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.coroutines.delay

private const val STARTING_KEY = 1

class MatchPagingSource(private val langRepo: LangrisserRepository): PagingSource<Int, MatchData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MatchData> {

        return try {
            val page = params.key ?: STARTING_KEY

            if(page != STARTING_KEY) {
                delay(1000)
            }

            val matchList = langRepo.getMatchData((page-1)*params.loadSize, params.loadSize)

            val data = matchList.map { matchEntity ->
                val matchHeroList = langRepo.getMatchHeroData(matchEntity.id)
                val myHeroList = matchHeroList.filter {
                    it.isMyHero
                }.map {
                    it.heroId
                }
                val enemyHeroList = matchHeroList.filter {
                    !it.isMyHero
                }.map {
                    it.heroId
                }

                MatchData(
                    id = matchEntity.id,
                    date = matchEntity.date,
                    isFirstHand = matchEntity.isFirstHand,
                    isWin = matchEntity.isWin,
                    map = matchEntity.map,
                    memo = matchEntity.memo,
                    myHeroes = myHeroList,
                    enemyHeroes = enemyHeroList
                )
            }

            LoadResult.Page(
                data = data,
                prevKey = if(page==1) null else page-1,
                nextKey = page + params.loadSize/20
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MatchData>): Int? {
        val anchorPosition = state.anchorPosition
        return anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?:state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}

