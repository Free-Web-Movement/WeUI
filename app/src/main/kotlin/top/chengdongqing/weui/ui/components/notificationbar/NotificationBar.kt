package top.chengdongqing.weui.ui.components.notificationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.isActive

enum class NotificationBarEffect {
    ELLIPSIS,
    SCROLL,
    WRAP
}

@Composable
fun WeNotificationBar(
    content: String,
    effect: NotificationBarEffect = NotificationBarEffect.SCROLL,
    scrollStep: Int = 2,
    colors: NotificationBarColors = MaterialTheme.notificationBarColorScheme,
    padding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
) {
    var containerWidth by remember { mutableIntStateOf(0) }
    val offsetX = remember { mutableIntStateOf(0) }

    if (effect == NotificationBarEffect.SCROLL) {
        ScrollingEffect(content, containerWidth, scrollStep, offsetX)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.containerColor)
            .padding(padding)
            .onSizeChanged {
                containerWidth = it.width
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = content,
            color = colors.contentColor,
            fontSize = 13.sp,
            maxLines = if (effect == NotificationBarEffect.WRAP) Int.MAX_VALUE else 1,
            softWrap = effect == NotificationBarEffect.WRAP,
            overflow = if (effect == NotificationBarEffect.ELLIPSIS) TextOverflow.Ellipsis else TextOverflow.Visible,
            modifier = Modifier.offset { IntOffset(x = offsetX.intValue, y = 0) })
    }
}

@Composable
private fun ScrollingEffect(
    content: String,
    containerWidth: Int,
    scrollStep: Int,
    offsetX: MutableIntState
) {
    val textMeasurer = rememberTextMeasurer()
    val contentWidth = remember(content) {
        textMeasurer.measure(content, style = TextStyle(fontSize = 13.sp)).size.width
    }

    LaunchedEffect(containerWidth, contentWidth, scrollStep) {
        offsetX.intValue = containerWidth

        while (isActive && contentWidth > containerWidth) {
            withFrameNanos {
                if (offsetX.intValue >= -contentWidth) {
                    offsetX.intValue -= scrollStep
                } else {
                    offsetX.intValue = containerWidth
                }
            }
        }
    }
}

data class NotificationBarColors(
    val containerColor: Color,
    val contentColor: Color
)

val MaterialTheme.notificationBarColorScheme: NotificationBarColors
    @Composable
    get() = NotificationBarColors(
        containerColor = colorScheme.errorContainer,
        contentColor = colorScheme.error
    )