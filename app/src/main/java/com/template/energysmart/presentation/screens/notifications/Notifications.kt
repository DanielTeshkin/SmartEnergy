package com.template.energysmart.presentation.screens.notifications

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.template.energysmart.R
import com.template.energysmart.presentation.navigation.navigation
import com.template.energysmart.presentation.screens.authorization.components.Loader
import com.template.energysmart.presentation.screens.notifications.components.textInBorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable

fun DrawNotificationsFullScreen( navController: NavController,viewModel: NotificationViewModel = hiltViewModel()){
    Log.i("naf","naf")
    LaunchedEffect(true ){ viewModel.mainNavigation.collect { if (it) navController.navigate("main") } }

    val instructionFlag= viewModel.instruction.collectAsState()
    if (instructionFlag.value){
        viewModel.changeStatus()
        navController.navigate("instruction")

    }


    val state=  navController
        .previousBackStackEntry?.savedStateHandle?.get<NotificationViewState>("state")?: NotificationViewState()
    navController.currentBackStackEntry?.savedStateHandle?.set(
        value = state,
        key = "state"
    )
    Log.i("naf","naf")
    val loading by viewModel.isShowDialog.collectAsState()
    val error by viewModel.errorShow.collectAsState()
    if (loading) Loader()
    if (error.isNotEmpty()) Toast.makeText(LocalContext.current,error, Toast.LENGTH_LONG).show()
    ConstraintLayout(

        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .background(state.theme!!.background)
            .border(
                0.5.dp,
                Color(red = 0f, green = 0f, blue = 0f, alpha = 0f),
                RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp
                )
            )
            .padding(start = 16.dp, top = 176.dp, end = 16.dp, bottom = 50.dp)

            .alpha(1f)
            .fillMaxSize()


    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier

                .fillMaxWidth()

                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color.Transparent)

                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                .alpha(1f)


        ) {

            Box(


                modifier = Modifier

                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.Transparent)

                    .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                    .alpha(1f)


            ) { Image(ImageVector.vectorResource(id = state.image?:R.drawable.ic_launch_error_automation), "") }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(9.dp, Alignment.Top),
                modifier = Modifier

                    .width(312.dp)

                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.Transparent)

                    .padding(start = 0.dp, top = 50.dp, end = 0.dp, bottom = 0.dp)

                    .alpha(1f)



            ) {

                Log.i("naf","nif")
                Text(

                    text = state.title,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.sp,

                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier

                        .width(312.dp)

                        //.height(23.dp)

                        .alpha(1f),
                    color = state.theme!!.generalText,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    )


                Text(
                    text = state.description,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.20000000298023224.sp,

                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier

                        .width(312.dp)

                        //.height(48.dp)

                        .alpha(1f),
                    color =state.theme.generalText,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                )
            }
            if (state.instruction?.isNotEmpty() == true)
                Box(Modifier.padding(top=20.dp)) {
                    textInBorder(state.instruction)
                }
        }

        if (state.textMode!=null) TextMode(state.textMode)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,


            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(Color.Transparent)

                .padding(top = 440.dp)

                .alpha(1f)
                .fillMaxWidth()
                .height(160.dp)

        ) {

            state.theme.buttonFirst?.let {   ImageVector.vectorResource(id = it) }?.let {
                        Image(  it, contentDescription ="",Modifier.clickable(
                            onClick = {
                                viewModel.handleEvent(state.buttonFirstEvent?:NotificationsViewEvent.IgnoreNotificationEvent)
                            }
                        )

                        )
                    }

            Image(
                ImageVector.vectorResource(id = state.theme.buttonSecond!!), contentDescription ="",
                Modifier
                    .clickable(
                        onClick = {
                            viewModel.handleEvent(
                                state.buttonSecondEvent
                                    ?: NotificationsViewEvent.IgnoreNotificationEvent
                            )

                        }
                    )
                    .offset(y = 10.dp)



            )


                    }
                }
            }


@Composable
@Preview
fun test(){

}

@Composable
fun TextMode(text:String){
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.20000000298023224.sp,

        overflow = TextOverflow.Ellipsis,
        modifier = Modifier

            .fillMaxWidth()
            .padding(top = 400.dp)

            //.height(16.dp)

            .alpha(1f),
        color = Color(red = 0.3541666567325592f, green = 0.3541666567325592f, blue = 0.3541666567325592f, alpha = 1f),
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )
}