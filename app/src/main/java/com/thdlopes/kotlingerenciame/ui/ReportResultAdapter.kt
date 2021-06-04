package com.thdlopes.kotlingerenciame.ui

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thdlopes.kotlingerenciame.data.Finance
import com.thdlopes.kotlingerenciame.databinding.RecyclerViewReportResultBinding
import java.math.BigDecimal

class ReportResultAdapter: RecyclerView.Adapter<ReportResultAdapter.ViewHolder>() {

    var finances = mutableListOf<Finance>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewReportResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var value :String? = "" //setText não aceita concatenação
        val zero = BigDecimal("0.00")
        var total = BigDecimal("0.00")
        var gains = BigDecimal("0.00")
        var losses = BigDecimal("0.00")
        for ( i in 0 until  finances.size){
            when(finances[i].moviment){
                "Ganho" -> gains += (finances[i].value.toString().toBigDecimal())
                "Gasto" -> losses += (finances[i].value.toString().toBigDecimal())
            }
        }
        total += gains
        total -= losses
        if (total > zero){
            value = "+"
            holder.binding.textViewResultReport.setTextColor(Color.parseColor("#008000"))
        } else if (total < zero){
            value = ""
            holder.binding.textViewResultReport.setTextColor(Color.parseColor("#cc0000"))
        } else holder.binding.textViewResultReport.setTextColor(Color.parseColor("#d3d3d3"))

        value += total.toString()
        holder.binding.textViewResultReport.text = value
        holder.binding.textViewGainReport.text = gains.toString()
        holder.binding.textViewLossReport.text = losses.toString()
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