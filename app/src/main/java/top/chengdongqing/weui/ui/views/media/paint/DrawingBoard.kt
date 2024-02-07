package top.chengdongqing.weui.ui.views.media.paint

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

@Composable
internal fun DrawingBoard(paths: MutableList<StrokeItem>, color: Color, strokeWidth: Float) {
    val currentPath = remember { mutableStateOf<Path?>(null) }
    val currentOffset = remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(color, strokeWidth) {
                detectDragGestures(
                    onDragStart = {
                        currentPath.value = Path().apply {
                            moveTo(it.x, it.y)
                        }
                    },
                    onDragEnd = {
                        currentPath.value?.let {
                            paths.add(StrokeItem(it, color, strokeWidth))
                            currentPath.value = null
                        }
                    }
                ) { change, _ ->
                    currentOffset.value = change.position
                }
            }
    ) {
        paths.forEach {
            drawPath(
                it.path,
                it.color,
                style = Stroke(
                    width = it.width,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        currentOffset.value?.let { offset ->
            currentPath.value?.let { path ->
                path.lineTo(offset.x, offset.y)
                drawPath(
                    path,
                    color,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}