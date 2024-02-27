package top.chengdongqing.weui.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.chengdongqing.weui.ui.components.loading.WeLoading
import top.chengdongqing.weui.ui.theme.DangerColorLight
import top.chengdongqing.weui.ui.theme.FontColorDark
import top.chengdongqing.weui.ui.theme.FontColorLight
import top.chengdongqing.weui.ui.theme.PrimaryColor

enum class ButtonType {
    PRIMARY,
    DANGER,
    PLAIN
}

enum class ButtonSize(
    val padding: PaddingValues,
    val fontSize: TextUnit,
    val borderRadius: Dp = 8.dp
) {
    LARGE(PaddingValues(vertical = 12.dp, horizontal = 24.dp), 17.sp),
    MEDIUM(PaddingValues(vertical = 10.dp, horizontal = 24.dp), 14.sp),
    SMALL(PaddingValues(vertical = 6.dp, horizontal = 12.dp), 14.sp, 6.dp)
}

/**
 * 按钮
 *
 * @param text 按钮文字
 * @param type 类型
 * @param size 大小
 * @param width 宽度
 * @param disabled 是否禁用
 * @param loading 是否加载中
 * @param onClick 点击事件
 */
@Composable
fun WeButton(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    width: Dp = 184.dp,
    disabled: Boolean = false,
    loading: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val colors = getColor(type)
    val localDisabled = disabled || loading

    Box(
        Modifier
            .width(if (size != ButtonSize.SMALL) width else Dp.Unspecified)
            .clip(RoundedCornerShape(size.borderRadius))
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = if (!localDisabled) rememberRipple() else null
            ) {
                if (!localDisabled) {
                    onClick?.invoke()
                }
            }
            .background(colors.containerColor)
            .padding(size.padding)
            .alpha(if (disabled) 0.7f else 1f)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (loading) {
                WeLoading(color = colors.contentColor)
                Spacer(Modifier.width(8.dp))
            }

            Text(
                text,
                color = colors.contentColor,
                fontSize = size.fontSize
            )
        }
    }
}

@Composable
private fun getColor(type: ButtonType): ButtonColors {
    return when (type) {
        ButtonType.PRIMARY -> ButtonColors()
        ButtonType.DANGER -> dangerColors
        ButtonType.PLAIN -> plainColors
    }
}

private data class ButtonColors(
    val containerColor: Color = PrimaryColor,
    val contentColor: Color = Color.White
)

private val dangerColors: ButtonColors
    @Composable
    get() {
        return if (isSystemInDarkTheme()) {
            ButtonColors(DangerColorLight, FontColorDark)
        } else {
            ButtonColors(Color.Black.copy(0.05f), DangerColorLight)
        }
    }

private val plainColors: ButtonColors
    @Composable
    get() {
        return if (isSystemInDarkTheme()) {
            ButtonColors(Color.White.copy(0.1f), FontColorDark)
        } else {
            ButtonColors(Color.Black.copy(0.05f), FontColorLight)
        }
    }