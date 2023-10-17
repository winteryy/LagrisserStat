package com.winterry.langrisserstat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.winterry.langrisserstat.adapter.MatchBindingAdapter
import com.winterry.langrisserstat.databinding.FragmentRecordBinding
import com.winterry.langrisserstat.viewmodel.MatchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordFragment: Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private lateinit var adapter: MatchBindingAdapter

    private val viewModel: MatchViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = MatchBindingAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addMatchButton.setOnClickListener {
            startActivity(Intent(this.context, AddMatchActivity::class.java))
        }

        binding.matchRecyclerView.apply {
            adapter = this@RecordFragment.adapter
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.items.collect {
                adapter.submitData(it)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}