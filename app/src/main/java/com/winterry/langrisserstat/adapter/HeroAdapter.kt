package com.winterry.langrisserstat.adapter

import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.winterry.langrisserstat.LangrisserStatApplication.Companion.getApplicationContext
import com.winterry.langrisserstat.R
import com.winterry.langrisserstat.databinding.ItemHeroBinding
import com.winterry.langrisserstat.db.HeroData
import com.winterry.langrisserstat.setHeroImage

class HeroAdapter(val onItemClickListener: OnItemClickListener) :
    ListAdapter<HeroData.Hero, HeroAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HeroData.Hero) {
            binding.heroNameTextView.text = item.name

            binding.heroImageView.setHeroImage(item.id)

            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(item)
                if(item.selected) {
                    setSelectBlue()
                } else {
                    setSelectTrans()
                }
            }
        }

        fun setSelectBlue() {
            binding.heroImageView.setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.transparent_blue)
            )
        }

        fun setSelectTrans() {
            binding.heroImageView.colorFilter = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHeroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        if(currentList[position].selected) {
            holder.setSelectBlue()
        } else {
            holder.setSelectTrans()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HeroData.Hero>() {
            override fun areItemsTheSame(oldItem: HeroData.Hero, newItem: HeroData.Hero): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HeroData.Hero,
                newItem: HeroData.Hero
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: HeroData.Hero)
    }
}