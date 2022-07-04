package com.example.blogdataviz

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var lineChart:LineChart
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        val message = bundle!!.getString("message")

        lineChart = findViewById(R.id.lineChart)
        lineChart.description.isEnabled = false

        lineChart.isHighlightPerTapEnabled = false
        lineChart.isHighlightPerDragEnabled = false
        lineChart.isScaleYEnabled = false

        lineChart.xAxis.valueFormatter = MyAxisFormatter()
        lineChart.xAxis.granularity = 52f
        lineChart.axisRight.isEnabled = false

        if(message == "shrimp.csv"){
            val shrimpDataSet = getEntriesFromCSV(R.raw.shrimp, "Shrimp")
            val bananaColor = resources.getColor(R.color.banana, null)
            configureSetLayout(shrimpDataSet, bananaColor)
            LineData(shrimpDataSet).also { lineChart.data = it }
        }
        else if(message == "lobster.csv")
        {
            val lobsterDataSet = getEntriesFromCSV(R.raw.lobster, "Lobster")
            val yogurtColor = resources.getColor(R.color.yogurt, null)
            configureSetLayout(lobsterDataSet, yogurtColor)
            LineData(lobsterDataSet).also { lineChart.data = it }

        }
        else{
            val lasagnaDataSet = getEntriesFromCSV(R.raw.lasagna, "Lasagna")
            val lasagnaColor = resources.getColor(R.color.lasagna, null)
            configureSetLayout(lasagnaDataSet,lasagnaColor)
            LineData(lasagnaDataSet).also { lineChart.data = it }
        }
    }
    private fun getEntriesFromCSV(rawResId: Int, label: String): LineDataSet {

        var data: List<FoodSearch>? = null
        resources.openRawResource(rawResId).use { stream ->
            data = Parser.toDataSet(stream.reader())
        }
        val entries: MutableList<Entry> = ArrayList()

        data?.mapIndexed { index, foodSearch ->
            entries.add(
                Entry(index.toFloat(), foodSearch.value.toFloat(), foodSearch)
            )
        }

        return LineDataSet(entries, label)
    }
    private fun configureSetLayout(set: LineDataSet, color: Int) {

        set.color = color                         // color of the line
        set.setDrawFilled(true)                   // fill the space between line and chart bottom
        set.fillColor = color                     // color of the fill
        set.setDrawCircles(false)                 // disable drawing circles over each Entry point
        set.mode = LineDataSet.Mode.CUBIC_BEZIER  // round the line
        set.fillAlpha = 50                        // make fill transparent with alpha (0-255)

    }
    val startingYear = 2004
    inner class MyAxisFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return "${(startingYear + (value.toInt()) / 52)}"

        }
    }
}