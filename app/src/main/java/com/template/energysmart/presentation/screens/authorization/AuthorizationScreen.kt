package com.template.energysmart.presentation.screens.authorization
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.presentation.screens.authorization.components.OutlineCustom

import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.GreenAlpha
import com.template.energysmart.presentation.theme.MainGrayColor

@Composable
@Preview
fun StartScreen(navHostController: NavHostController = rememberNavController()){

    Box(


        modifier = Modifier

            .width(360.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(MainGrayColor)

            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 55.dp)

            .alpha(1f)


    ) {
        Column(
            Modifier
                .padding(top = 100.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
            Box {
                Text(
                    text = "Smart Energy",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.sp,

                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier

                        .width(145.dp)

                        //.height(28.dp)

                        .alpha(1f),
                    color = Color(
                        red = 0.06499999761581421f,
                        green = 0.10400000214576721f,
                        blue = 0.20000000298023224f,
                        alpha = 1f
                    ),
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                )
            }
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.height(105.dp)) {


                Button(
                    onClick = {
                        navHostController.navigate("sign-in")

                    }, modifier = Modifier
                        .width(335.dp)
                        .height(50.dp), colors = ButtonDefaults.buttonColors(
                        backgroundColor = Green,
                        disabledBackgroundColor = GreenAlpha

                    ), content = { Text(text = "Войти") },
                    shape = RoundedCornerShape(8.dp)


                )
                Button(
                    onClick = {

                        navHostController.navigate("confirm_number")
                    }, modifier = Modifier
                        .width(335.dp)
                        .height(50.dp), colors = ButtonDefaults.buttonColors(
                        backgroundColor = Green,
                        disabledBackgroundColor = GreenAlpha

                    ), content = { Text(text = "Регистрация") },
                    shape = RoundedCornerShape(8.dp)


                )
            }


        }
    }
}

@Composable

fun AuthorizationScreen(
    navController: NavController = rememberNavController(),
     viewModel: AuthorizationViewModel = hiltViewModel()
    ){
    OutlineCustom(navController,viewModel)
}