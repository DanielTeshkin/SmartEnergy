package com.template.energysmart.presentation.screens.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.template.energysmart.presentation.screens.authorization.components.Loader
import com.template.energysmart.presentation.theme.Green
import com.template.energysmart.presentation.theme.GreenAlpha
import com.template.energysmart.presentation.theme.MainGrayColor

@Composable
fun UserInfoScreen(viewModel: UserInfoViewModel= hiltViewModel(),navController: NavController= rememberNavController(
)){

    LaunchedEffect(true){
        viewModel.navigation.collect{
            if(it) navController.navigate("device")
        }
    }
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val name=remember{ mutableStateOf("")}
    if(loading) Loader()
    if(error.isNotEmpty()) Toast.makeText(LocalContext.current,error, Toast.LENGTH_LONG).show()
    val focusManager= LocalFocusManager.current
    var buttonPadding by remember { mutableStateOf(0.dp) }
    var enabled =viewModel.enabled.collectAsState()
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

        Box(
            Modifier
                .align(Alignment.TopCenter)


        ) {


            Text(
                text = "Введите ваше имя" ,
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
        }



        Column(
            Modifier
                .align(Alignment.Center)
            , verticalArrangement = Arrangement.SpaceBetween
        ) {


            TextField(value =name.value, onValueChange = {
                name.value=it
                viewModel.changeName(it)
            }, modifier = Modifier
                .width(333.dp)
                .onFocusEvent {
                    buttonPadding = when (it.isFocused) {
                        true -> 250.dp
                        false -> 0.dp

                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
                placeholder = {


                    Text(
                        text = "Ваше имя",
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

        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = buttonPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {viewModel.updateUserInfo() }, modifier = Modifier
                    .width(335.dp)
                    .height(50.dp), enabled = enabled.value, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green,
                    disabledBackgroundColor = GreenAlpha

                ), content = { Text(text = "Продолжить") },
                shape = RoundedCornerShape(8.dp)


            )


        }
    }
}