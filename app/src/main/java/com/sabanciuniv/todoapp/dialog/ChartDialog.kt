package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.sabanciuniv.todoapp.databinding.ChartDialogBinding
import com.sabanciuniv.todoapp.model.Food

class ChartDialog(context: Context, var consumed: MutableList<Food>, var currentGoal: Int ):Dialog(context) {
    private var binding: ChartDialogBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChartDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        window!!.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding!!.barChart.axisRight?.setDrawLabels(false)
        val entries: ArrayList<BarEntry> = arrayListOf()
        entries.add(BarEntry(0f,45f))
        entries.add(BarEntry(1f,67f))
        entries.add(BarEntry(2f,35f))
        entries.add(BarEntry(3f,15f))
        entries.add(BarEntry(4f,145f))
        binding!!.barChart.axisLeft.axisMinimum = 0f
        binding!!.barChart.axisLeft.axisMaximum = 200f
        binding!!.barChart.axisLeft.axisLineWidth = 2f
        binding!!.barChart.axisLeft.axisLineColor = Color.YELLOW
        binding!!.barChart.axisLeft.labelCount = 10
        val dataSet: BarDataSet = BarDataSet(entries, "Test Label")
        dataSet.colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
        val barData: BarData = BarData(dataSet)
        binding!!.barChart.data = barData
        binding!!.barChart.description.isEnabled = false
        binding!!.barChart.invalidate()
        binding!!.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding!!.barChart.xAxis.granularity = 1f
        binding!!.barChart.xAxis.isGranularityEnabled = true


    }
}