package ir.nilva.abotorab.view.page.operation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.anychart.AnyChart
import com.anychart.AnyChart.column
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.*
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import ir.nilva.abotorab.R
import ir.nilva.abotorab.helper.dp
import ir.nilva.abotorab.model.Distribution
import ir.nilva.abotorab.model.House
import ir.nilva.abotorab.model.ReportResponse
import ir.nilva.abotorab.view.page.base.BaseActivity
import ir.nilva.abotorab.webservices.MyRetrofit
import ir.nilva.abotorab.webservices.callWebservice
import ir.nilva.abotorab.webservices.getServices
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.below
import org.jetbrains.anko.centerHorizontally


class ReportActivity : BaseActivity() {

    private var data: ReportResponse? = null
    private var currentPosition = 0
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        switchButton.setCheckedPosition(currentPosition)

        progressBar = ProgressBar(this)
        val layoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.centerHorizontally()
        layoutParams.below(R.id.switchButton)
        layoutParams.topMargin = dp(125)
        progressBar.layoutParams = layoutParams
        rootLayout.addView(progressBar)

        switchButton.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                currentPosition = position
                triggerChart()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            callWebservice { getServices().report() }?.run {
                data = this
                triggerChart()
            }
        }

        exportButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                callWebservice { getServices().export() }?.run {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(MyRetrofit.getBaseUrl() + this.url)
                        )
                    )
                }
            }
        }

        exportStoreButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                callWebservice { getServices().exportStore() }?.run {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(MyRetrofit.getBaseUrl() + this.url)
                        )
                    )
                }
            }
        }
    }

    private fun triggerChart() {
        if (currentPosition == 0 && data != null) {
            initChart(data?.house!!)
        } else if (currentPosition == 1 && data != null) {
            initPieChart(data?.distribution!!)
        }
    }

    private fun initChart(house: House) {
        val chart = AnyChartView(this)
        chart.layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        chart.setProgressBar(progressBar)

        val cartesian = column()

        cartesian.column(prepareData(house)).tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0)
            .offsetY(5)
            .format("{%value}")

        cartesian.animation(true)

        cartesian.yScale(ScaleTypes.ORDINAL)

        cartesian.yAxis(0).labels().format("{%value}")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("بازه های ساعت")
        cartesian.yAxis(0).title("تعداد محموله ها")

        chart.setChart(cartesian)

        chartLayout.removeAllViews()
        chartLayout.addView(chart)
    }

    private fun initPieChart(distribution: Distribution) {
        val chart = AnyChartView(this)
        chart.layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        chart.setProgressBar(progressBar)

        val pie = AnyChart.pie()

        pie.data(prepareData(distribution))

        pie.title("توزیع کل محموله ها")

        pie.labels().position("outside")

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        chart.setChart(pie)

        chartLayout.removeAllViews()
        chartLayout.addView(chart)
    }

    private fun prepareData(house: House): ArrayList<DataEntry> {
        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("< 3", house.in3Hour))
        data.add(ValueDataEntry("3-6", house.in6Hour))
        data.add(ValueDataEntry("6-24", house.in24Hour))
        data.add(ValueDataEntry("24-48", house.in48Hour))
        return data
    }

    private fun prepareData(distribution: Distribution): ArrayList<DataEntry> {
        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("تحویل داده شده", distribution.deliveredToCustomer))
        data.add(ValueDataEntry("تحویل گرفته شده", distribution.deliveredToStore))
        data.add(ValueDataEntry("گم شده", distribution.missed))
        return data
    }
}
