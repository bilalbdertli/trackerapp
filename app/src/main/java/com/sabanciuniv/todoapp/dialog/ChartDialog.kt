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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.sabanciuniv.todoapp.databinding.ChartDialogBinding
import com.sabanciuniv.todoapp.model.Food
import com.sabanciuniv.todoapp.model.RecentDayData

class ChartDialog(context: Context, var recentWeekList: MutableList<RecentDayData>, var currentGoal: Int ):Dialog(context) {
    private var binding: ChartDialogBinding? = null
    private val days = listOf("16 ", "17 ", "18 ",
        "19 ", "20 ","21 ", "22 ")
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
        val newEntries: ArrayList<BarEntry> = arrayListOf()
        newEntries.add(BarEntry(0f,2145f))
        newEntries.add(BarEntry(1f,1467f))
        newEntries.add(BarEntry(2f,1531f))
        newEntries.add(BarEntry(3f,3875f))
        newEntries.add(BarEntry(4f,2000f))
        newEntries.add(BarEntry(5f,3842f))
        newEntries.add(BarEntry(6f,2548f))
        chart.axisLeft.axisMinimum = 1000f
        chart.axisLeft.axisMaximum = 4000f
        chart.axisLeft.axisLineWidth = 2f
        chart.axisLeft.axisLineColor = Color.BLACK
        chart.axisLeft.labelCount = 5
        val dataSet: BarDataSet = BarDataSet(entries, "Goal for day")
        val secDataSet: BarDataSet = BarDataSet(newEntries , "Consumed in day")
        dataSet.colors = List(1){ Color.GRAY}
        secDataSet.colors = List(1){ Color.CYAN}
        val barData: BarData = BarData(dataSet, secDataSet)
        chart.data = barData
        chart.description.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.granularity = 20f
        chart.xAxis.isGranularityEnabled = true
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(days)
        chart.isDragEnabled = true
        chart.setVisibleXRangeMaximum(3.5f)
        barData.barWidth = 0.15f
        chart.xAxis.axisMinimum = 0f
        chart.groupBars(0f, 0.45f, 0.1f)
        chart.invalidate()



    }
}