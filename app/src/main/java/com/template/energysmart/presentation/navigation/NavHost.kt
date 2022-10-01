package com.template.energysmart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.template.energysmart.presentation.screens.authorization.AuthorizationScreen
import com.template.energysmart.presentation.screens.authorization.StartScreen
import com.template.energysmart.presentation.screens.authorization.registration.ConfirmCodeScreen
import com.template.energysmart.presentation.screens.authorization.registration.ConfirmNumberScreen
import com.template.energysmart.presentation.screens.authorization.registration.CreatePasswordScreen
import com.template.energysmart.presentation.screens.device.components.BindDeviceContent
import com.template.energysmart.presentation.screens.device.components.DeviceScreen

import com.template.energysmart.presentation.screens.main.Test
import com.template.energysmart.presentation.screens.notifications.DrawNotificationsFullScreen
import com.template.energysmart.presentation.screens.notifications.NotificationViewState
import com.template.energysmart.presentation.screens.settings.SettingsScreen

@Composable
fun navigation(navController: NavHostController,startDestination: String){
    installNavHost(
            navController = navController,
            startDestination = startDestination
        )

}
@Composable
 fun installNavHost(
    navController: NavHostController,
    startDestination: String

){
    NavHost(navController = navController, startDestination = startDestination) {
        composable("start"){ StartScreen(navController)}
        composable("main") { Test(navController)}
        composable("settings") { SettingsScreen() }
        composable("sign-in"){ AuthorizationScreen(navController)}
        composable("device"){  DeviceScreen(navController = navController) }
        composable("confirm_number"){ ConfirmNumberScreen(navController =  navController,
            text = "Регистрация", type = "")}
        composable("confirm_number_reset"){ ConfirmNumberScreen(navController =  navController,
            text = "Смена пароля", type = "reset")}
        composable("confirm_code/{phone}"){ backStackEntry->
            ConfirmCodeScreen( backStackEntry.arguments?.getString("phone")?:"",navController,
                text = "Подтверждение номера", type = "")}
        composable("confirm_code_reset/{phone}"){ backStackEntry->
            ConfirmCodeScreen( backStackEntry.arguments?.getString("phone")?:"",navController,
                text = "Подтверждение смены пароля", type = "reset")}
        composable("create_password/{phone}"){ backStackEntry->
            CreatePasswordScreen( backStackEntry.arguments?.getString("phone")?:"",navController)
        }
        composable("notification"){

            DrawNotificationsFullScreen(navController)
        }
    }
 }