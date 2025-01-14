package gov.anzong.androidnga.message

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gov.anzong.androidnga.compose.theme.AppTheme
import gov.anzong.androidnga.compose.widget.ScaffoldApp

abstract class BaseComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ScaffoldApp(this, appTitle = title.toString(), fabClick = getFabClickAction()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        ContentView()
                    }
                }
            }
        }
    }

    abstract fun getFabClickAction(): (() -> Unit)?

    @Composable
    abstract fun ContentView()
}