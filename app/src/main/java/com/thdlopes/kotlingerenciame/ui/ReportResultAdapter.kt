package com.thdlopes.kotlingerenciame.ui

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thdlopes.kotlingerenciame.data.Finance
import com.thdlopes.kotlingerenciame.databinding.RecyclerViewReportResultBinding

class ReportResultAdapter: RecyclerView.Adapter<ReportResultAdapter.ViewHolder>() {

    var finances = mutableListOf<Finance>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewReportResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var value :String? = null //setText não aceita concatenação
        var total = 0.0F;
        for ( i in 0 until  finances.size){
            when(finances[i].moviment){
                "Ganho" -> total += finances[i].value.toString().toFloat()
                "Gasto" -> total -= finances[i].value.toString().toFloat()
            }
        }
        if (total > 0.0F){
            value = "+"
            holder.binding.textViewResultReport.setTextColor(Color.parseColor("#008000"))
        } else if (total < 0.0F){
            holder.binding.textViewResultReport.setTextColor(Color.parseColor("#cc0000"))
        } else holder.binding.textViewResultReport.setTextColor(Color.parseColor("#d3d3d3"))

        value += total.toString().trim()
        holder.binding.textViewResultReport.text = value
    }

    override fun getItemCount(): Int {
        return 1
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

    inner class ViewHolder (val binding: RecyclerViewReportResultBinding): RecyclerView.ViewHolder(binding.root){
    }

}