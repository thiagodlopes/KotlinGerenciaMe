package com.thdlopes.kotlingerenciame.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thdlopes.kotlingerenciame.data.Finance
import com.thdlopes.kotlingerenciame.databinding.RecyclerViewReportBinding

class ReportAdapter: RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    var finances = mutableListOf<Finance>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewReportBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var value :String //setText não aceita concatenação
        holder.binding.textViewName.text = finances[position].name
        holder.binding.textViewDate.text = (finances[position].day + "/" + finances[position].month + "/" + finances[position].year )

        if (finances[position].moviment == "Ganho") {
            value = "+" + finances[position].value
            holder.binding.textViewValue.text = value
            holder.binding.textViewValue.setTextColor(Color.parseColor("#008000"))
        } else {
            value = "-" + finances[position].value
            holder.binding.textViewValue.text = value
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

    inner class ViewHolder (val binding: RecyclerViewReportBinding): RecyclerView.ViewHolder(binding.root){
    }

}