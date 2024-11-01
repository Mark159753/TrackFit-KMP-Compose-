package org.track.fit.ui.util.window_size

import androidx.compose.material3.windowsizeclass.objc.KeyValueObserverProtocol
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.useContents
import platform.Foundation.NSKeyValueObservingOptionNew
import platform.Foundation.addObserver
import platform.Foundation.removeObserver
import platform.UIKit.UIViewController
import platform.darwin.NSObject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalForeignApi::class)
@Composable
actual fun calculateWindowSizeClass(): WindowSizeClass {
    val uiViewController = LocalUIViewController.current

    var windowSizeClass by remember(uiViewController) {
        mutableStateOf(WindowSizeClass.calculateFromSize(uiViewController.getViewFrameSize()))
    }

    DisposableEffect(uiViewController) {
        val observer = ObserverObject {
            windowSizeClass = WindowSizeClass.calculateFromSize(uiViewController.getViewFrameSize())
        }

        uiViewController.view.layer.addObserver(
            observer = observer,
            forKeyPath = "bounds",
            options = NSKeyValueObservingOptionNew,
            context = null,
        )

        onDispose {
            uiViewController.view.layer.removeObserver(
                observer = observer,
                forKeyPath = "bounds",
            )
        }
    }

    return windowSizeClass
}

@OptIn(ExperimentalForeignApi::class)
private fun UIViewController.getViewFrameSize(): DpSize = view.frame().useContents {
    // iOS returns density independent pixels, rather than raw pixels
    DpSize(size.width.dp, size.height.dp)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@ExportObjCClass
private class ObserverObject(
    private val onChange: () -> Unit,
) : NSObject(), KeyValueObserverProtocol {
    override fun observeValueForKeyPath(
        keyPath: String?,
        ofObject: Any?,
        change: Map<Any?, *>?,
        context: COpaquePointer?,
    ) {
        onChange()
    }
}