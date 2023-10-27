package top.chengdongqing.weui.ui.views.feedback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.chengdongqing.weui.ui.components.Page
import top.chengdongqing.weui.ui.components.feedback.WePopup
import top.chengdongqing.weui.ui.components.form.WeButton

@Composable
fun PopupPage() {
    Page(title = "Popup", description = "弹出框") {
        val visible = remember {
            mutableStateOf(false)
        }

        WeButton(text = "open") {
            visible.value = true
        }

        WePopup(visible.value, onClose = {
            visible.value = false
        }) {
            Box(Modifier.height(450.dp))
        }
    }
}