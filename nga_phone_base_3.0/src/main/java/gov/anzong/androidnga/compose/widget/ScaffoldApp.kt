package gov.anzong.androidnga.compose.widget

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun FloatingActionButton(fabClick: (() -> Unit)? = null) {
    if (fabClick != null) {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            onClick = { fabClick.invoke() }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun TopBarView(title: String, callback: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primary, title = {
        Text(title)
    }, navigationIcon = {
        IconButton(onClick = {
            callback()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description"
            )
        }
    })
}


@Preview()
@Composable
fun ScaffoldApp(
    activity: ComponentActivity? = null,
    appTitle: String = "title",
    fabClick: (() -> Unit)? = null,
    appContent: @Composable (() -> Unit)? = null,
) {
    rememberSystemUiController().run {
        setStatusBarColor(MaterialTheme.colors.primary, false)
    }
    Scaffold(
        topBar = {
            TopBarView(title = appTitle, callback = { activity?.finish() })
        },
        floatingActionButton = {
            FloatingActionButton(fabClick = fabClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            appContent?.invoke()
        }
    }
}