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
import com.sabanciuniv.todoapp.model.RecentDayData

class ChartDialog(context: Context, var recentWeekList: MutableList<RecentDayData>, var currentGoal: Int ):Dialog(context) {
    private var binding: ChartDialogBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChartDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        window!!.setLayout(
            ViewGroup.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val chart = binding!!.barChart
        chart.axisRight?.setDrawLabels(false)
        val entries: ArrayList<BarEntry> = arrayListOf()
        entries.add(BarEntry(0f,1145f))
        entries.add(BarEntry(1f,2467f))
        entries.add(BarEntry(2f,3531f))
        entries.add(BarEntry(3f,1875f))
        entries.add(BarEntry(4f,4000f))
        entries.add(BarEntry(5f,2842f))
        entries.add(BarEntry(6f,1548f))

        chart.axisLeft.axisMinimum = 1000f
        chart.axisLeft.axisMaximum = 4000f
        chart.axisLeft.axisLineWidth = 2f
        chart.axisLeft.axisLineColor = Color.YELLOW
        chart.axisLeft.labelCount = 10
        val dataSet: BarDataSet = BarDataSet(entries, "Previous 7 Days Data (Excluding Missing)")
        dataSet.colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
        val barData: BarData = BarData(dataSet)
        chart.data = barData
        chart.description.isEnabled = false
        chart.invalidate()
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.granularity = 20f
        chart.xAxis.isGranularityEnabled = true


    }
}