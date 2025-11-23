package cmpt362.group5.bevr.ui.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpt362.group5.bevr.data.usersettings.BEVERAGE_DEFINITIONS
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore

/**
 * Vico column chart for drink breakdown.
 *
 * `drinkTypeCounts` is a map of canonical beverage key -> count,
 * already filtered by activeBeverages in the ProfileViewModel.
 */
@Composable
fun DrinkBreakdownChart(
    drinkTypeCounts: Map<String, Int>,
    modifier: Modifier = Modifier,
) {
    // Build labels and values based on canonical definition ordering.
    val (labels, values) = remember(drinkTypeCounts) {
        if (drinkTypeCounts.isNotEmpty()) {
            val defsInUse = BEVERAGE_DEFINITIONS.filter { def ->
                def.key in drinkTypeCounts
            }
            val labels = defsInUse.map { it.label }
            val values = defsInUse.map { def ->
                drinkTypeCounts[def.key]?.toFloat() ?: 0f
            }
            labels to values
        } else {
            val labels = BEVERAGE_DEFINITIONS.map { it.label }
            val values = List(labels.size) { 0f } // show empty chart
            labels to values
        }
    }

    // Axis scaling: top of Y should be at least the max bar and total count.
    val maxBarValue = values.maxOrNull() ?: 0f
    val total = values.sum()
    val axisBasis = maxOf(maxBarValue.toInt(), total.toInt(), 1)
    val yStep = chooseYAxisStep(axisBasis).toDouble()

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(values) {
        modelProducer.runTransaction {
            columnSeries {
                series(values)
            }
        }
    }

    // Smaller text for labels so we can fit all 5.
    val bottomLabelComponent = rememberAxisLabelComponent(
        textSize = 10.sp,
        lineCount = 1,
        margins = insets(horizontal = 2.dp, vertical = 2.dp),
    )

    val bottomFormatter = remember(labels) {
        CartesianValueFormatter { _, value, _ ->
            val index = value.toInt()
            labels.getOrNull(index) ?: " "
        }
    }

    // Custom range provider to ensure Y max is >= axisBasis.
    val rangeProvider = remember(axisBasis) {
        object : CartesianLayerRangeProvider {
            override fun getMinX(
                minX: Double,
                maxX: Double,
                extraStore: ExtraStore
            ): Double = minX

            override fun getMaxX(
                minX: Double,
                maxX: Double,
                extraStore: ExtraStore
            ): Double = maxX

            override fun getMinY(
                minY: Double,
                maxY: Double,
                extraStore: ExtraStore
            ): Double = 0.0

            override fun getMaxY(
                minY: Double,
                maxY: Double,
                extraStore: ExtraStore
            ): Double = maxOf(maxY, axisBasis.toDouble())
        }
    }

    val startAxis = VerticalAxis.rememberStart(
        itemPlacer = VerticalAxis.ItemPlacer.step(step = { yStep }),
    )

    val bottomAxis = HorizontalAxis.rememberBottom(
        label = bottomLabelComponent,
        valueFormatter = bottomFormatter,
        // Aligned with the implicit x = 0,1,2,... so each bar *can* have a label.
        itemPlacer = HorizontalAxis.ItemPlacer.aligned(),
    )

    ProvideVicoTheme(rememberM3VicoTheme()) {
        CartesianChartHost(
            modifier = modifier
                .fillMaxWidth()
                .height(240.dp),
            modelProducer = modelProducer,
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(
                    rangeProvider = rangeProvider,
                ),
                startAxis = startAxis,
                bottomAxis = bottomAxis,
            ),
        )
    }
}

private fun chooseYAxisStep(max: Int): Int =
    when {
        max <= 5 -> 1
        max <= 10 -> 2
        max <= 25 -> 5
        max <= 100 -> 10
        else -> 20
    }
