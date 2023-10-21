package com.winterry.langrisserstat

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.winterry.langrisserstat.adapter.MatchPagingAdapter
import com.winterry.langrisserstat.databinding.FragmentRecordBinding
import com.winterry.langrisserstat.db.repository.MatchData
import com.winterry.langrisserstat.viewmodel.MatchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordFragment: Fragment(), MatchPagingAdapter.MatchClickListener {

    private var _binding: FragmentRecordBinding? = null
    private lateinit var adapter: MatchPagingAdapter

    private val viewModel: MatchViewModel by viewModels()

    private val binding get() = _binding!!

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == RESULT_OK) {
            adapter.refresh()
            binding.matchRecyclerView.smoothScrollToPosition(0)
        } else {
            return@registerForActivityResult
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        val view = binding.root
        adapter = MatchPagingAdapter(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addMatchButton.setOnClickListener {
            activityResultLauncher.launch(Intent(this.context, AddMatchActivity::class.java))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(matchId: Long) {
        AlertDialog.Builder(this.requireContext())
            .setNegativeButton("취소", null)
            .setPositiveButton("삭제") { _, _ ->
                viewModel.deleteMatch(matchId)
                adapter.refresh()
            }
            .setTitle("삭제 확인")
            .setMessage("해당 전적을 삭제하시겠습니까?")
            .create()
            .show()

    }

}