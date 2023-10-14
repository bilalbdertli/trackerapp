package com.sabanciuniv.todoapp.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CombinedData
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
        chart.xAxis.granularity = 1000f
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

        val trialHigherCalGoals: ArrayList<BarEntry> = arrayListOf()
        higherCaloryGoalList.forEach{ calDay ->
            trialHigherCalGoals.add(BarEntry(calDay.index.toFloat(), calDay.caloryGoal.toFloat()))
        }
        val trialHigherCalCals: ArrayList<BarEntry> = arrayListOf()
        higherCaloryGoalList.forEach{ calDay ->
            trialHigherCalCals.add(BarEntry(calDay.index.toFloat(), calDay.earnedCalories.toFloat()))
        }

        val higherCalDatasetCal: BarDataSet = BarDataSet(trialHigherCalCals , "Daily Calory Earned")
        val higherCalDatasetGoal: BarDataSet = BarDataSet(trialHigherCalGoals , "Daily Calory Goal")
        val barFirstColor = Color.parseColor("#D0BCFF")
        val barSecondaryColor = Color.parseColor("#8C1D18")
        higherCalDatasetCal.color = barFirstColor
        higherCalDatasetGoal.color = barSecondaryColor

        val trialHigherGoalGoals: ArrayList<BarEntry> = arrayListOf()
        higherEarnedCaloriesList.forEach{ calDay ->
            trialHigherGoalGoals.add(BarEntry(calDay.index.toFloat(), calDay.caloryGoal.toFloat()))
        }
        val trialHigherGoalCals: ArrayList<BarEntry> = arrayListOf()
        higherEarnedCaloriesList.forEach{ calDay ->
            trialHigherGoalCals.add(BarEntry(calDay.index.toFloat(), calDay.earnedCalories.toFloat()))
        }
        val higherGoalFirstDataset: BarDataSet = BarDataSet(trialHigherGoalGoals , "")
        val higherGoalSecondDataset: BarDataSet = BarDataSet(trialHigherGoalCals , "")
        higherGoalFirstDataset.color = barSecondaryColor
        higherGoalSecondDataset.color = barFirstColor
        val barData: BarData = BarData(higherCalDatasetGoal, higherCalDatasetCal, higherGoalSecondDataset,
            higherGoalFirstDataset)
        chart.data = barData
        /*chart.description = Description()    // Hide the description
        chart.axisLeft.setDrawLabels(false);
        chart.axisRight.setDrawLabels(false);
        chart.xAxis.setDrawLabels(false);*/
        chart.legend.isEnabled = false;

        chart.animateY(1500,  Easing.EaseOutCirc)
        chart.invalidate()
        /*
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
        pieChart.invalidate()*/
    }

    override fun onStart() {
        super.onStart()
        binding!!.animationButton.setOnClickListener {
            binding!!.barChart.animateY(1500,  Easing.EaseOutCirc)
            binding!!.barChart.invalidate()
        }
    }
}