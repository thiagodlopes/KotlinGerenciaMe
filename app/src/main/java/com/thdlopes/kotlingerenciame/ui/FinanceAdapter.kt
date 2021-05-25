package com.thdlopes.kotlingerenciame.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thdlopes.kotlingerenciame.data.Finance
import com.thdlopes.kotlingerenciame.databinding.RecyclerViewFinanceBinding

class FinanceAdapter: RecyclerView.Adapter<FinanceAdapter.ViewHolder>() {

    var finances = mutableListOf<Finance>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewFinanceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewName.text = finances[position].name
        holder.binding.textViewDate.text = (finances[position].day + "/" + finances[position].month + "/" + finances[position].year )
        holder.binding.textViewValue.text = finances[position].value
    }

    override fun getItemCount(): Int {
        return finances.size
    }

    fun addFinance(finance: Finance){
        if(!finances.contains(finance)){
            finances.add(finance)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder (val binding: RecyclerViewFinanceBinding): RecyclerView.ViewHolder(binding.root){
    }

}