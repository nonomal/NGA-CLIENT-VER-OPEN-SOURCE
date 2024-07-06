package gov.anzong.androidnga.message.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gov.anzong.androidnga.R
import gov.anzong.androidnga.compose.theme.PrimaryGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sp.phone.util.ActivityUtils

@Preview
@Composable
fun CircleProgressWidget() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(70.dp),
            color = PrimaryGreen,
            strokeWidth = 6.dp
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.padding(start = 32.dp, end = 32.dp),
            text = ActivityUtils.getSaying(),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(64.dp))
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshWidget(
    content: @Composable () -> Unit,
    isEmpty: Boolean = false,
    onRefresh: () -> Unit
) {

    var refreshing by remember {
        mutableStateOf(false)
    }

    // 用协程模拟一个耗时加载
    val scope = rememberCoroutineScope()
    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            delay(1000) // 模拟数据加载
            onRefresh.invoke()
            refreshing = false
        }
    })

    val contentWidget =
        if (isEmpty) {
            { EmptyWidget(onRefresh = onRefresh) }
        } else {
            content
        }

    Box(modifier = Modifier.pullRefresh(state = state)) {
        contentWidget.invoke()
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun EmptyWidget(onRefresh: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onRefresh.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(id = R.string.error_load_failed))
    }
}