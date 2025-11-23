package cmpt362.group5.bevr.ui.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries

/**
 * Vico-based column chart for drink breakdown.
 *
 * X axis: drink type labels
 * Y axis: number of drinks, step chosen based on max count.
 */
@Composable
fun DrinkBreakdownChart(
    drinkTypeCounts: Map<String, Int>,
    modifier: Modifier = Modifier,
) {
    // Normalize to a stable list of labels + values.
    val (labels, values) = remember(drinkTypeCounts) {
        if (drinkTypeCounts.isNotEmpty()) {
            val sorted = drinkTypeCounts.entries.sortedBy { it.key }
            val labels = sorted.map { it.key }
            val vals = sorted.map { it.value.toFloat() }
            labels to vals
        } else {
            // Skeleton: fixed labels with tiny non-zero values so bars render.
            val labels = listOf("Coffee", "Tea", "Juice", "Liquor", "Boba")
            val vals = List(labels.size) { 1f }
            labels to vals
        }
    }

    val maxValue = (values.maxOrNull() ?: 1f).coerceAtLeast(1f)
    val yStep = chooseYAxisStep(maxValue.toInt()).toDouble()

    val modelProducer = remember { CartesianChartModelProducer() }

    // Feed the data into the model producer.
    LaunchedEffect(values) {
        modelProducer.runTransaction {
            columnSeries {
                // Single series, x = index, y = value.
                series(values)
            }
        }
    }

    // X-axis formatter: map index -> drink label.
    val bottomFormatter = remember(labels) {
        CartesianValueFormatter { _, value, _ ->
            val index = value.toInt()
            // Vico 2.x does not allow empty labels; use a space as fallback.
            labels.getOrNull(index) ?: " "
        }
    }

    val startAxis = VerticalAxis.rememberStart(
        itemPlacer = VerticalAxis.ItemPlacer.step(step = { yStep }),
    )

    val bottomAxis = HorizontalAxis.rememberBottom(
        valueFormatter = bottomFormatter,
    )

    ProvideVicoTheme(rememberM3VicoTheme()) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(),
                startAxis = startAxis,
                bottomAxis = bottomAxis,
            ),
            modelProducer = modelProducer,
            modifier = modifier
                .fillMaxWidth()
                .height(180.dp),
        )
    }
}

/**
 * Pick a "nice" Y-axis step based on the maximum count.
 */
private fun chooseYAxisStep(max: Int): Int =
    when {
        max <= 5 -> 1
        max <= 10 -> 2
        max <= 25 -> 5
        max <= 100 -> 10
        max <= 500 -> 50
        else -> 100
    }
