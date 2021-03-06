package com.thdlopes.kotlingerenciame.ui

import android.graphics.Color
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
        if (finances[position].moviment == "Ganho") {
            holder.binding.textViewSymbol.text = "+ "
            holder.binding.textViewSymbol.setTextColor(Color.parseColor("#008000"))
            holder.binding.textViewValue.setTextColor(Color.parseColor("#008000"))
        } else {
            holder.binding.textViewSymbol.text = "- "
            holder.binding.textViewSymbol.setTextColor(Color.parseColor("#cc0000"))
            holder.binding.textViewValue.setTextColor(Color.parseColor("#cc0000"))
        }
    }

    override fun getItemCount(): Int {
        return finances.size
    }

    fun addFinance(finance: Finance){
        if(!finances.contains(finance)){
            finances.add(finance)
        }else{
            val index = finances.indexOf(finance)
            if(finance.isDeleted){
                finances.removeAt(index)
            }else {
                finances[index] = finance
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder (val binding: RecyclerViewFinanceBinding): RecyclerView.ViewHolder(binding.root){
    }

}