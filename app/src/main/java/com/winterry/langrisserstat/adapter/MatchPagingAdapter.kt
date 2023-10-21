package com.winterry.langrisserstat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.winterry.langrisserstat.LangrisserStatApplication
import com.winterry.langrisserstat.R
import com.winterry.langrisserstat.databinding.ItemMatchBinding
import com.winterry.langrisserstat.db.MapData
import com.winterry.langrisserstat.db.repository.MatchData
import com.winterry.langrisserstat.setHeroImage

class MatchPagingAdapter(private val matchClickListener: MatchClickListener)
    : PagingDataAdapter<MatchData, MatchPagingAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemMatchBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MatchData) {
            binding.dateTextView.text = item.date.toString()
            binding.mapNameTextView.text = MapData.getMaps()[item.map]
            binding.resultTextView.text = if(item.isWin) WIN_TEXT else DEFEAT_TEXT
            binding.turnHandTextView.text = if(item.isFirstHand) FIRST_HAND_TEXT else SECOND_HAND_TEXT
            binding.memoTextView.text = item.memo

            binding.myHero1ImageView.setHeroImage(item.myHeroes[0])
            binding.myHero2ImageView.setHeroImage(item.myHeroes[1])
            binding.myHero3ImageView.setHeroImage(item.myHeroes[2])
            binding.myHero4ImageView.setHeroImage(item.myHeroes[3])
            binding.myHero5ImageView.setHeroImage(item.myHeroes[4])
            binding.enemyHero1ImageView.setHeroImage(item.enemyHeroes[0])
            binding.enemyHero2ImageView.setHeroImage(item.enemyHeroes[1])
            binding.enemyHero3ImageView.setHeroImage(item.enemyHeroes[2])
            binding.enemyHero4ImageView.setHeroImage(item.enemyHeroes[3])
            binding.enemyHero5ImageView.setHeroImage(item.enemyHeroes[4])

            binding.deleteMatchButton.setOnClickListener {
                matchClickListener.onClick(item.id)
            }
        }

        fun setBgWin() {
            binding.backGroundView.setBackgroundColor(WIN_COLOR)
        }

        fun setBgDefeat() {
            binding.backGroundView.setBackgroundColor(DEFEAT_COLOR)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            if(it.isWin) {
                holder.setBgWin()
            } else {
                holder.setBgDefeat()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMatchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    interface MatchClickListener {
        fun onClick(matchId: Long)
    }

    companion object {
        private const val WIN_TEXT = "승리"
        private const val DEFEAT_TEXT = "패배"
        private const val FIRST_HAND_TEXT = "(선)"
        private const val SECOND_HAND_TEXT = "(후)"

        private val WIN_COLOR = LangrisserStatApplication.getApplicationContext().getColor(R.color.win_bg)
        private val DEFEAT_COLOR = LangrisserStatApplication.getApplicationContext().getColor(R.color.lose_bg)

        val diffCallback = object : DiffUtil.ItemCallback<MatchData>() {
            override fun areItemsTheSame(oldItem: MatchData, newItem: MatchData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MatchData, newItem: MatchData): Boolean {
                return oldItem == newItem
            }

        }
    }


}