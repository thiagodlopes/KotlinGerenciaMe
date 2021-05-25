package com.thdlopes.kotlingerenciame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.thdlopes.kotlingerenciame.R
import com.thdlopes.kotlingerenciame.data.FinanceViewModel
import com.thdlopes.kotlingerenciame.databinding.FragmentFinanceBinding


class FinanceFragment : Fragment() {

    private var _binding: FragmentFinanceBinding? = null
    private val binding get() = _binding!!

    private val adapter = FinanceAdapter()

    private lateinit var viewModel: FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(FinanceViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewFinances.adapter = adapter

        binding.addButton.setOnClickListener {
            AddFinanceDialogFragment().show(childFragmentManager, "")
        }

        viewModel.finance.observe(viewLifecycleOwner, Observer {
            adapter.addFinance(it)
        })

        viewModel.getRealTimeUpdate()

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewFinances)

    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            var currentFinance = adapter.finances[position]

            when(direction){
                ItemTouchHelper.RIGHT -> {
                    UpdateFinanceDialogFragment(currentFinance).show(childFragmentManager, "")
                }
            }
            binding.recyclerViewFinances.adapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}