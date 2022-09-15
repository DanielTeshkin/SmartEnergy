package com.template.energysmart.presentation.screens.authorization.components

import android.graphics.Outline
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.navigation.NavController
import com.template.energysmart.presentation.screens.authorization.AuthorizationViewModel
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.GreenAlpha
import com.template.energysmart.presentation.theme.MainGrayColor


@Composable

fun OutlineCustom(navController: NavController, viewModel: AuthorizationViewModel) {



      LaunchedEffect(true){
          viewModel.navigation.collect{
              if(it) navController.navigate("device")
          }
      }
    val enabled = viewModel.enabled.collectAsState()
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
            .background(MainGrayColor)

            .padding(start = 16.dp, top = 55.dp, end = 16.dp, bottom = 75.dp)

            .alpha(1f)
            .fillMaxHeight()
            .fillMaxWidth()


    ) {
        val phone = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val buttonEnabled = remember { mutableStateOf(false) }
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .height(75.dp)
        ) {


            Text(
                text = "Вход в аккаунт",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier


                    //.height(28.dp)
                    .align(Alignment.TopCenter)
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
            Text(
                text = "Введите ваши данные.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                    .width(335.dp)
                    .align(Alignment.BottomCenter)
                    //.height(17.dp)

                    .alpha(1f),
                color = Color(
                    red = 0.08444444835186005f,
                    green = 0.1370864361524582f,
                    blue = 0.2666666805744171f,
                    alpha = 0.550000011920929f
                ),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        }



        Column(
            Modifier
                .align(Alignment.Center)
                .height(120.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {


            TextField(value = phone.value, onValueChange = {
                phone.value = it
                viewModel.checkFieldsState(it)
            }, modifier = Modifier
                .height(50.dp)
                .width(333.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {


                    Text(
                        text = "Номер телефона",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(128.dp)

                            //.height(19.dp)

                            .alpha(1f),
                        color = Color(
                            red = 0.08444444835186005f,
                            green = 0.1370864361524582f,
                            blue = 0.2666666805744171f,
                            alpha = 0.550000011920929f
                        ),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )


                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp)
            )
            TextField(value = password.value, onValueChange = {
                password.value = it
                viewModel.checkFieldsState(it)
            }, modifier = Modifier
                .height(50.dp)
                .width(335.dp),
                placeholder = {


                    Text(
                        text = "Пароль",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.sp,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .width(57.dp)

                            //.height(19.dp)

                            .alpha(1f),
                        color = Color(
                            red = 0.08444444835186005f,
                            green = 0.1370864361524582f,
                            blue = 0.2666666805744171f,
                            alpha = 0.550000011920929f
                        ),
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )

                }, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), shape = RoundedCornerShape(8.dp)
            )
        }

        Column(Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.CenterHorizontally) {
            val context = LocalContext.current

            Button(
                onClick = {
                    viewModel.signIn(phone.value, password.value, context)
                   viewModel.disableButton()

                },
                modifier = Modifier
                    .width(335.dp)
                    .height(50.dp), enabled = enabled.value, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green,
                    disabledBackgroundColor = GreenAlpha

                ), content = { Text(text = "Продолжить") },
                shape = RoundedCornerShape(8.dp)


            )

            Text(
                text = "Забыл пароль",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                   .padding(5.dp)
                    //.height(17.dp)

                    .alpha(1f),
                color = Color(
                    red = 0.08444444835186005f,
                    green = 0.1370864361524582f,
                    blue = 0.2666666805744171f,
                    alpha = 0.550000011920929f
                ),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )


        }


    }
}

