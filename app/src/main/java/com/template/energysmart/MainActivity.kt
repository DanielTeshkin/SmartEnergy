package com.template.energysmart

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.data.repositories.UsersRepository
import com.template.energysmart.domain.repositories.DevicesRepository
import com.template.energysmart.presentation.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var  usersRepository:UsersRepository
    @Inject
    lateinit var  deviceRepository: DevicesRepository

    var kbClosed: () -> Unit = {}
    var kbClose: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            val focusManager = LocalFocusManager.current
            kbClosed = {
                focusManager.clearFocus()
            }

                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                when(usersRepository.getToken().isNullOrEmpty()){
                    true -> navigation(navController = rememberNavController(),"start")
                    false-> {
                           when(deviceRepository.getSavedDevice().isNullOrEmpty()) {
                               true-> navigation(navController = rememberNavController(), "device")
                                false -> navigation(navController = rememberNavController( ), startDestination ="main")
                           }
                    }
                }

            setupKeyboardDetection(findViewById(android.R.id.content))
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)){view,insets->
           val bottom=insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }

    fun setupKeyboardDetection(contentView: View) {
        contentView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            contentView.getWindowVisibleDisplayFrame(r)
            val screenHeight = contentView.rootView.height
            val keypadHeight = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                kbClose = false
                // kb opened
            } else if(!kbClose) {
                kbClose = true
                kbClosed()
            }
        }
    }


}






