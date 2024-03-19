package top.chengdongqing.weui.feature.system.database

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import top.chengdongqing.weui.core.ui.components.screen.WeScreen
import top.chengdongqing.weui.feature.system.database.address.AddressList

@Composable
fun DatabaseScreen(navController: NavController) {
    WeScreen(
        title = "Database",
        description = "数据库（SQLite+Room）",
        padding = PaddingValues(0.dp),
        scrollEnabled = false
    ) {
        AddressList(navController)
    }
}