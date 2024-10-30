package org.track.fit

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.track.fit.ui.MainViewModel
import org.track.fit.ui.TrackFit
import org.track.fit.ui.util.language.getCurrentLng
import org.track.fit.ui.util.language.toAppLanguageOrNull

class MainActivity : ComponentActivity() {


    val viewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        viewModel.saveInitLng(getCurrentLng().toAppLanguageOrNull())
        setContent {
            TrackFit(
                viewModel = viewModel
            )
        }
    }
}

@Preview(device = "id:pixel_3a")
@Composable
fun AppAndroidPreview() {
    TrackFit()
}