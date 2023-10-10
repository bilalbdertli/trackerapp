package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.sabanciuniv.todoapp.databinding.ChartDialogBinding
import com.sabanciuniv.todoapp.model.ChartData
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
        chart.axisLeft.axisMinimum = 1000f
        chart.axisLeft.axisMaximum = 4000f
        chart.axisLeft.axisLineWidth = 2f
        chart.axisLeft.axisLineColor = Color.BLACK
        chart.axisLeft.labelCount = 5
        chart.description.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.granularity = 20f
        chart.xAxis.isGranularityEnabled = true
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(days)

        val recentWeek: MutableList<RecentDayData> = mutableListOf(RecentDayData("16 Nov", 2500, 3200),
            RecentDayData("17 Nov", 2500, 2200),RecentDayData("18 Nov", 3500, 3200),
            RecentDayData("19 Nov", 3500, 3750),RecentDayData("20 Nov", 3000, 2300),
            RecentDayData("21 Nov", 2800, 2130),RecentDayData("16 Nov", 2500, 3200))
        var columnNum: Int = 0
        val higherCaloryGoalList = mutableListOf<ChartData>()
        val higherEarnedCaloriesList = mutableListOf<ChartData>()
        recentWeek.forEach { dayData ->
            if (dayData.caloryGoal > dayData.earnedCalories) {
                higherCaloryGoalList.add(ChartData(columnNum, dayData.caloryGoal, dayData.earnedCalories))
            } else if (dayData.earnedCalories > dayData.caloryGoal) {
                higherEarnedCaloriesList.add(ChartData(columnNum, dayData.caloryGoal, dayData.earnedCalories))
            }
            columnNum += 1
        }
        val trialFirstEntries: ArrayList<BarEntry> = arrayListOf()
        trialFirstEntries.add(BarEntry(0f,3000f))
        trialFirstEntries.add(BarEntry(1f,3000f))
        trialFirstEntries.add(BarEntry(2f,3900f))
        trialFirstEntries.add(BarEntry(3f,3970f))
        trialFirstEntries.add(BarEntry(4f,4000f))
        trialFirstEntries.add(BarEntry(5f,3970f))
        trialFirstEntries.add(BarEntry(6f,3000f))


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
        val thirdEntries: ArrayList<BarEntry> = arrayListOf()
        thirdEntries.add(BarEntry(0f,3000f))
        thirdEntries.add(BarEntry(1f,3000f))
        thirdEntries.add(BarEntry(2f,3900f))
        thirdEntries.add(BarEntry(3f,3970f))
        thirdEntries.add(BarEntry(4f,4000f))
        thirdEntries.add(BarEntry(5f,3970f))
        thirdEntries.add(BarEntry(6f,3000f))
        val dataSet: BarDataSet = BarDataSet(entries , "Goal for day")
        val secDataSet: BarDataSet = BarDataSet(newEntries , "Consumed in day")
        val thirdDataSet: BarDataSet = BarDataSet(thirdEntries , "Consumed in day")
        dataSet.color = Color.RED
        secDataSet.color = Color.TRANSPARENT
        thirdDataSet.color = Color.TRANSPARENT
        val barData: BarData = BarData(dataSet, secDataSet)
        chart.data = barData
        chart.animateY(1500,  Easing.EaseOutCirc)
        chart.invalidate()
        /*chart.isDragEnabled = true
        chart.setVisibleXRangeMaximum(3.5f)
        barData.barWidth = 0.15f
        chart.xAxis.axisMinimum = 0f
        chart.groupBars(0f, 0.45f, 0.1f)*/

        val pieEntries: ArrayList<PieEntry> = arrayListOf()
        pieEntries.add(PieEntry(20f, "Goal is exceeded"))
        pieEntries.add(PieEntry(30f, "Goal is not reached"))
        pieEntries.add(PieEntry(50f, "No available data"))
        val pieDataSet = PieDataSet(pieEntries, "Values")
        pieDataSet.colors = ColorTemplate.createColors(ColorTemplate.MATERIAL_COLORS)
        val pieData: PieData = PieData(pieDataSet)
        val pieChart = binding!!.pieChart
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.animateXY(1000, 1000, Easing.EaseOutElastic, Easing.EaseInCirc)
        pieChart.invalidate()





    }
}